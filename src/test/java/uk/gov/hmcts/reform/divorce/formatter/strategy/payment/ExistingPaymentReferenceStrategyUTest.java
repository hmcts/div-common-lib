package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ExistingPaymentReferenceStrategyUTest {
    private final ExistingPaymentReferenceStrategy existingPaymentReferenceStrategy =
        new ExistingPaymentReferenceStrategy();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        final PaymentCollection newPayment = createPayment("111222333", "success", "123456789");
        final PaymentCollection existingPayment = createPayment("999888777", "success", "12345");
        final PaymentCollection toBeReplacedPayment = createPayment("111222333", "created", "123");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final PaymentCollection updatedNewPayment = createPayment("111222333", "success", "123");

        final List<PaymentCollection> expectedPaymentsList = asList(existingPayment, updatedNewPayment);
        final List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }

    @Test
    public void testExistingPaymentReferenceAndSubsetOfPaymentWillUpdatePayment() {
        final PaymentCollection newPayment = createPayment("111222333", null, "123456789");
        final PaymentCollection existingPayment = createPayment("999888777", "success", "12345");
        final PaymentCollection toBeReplacedPayment = createPayment("111222333", "created", "123");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final PaymentCollection updatedNewPayment = createPayment("111222333", "created", "123");

        final List<PaymentCollection> expectedPaymentsList = asList(existingPayment, updatedNewPayment);
        final List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }


    private PaymentCollection createPayment(String reference, String status, String collectionId) {
        final Payment payment = Payment.builder()
            .paymentReference(reference)
            .paymentStatus(status)
            .build();

        return PaymentCollection.builder().id(collectionId).value(payment).build();
    }
}
