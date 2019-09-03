package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class NoExistingPaymentStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        PaymentCollection paymentCollection = new PaymentCollection();
        paymentCollection.setValue(newPayment);

        return Collections.singletonList(paymentCollection);
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.isNull(existingPayments);
    }

}
