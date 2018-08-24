package playground.model;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

public class MockPayments {

  public static Collection<Payment> getMockPayments(Integer n) {
    return IntStream.range(0, n).mapToObj(i -> mockPayment()).collect(toList());
  }

  public static Payment mockPayment() {
    return new Payment()
        .setId(randomUUID().toString())
        .setOrganisationId("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb")
        .setVersion(0)
        .setType("Payment")
        .setAttributes(
            new PaymentAttributes()
                .setAmount(100.21f)
                .setBeneficiaryParty(
                    new PaymentAttributes.Party()
                        .setName("Wilfred Jeremiah Owens")
                        .setAccountNumber("31926819")
                        .setBankIdCode("GBDSC")
                ));
  }
}