package playground;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static playground.model.MockPayments.mockPayment;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import playground.model.Payment;

@Slf4j
public class MockPaymentsTest {

  @Test
  public void testSingleMockPayment() {
    isNotBlank(mockPayment().getId());
  }

  @Test
  public void testEquality() {

    Payment mock1 = mockPayment();
    Payment mock2 = mockPayment().setId(mock1.getId());

    assertThat(mock1, is(equalTo(mock2)));
  }

}