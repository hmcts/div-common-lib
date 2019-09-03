package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPayment;
import static uk.gov.hmcts.reform.divorce.formatter.strategy.payment.PaymentUtils.createPaymentCollection;

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
}
