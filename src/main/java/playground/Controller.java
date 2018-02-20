package playground;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.exception.My400Exception;
import playground.exception.My404Exception;
import playground.model.MockPayments;
import playground.model.Payment;
import playground.repo.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
    public Collection<Payment> getMock() {
        return MockPayments
                .getMockPayments(NUMBER_OF_MOCKS);
    }

    @RequestMapping(value = "payments/{id}", method = GET)
    public Mono<Payment> getPayment(@PathVariable String id) throws My404Exception {
//        checkExistence(id);

        return paymentRepository.findById(id).switchIfEmpty(
                Mono.error(new My404Exception("payment " + id + " doesn't exist"))
        );
    }

    @RequestMapping(value = "payments", method = POST)
    public Mono<Payment> postPayment(@RequestBody Payment payment) {

        if (payment.getId() != null) {
            return Mono.error(new My400Exception("POSTs shouldn't have ids"));
        }

        String newId = UUID.randomUUID().toString();
        paymentRepository.save(payment.setId(newId)).block();
        return paymentRepository.findById(newId);
    }

    @RequestMapping(value = "payments/{id}", method = PUT)
    public Mono<Payment> updateCustomer(@RequestBody Payment payment, @PathVariable String id) {

        if (payment.getId() == null || !payment.getId().equals(id)) {
            return Mono.error(new My400Exception("path variable id and payment id should match"));
        }

        if (!paymentRepository.existsById(id).block())
            return Mono.error(new My404Exception("payment " + id + " doesn't exist"));

        return paymentRepository.save(payment);
    }

    @RequestMapping(value = "payments", method = DELETE)
    public void deleteAll() {
        paymentRepository.deleteAll().block();
    }

    @RequestMapping(value = "payments/{id}", method = DELETE)
    public void deletePayment(@PathVariable String id) {
        paymentRepository.deleteById(id).block();
    }


//    private void checkExistence(String id) throws My404Exception {
//        if (!paymentRepository.existsById(id).block()) {
//            throw new My404Exception("payment " + id + " doesn't exist");
//        }
//    }

//    private Map<String, Object> getHal() {
//        return ImmutableMap.of("self", getHalUrl());
//    }

//    private String getHalUrl() {
//        return ServletUriComponentsBuilder
//                .fromCurrentRequestUri()
//                .build()
//                .toUriString();
//    }

}
