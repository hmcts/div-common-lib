package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPaymentCollection;

public class ExistingPaymentReferenceStrategyUTest {
    private final ExistingPaymentReferenceStrategy existingPaymentReferenceStrategy =
        new ExistingPaymentReferenceStrategy();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        final PaymentCollection newPayment = createPaymentCollection("111222333", "success", "123456789");
        final PaymentCollection existingPayment = createPaymentCollection("999888777", "success", "12345");
        final PaymentCollection toBeReplacedPayment = createPaymentCollection("111222333", "created", "123");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final PaymentCollection updatedNewPayment = createPaymentCollection("111222333", "success", "123");

        final List<PaymentCollection> expectedPaymentsList = asList(existingPayment, updatedNewPayment);
        final List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }

    @Test
    public void testExistingPaymentReferenceAndSubsetOfPaymentWillUpdatePayment() {
        final PaymentCollection newPayment = createPaymentCollection("111222333", null, "123456789");
        final PaymentCollection existingPayment = createPaymentCollection("999888777", "success", "12345");
        final PaymentCollection toBeReplacedPayment = createPaymentCollection("111222333", "created", "123");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final PaymentCollection updatedNewPayment = createPaymentCollection("111222333", "created", "123");

        final List<PaymentCollection> expectedPaymentsList = asList(existingPayment, updatedNewPayment);
        final List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }
}
