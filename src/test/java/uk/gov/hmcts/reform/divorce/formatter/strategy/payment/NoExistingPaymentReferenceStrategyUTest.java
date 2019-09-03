package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPaymentCollection;

public class NoExistingPaymentReferenceStrategyUTest {
    private final ExistingPaymentReferenceStrategy existingPaymentReferenceStrategy =
        new ExistingPaymentReferenceStrategy();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        PaymentCollection newPayment = createPaymentCollection("111222333", "success");
        PaymentCollection existingPayment = createPaymentCollection("999888777", "success");
        PaymentCollection toBeReplacedPayment = createPaymentCollection("111222333", "created");

        List<PaymentCollection> existingPaymentsList = new ArrayList<>();

        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        List<PaymentCollection> expectedPaymentsList = Arrays.asList(existingPayment, newPayment);
        List<PaymentCollection> returnedPaymentsList = existingPaymentReferenceStrategy
            .getCurrentPaymentsList(newPayment.getValue(), existingPaymentsList);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }
}
