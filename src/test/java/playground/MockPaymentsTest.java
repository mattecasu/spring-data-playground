package playground;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import playground.model.MockPayments;
import playground.model.Payment;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class MockPaymentsTest {

    @Test
    public void testSingleMockPayment() {
        isNotBlank(MockPayments.mockPayment().getId());
    }

    @Test
    public void testEquality() {
        Payment mock1 = MockPayments.mockPayment();
        Payment mock2 = MockPayments.mockPayment()
                .setId(mock1.getId());
        assertThat(mock1, is(equalTo(mock2)));
    }

}