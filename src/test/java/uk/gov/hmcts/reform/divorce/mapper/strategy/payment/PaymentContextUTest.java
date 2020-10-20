package uk.gov.hmcts.reform.divorce.mapper.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PaymentContextUTest {
    private final PaymentContext paymentContext = new PaymentContext();

    @Test
    public void testExistingPaymentReferenceAndPaymentReferenceWillReplacePayment() {
        final Payment newPayment = createPayment("111222333", "success");
        final PaymentCollection newPaymentCollection = createPaymentCollection("111222333", "success");
        final PaymentCollection existingPayment = createPaymentCollection("999888777", "success");
        final PaymentCollection toBeReplacedPayment = createPaymentCollection("111222333", "created");

        final List<PaymentCollection> existingPaymentsList = new ArrayList<>();
        existingPaymentsList.add(existingPayment);
        existingPaymentsList.add(toBeReplacedPayment);

        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setPayment(newPayment);
        divorceSession.setExistingPayments(existingPaymentsList);

        final List<PaymentCollection> expectedPaymentsList = Arrays.asList(existingPayment, newPaymentCollection);
        final List<PaymentCollection> returnedPaymentsList = paymentContext.getListOfPayments(divorceSession);

        assertThat(returnedPaymentsList, equalTo(expectedPaymentsList));
    }

    private PaymentCollection createPaymentCollection(String reference, String status) {
        return PaymentCollection.builder().value(createPayment(reference, status)).build();
    }

    private Payment createPayment(String reference, String status) {

        return Payment.builder()
            .paymentReference(reference)
            .paymentStatus(status)
            .build();
    }
}
