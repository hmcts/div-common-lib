package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

public class PaymentUtils {

    private PaymentUtils() {
    }

    public static PaymentCollection createPaymentCollection(String reference, String status) {
        return createPaymentCollection(reference, status, null);
    }

    public static PaymentCollection createPaymentCollection(String reference, String status, String collectionId) {
        Payment payment = createPayment(reference, status);

        PaymentCollection result = new PaymentCollection();
        result.setValue(payment);
        result.setId(collectionId);

        return result;
    }

    public static PaymentCollection createPaymentCollection(Payment value) {
        PaymentCollection result = new PaymentCollection();
        result.setValue(value);

        return result;
    }

    public static Payment createPayment(String reference, String status) {
        final Payment payment = new Payment();
        payment.setPaymentReference(reference);
        payment.setPaymentStatus(status);

        return payment;
    }

}
