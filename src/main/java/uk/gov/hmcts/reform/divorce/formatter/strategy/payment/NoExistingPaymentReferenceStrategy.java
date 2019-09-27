package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.List;
import java.util.Objects;

@Component
public class NoExistingPaymentReferenceStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        PaymentCollection paymentCollection = new PaymentCollection();
        paymentCollection.setValue(newPayment);

        existingPayments.add(paymentCollection);

        return existingPayments;
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.nonNull(existingPayments) && existingPayments.stream()
            .noneMatch(payment -> payment.getValue()
                .getPaymentReference()
                .equals(newPayment.getPaymentReference()));
    }

}
