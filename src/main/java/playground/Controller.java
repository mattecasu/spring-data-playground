package playground;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import playground.exception.My400Exception;
import playground.exception.My404Exception;
import playground.model.MockPayments;
import playground.model.Payment;
import playground.repo.PaymentRepository;

import java.util.Collection;
import java.util.Map;
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
    public Map<String, Object> getAll(Pageable pageable) {
        return ImmutableMap.of(
                "data", paymentRepository.findAll(pageable),
                "links", ImmutableMap.of("self", getHal())
        );
    }

    @RequestMapping(value = "payments/mock", method = GET)
    public Collection<Payment> getMock() {
        return MockPayments
                .getMockPayments(NUMBER_OF_MOCKS)
                .stream().map(p -> p.setLinks(getHal()))
                .collect(toList());
    }

    @RequestMapping(value = "payments/{id}", method = GET)
    public Payment getPayment(@PathVariable String id) throws My404Exception {
        checkExistence(id);
        return paymentRepository.findOne(id)
                .setLinks(getHal());
    }

    @RequestMapping(value = "payments", method = POST)
    public Payment postPayment(@RequestBody Payment payment) {

        if (payment.getId() != null) {
            throw new My400Exception("POSTs shouldn't have ids");
        }

        String newId = UUID.randomUUID().toString();
        paymentRepository.save(payment.setId(newId));
        return paymentRepository.findOne(newId)
                .setLinks(getHal());
    }

    @RequestMapping(value = "payments/{id}", method = PUT)
    public Payment updateCustomer(@RequestBody Payment payment, @PathVariable String id) {

        if (payment.getId() == null || !payment.getId().equals(id)) {
            throw new My400Exception("path variable id and payment id should match");
        }

        checkExistence(payment.getId());
        paymentRepository.save(payment);
        return payment
                .setLinks(getHal());
    }

    @RequestMapping(value = "payments", method = DELETE)
    public void deleteAll() {
        paymentRepository.deleteAll();
    }

    @RequestMapping(value = "payments/{id}", method = DELETE)
    public void deletePayment(@PathVariable String id) {
        paymentRepository.delete(id);
    }


    private void checkExistence(String id) throws My404Exception {
        if (!paymentRepository.exists(id)) {
            throw new My404Exception("payment " + id + " doesn't exist");
        }
    }

    private Map<String, Object> getHal() {
        return ImmutableMap.of("self", getHalUrl());
    }

    private String getHalUrl() {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .build()
                .toUriString();
    }

}
