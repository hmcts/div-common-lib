package uk.gov.hmcts.reform.divorce.mapper.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoExistingPaymentStrategyUTest {
    private final NoExistingPaymentStrategy noExistingPaymentStrategy = new NoExistingPaymentStrategy();

    @Test
    public void testNoExistingPaymentsAddsJustNewPayment() {
        final Payment newPayment = Payment.builder().paymentReference("111222333").build();

        final List<PaymentCollection> existingPaymentsList = null;

        final List<PaymentCollection> expectedPaymentsList = Collections.singletonList(PaymentCollection.builder()
            .value(newPayment).build());

        final List<PaymentCollection> returnedPaymentsList =
            noExistingPaymentStrategy.getCurrentPaymentsList(newPayment, existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }
}
