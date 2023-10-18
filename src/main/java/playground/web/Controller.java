package playground.web;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static playground.model.MockPayments.getMockPayments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import playground.model.Payment;
import playground.repo.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Searcher Controller")
@CrossOrigin
@Slf4j
public class Controller {

  @Autowired PaymentRepository paymentRepository;

  private static final Integer NUMBER_OF_MOCKS = 4;

  @Operation(summary = "Get a payment")
  @RequestMapping(
      value = "/payments",
      method = {GET})
  public Flux<Payment> getAll(@RequestParam(required = false) String beneficiary) {

    return isBlank(beneficiary)
        ? paymentRepository.findAll()
        : paymentRepository.findByBeneficiaryName(beneficiary);
  }

  @RequestMapping(
      value = "/payments/mock",
      method = {GET})
  public Flux<Payment> getMock() {
    return Flux.fromIterable(getMockPayments(NUMBER_OF_MOCKS));
  }

  @RequestMapping(
      value = "/payments/{id}",
      method = {GET})
  public Mono<Payment> getPayment(@PathVariable String id) {

    return paymentRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist")));
  }

  @RequestMapping(
      value = "/payments",
      method = {POST})
  public Mono<Payment> postPayment(@RequestBody Payment payment) {

    if (payment.getId() != null) {
      return Mono.error(new ResponseStatusException(BAD_REQUEST, "POSTs shouldn't have ids"));
    }

    String newId = UUID.randomUUID().toString();
    return paymentRepository.insert(payment.setId(newId));
  }

  @RequestMapping(
      value = "/payments/{id}",
      method = {PUT})
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

  @RequestMapping(
      value = "/payments",
      method = {DELETE})
  public Mono<Void> deleteAll() {
    return paymentRepository.deleteAll();
  }

  @RequestMapping(
      value = "/payments/{id}",
      method = {DELETE})
  public Mono<Void> deletePayment(@PathVariable String id) {
    return paymentRepository
        .findById(id)
        .flatMap(payment -> paymentRepository.deleteById(id))
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist")));
  }
}
