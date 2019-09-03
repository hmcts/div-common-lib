package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NoExistingPaymentReferenceStrategyUTest {
    private final ExistingPaymentReferenceStrategy existingPaymentReferenceStrategy =
        new ExistingPaymentReferenceStrategy();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        final PaymentCollection newPayment = createPayment("111222333", "success");
        final PaymentCollection existingPayment = createPayment("999888777", "success");
        final PaymentCollection toBeReplacedPayment = createPayment("111222333", "created");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final List<PaymentCollection> expectedPaymentsList = Arrays.asList(existingPayment, newPayment);
        final List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }

    private PaymentCollection createPayment(String reference, String status) {
        final Payment payment = Payment.builder()
            .paymentReference(reference)
            .paymentStatus(status)
            .build();

        return PaymentCollection.builder().value(payment).build();
    }
}
