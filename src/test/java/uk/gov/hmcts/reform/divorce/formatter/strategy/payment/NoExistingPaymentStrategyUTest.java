package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPayment;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPaymentCollection;

public class NoExistingPaymentStrategyUTest {
    private final NoExistingPaymentStrategy noExistingPaymentStrategy = new NoExistingPaymentStrategy();

    @Test
    public void testNoExistingPaymentsAddsJustNewPayment() {
        Payment newPayment = createPayment("111222333", "success");
        List<PaymentCollection> existingPaymentsList = null;
        List<PaymentCollection> expectedPaymentsList = Collections.singletonList(createPaymentCollection(newPayment));

        final List<PaymentCollection> returnedPaymentsList =
            noExistingPaymentStrategy.getCurrentPaymentsList(newPayment, existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }
}
