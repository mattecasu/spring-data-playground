package playground.model;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.IntStream;

public class MockPayments {

  public static Collection<Payment> getMockPayments(Integer n) {
    return IntStream.range(0, n)
        .mapToObj(i -> mockPayment())
        .collect(toList());
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
                        .setAccountName("W Owens")
                        .setAccountNumber("31926819")
                        .setAccountNumberCode("BBAN")
                        .setAccountType(0)
                        .setAddress("1 The Beneficiary Localtown SE2")
                        .setBankId("403000")
                        .setBankIdCode("GBDSC")
                        .setName("Wilfred Jeremiah Owens")
                )
                .setChargesInformation(
                    new PaymentAttributes.ChargesInformation()
                        .setBearerCode("SHAR")
                        .setSenderCharges(newArrayList(
                            new PaymentAttributes.ChargesInformation.Charge()
                                .setAmount(5.00f).setCurrency("GBP"),
                            new PaymentAttributes.ChargesInformation.Charge()
                                .setAmount(10.00f).setCurrency("USD"))
                        )
                        .setReceiverChargesAmount(1.00f)
                        .setReceiverChargesCurrency("USD")
                )
                .setCurrency("GBP")
                .setDebtorParty(
                    new PaymentAttributes.Party()
                        .setAccountName("EJ Brown Black")
                        .setAccountNumber("GB29XABC10161234567801")
                        .setAccountNumberCode("IBAN")
                        .setAccountType(0)
                        .setAddress("10 Debtor Crescent Sourcetown NE1")
                        .setBankId("203301")
                        .setBankIdCode("GBDSC")
                        .setName("Emelia Jane Brown")
                )
                .setEndToEndReference("Wil piano Jan")
                .setFx(
                    new PaymentAttributes.Fx()
                        .setContractReference("FX123")
                        .setExchangeRate(2.00000f)
                        .setOriginalAmount(200.42f)
                        .setOriginalCurrency("USD")
                )
                .setNumericReference("1002001")
                .setPaymentId("123456789012345678")
                .setPaymentPurpose("Paying for goods/services")
                .setPaymentScheme("FPS")
                .setPaymentType("Credit")
                .setProcessingDate("2017-01-18")
                .setReference("Payment for Em's piano lessons")
                .setSchemePaymentSubtype("InternetBanking")
                .setSchemePaymentType("ImmediatePayment")
                .setSponsorParty(
                    new PaymentAttributes.Party()
                        .setAccountNumber("56781234")
                        .setBankId("123123")
                        .setBankIdCode("GBDSC")
                )

        );
  }

}
