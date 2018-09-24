package playground.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import playground.model.Payment;
import playground.repo.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static playground.model.MockPayments.getMockPayments;

@RestController
@CrossOrigin
@Slf4j
public class Controller {

  @Autowired
  PaymentRepository paymentRepository;

  private static final Integer NUMBER_OF_MOCKS = 4;

  @GetMapping(value = "/payments")
  public Flux<Payment> getAll(@RequestParam(required = false) String beneficiary) {

    return isBlank(beneficiary)
        ? paymentRepository.findAll()
        : paymentRepository.findByBeneficiaryName(beneficiary);
  }

  @GetMapping(value = "/payments/mock")
  public Flux<Payment> getMock() {
    return Flux.fromIterable(getMockPayments(NUMBER_OF_MOCKS));
  }

  @GetMapping(value = "/payments/{id}")
  public Mono<Payment> getPayment(@PathVariable String id) {

    return paymentRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist")));
  }

  @PostMapping(value = "/payments")
  public Mono<Payment> postPayment(@RequestBody Payment payment) {

    if (payment.getId() != null) {
      return Mono.error(new ResponseStatusException(BAD_REQUEST, "POSTs shouldn't have ids"));
    }

    String newId = UUID.randomUUID().toString();
    return paymentRepository.insert(payment.setId(newId));
  }

  @PutMapping(value = "/payments/{id}")
  public Mono<Payment> updateCustomer(@RequestBody Payment payment, @PathVariable String id) {

    if (payment.getId() == null || !payment.getId().equals(id)) {
      return Mono.error(
          new ResponseStatusException(BAD_REQUEST, "path variable id and payment id should match"));
    }

    return paymentRepository
        .existsById(id)
        .flatMap(p -> paymentRepository.deleteById(id).then(paymentRepository.insert(payment)))
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist")));
  }

  @DeleteMapping(value = "/payments")
  public Mono<Void> deleteAll() {
    return paymentRepository.deleteAll();
  }

  @DeleteMapping(value = "/payments/{id}")
  public Mono<Void> deletePayment(@PathVariable String id) {
    return paymentRepository
        .findById(id)
        .flatMap(payment -> paymentRepository.deleteById(id))
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist")));
  }
}