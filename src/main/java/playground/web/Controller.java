package playground.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Collection;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import playground.exception.My400Exception;
import playground.exception.My404Exception;
import playground.model.MockPayments;
import playground.model.Payment;
import playground.repo.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
public class Controller {

    @Autowired
    PaymentRepository paymentRepository;

    private static final Integer NUMBER_OF_MOCKS = 4;

    @RequestMapping(value = "payments", method = GET)
    public Flux<Payment> getAll(@RequestParam(required = false) String beneficiary) {

        return StringUtils.isBlank(beneficiary) ?
                paymentRepository.findAll() :
                paymentRepository.findByBeneficiaryName(beneficiary);
    }

    @RequestMapping(value = "payments/mock", method = GET)
    public Flux<Payment> getMock() {
        return Flux.fromIterable(MockPayments.getMockPayments(NUMBER_OF_MOCKS));
    }

    @RequestMapping(value = "payments/{id}", method = GET)
    public Mono<Payment> getPayment(@PathVariable String id) throws My404Exception {

        return paymentRepository.findById(id).switchIfEmpty(
                Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist"))
        );
    }

    @RequestMapping(value = "payments", method = POST)
    public Mono<Payment> postPayment(@RequestBody Payment payment) throws My400Exception {

        if (payment.getId() != null) {
            return Mono.error(new ResponseStatusException(BAD_REQUEST, "POSTs shouldn't have ids"));
        }

        String newId = UUID.randomUUID().toString();
        return paymentRepository.insert(payment.setId(newId));
    }

    @RequestMapping(value = "payments/{id}", method = PUT)
    public Mono<Payment> updateCustomer(@RequestBody Payment payment, @PathVariable String id) {

        if (payment.getId() == null || !payment.getId().equals(id)) {
            return Mono.error(new ResponseStatusException(BAD_REQUEST, "path variable id and payment id should match"));
        }

        if (!paymentRepository.existsById(id).block()) {
            return Mono.error(new ResponseStatusException(NOT_FOUND, "payment " + id + " doesn't exist"));
        }

        paymentRepository.delete(payment).block();
        paymentRepository.insert(payment).block();
        return getPayment(payment.getId());
    }

    @RequestMapping(value = "payments", method = DELETE)
    public Mono<Void> deleteAll() {
        return paymentRepository.deleteAll();
    }

    @RequestMapping(value = "payments/{id}", method = DELETE)
    public Mono<Void> deletePayment(@PathVariable String id) {
        return paymentRepository.deleteById(id);
    }

}
