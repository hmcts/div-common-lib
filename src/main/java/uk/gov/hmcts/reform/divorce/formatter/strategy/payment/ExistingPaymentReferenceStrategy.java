package uk.gov.hmcts.reform.divorce.formatter.strategy.payment;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExistingPaymentReferenceStrategy implements PaymentStrategy {

    @Override
    public List<PaymentCollection> getCurrentPaymentsList(Payment newPayment,
                                                          List<PaymentCollection> existingPayments) {
        return existingPayments.stream()
            .map(payment -> mapExistingPayment(payment, newPayment))
            .collect(Collectors.toList());
    }

    @Override
    public boolean accepts(Payment newPayment, List<PaymentCollection> existingPayments) {
        return Objects.nonNull(existingPayments) && existingPayments.stream()
            .anyMatch(
                payment -> payment.getValue().getPaymentReference().equals(newPayment.getPaymentReference()));
    }

    private PaymentCollection mapExistingPayment(PaymentCollection existingPayment, Payment newPayment) {
        if (existingPayment.getValue().getPaymentReference().equals(newPayment.getPaymentReference())) {
            // Overwrite non-null values only, as we could get a subset of payment data to be updated
            Payment updatedPayment = new Payment();
            updatedPayment.setPaymentReference(newPayment.getPaymentReference());
            updatedPayment.setPaymentAmount(Optional.ofNullable(newPayment.getPaymentAmount())
                .orElse(existingPayment.getValue().getPaymentAmount()));
            updatedPayment.setPaymentChannel(Optional.ofNullable(newPayment.getPaymentChannel())
                .orElse(existingPayment.getValue().getPaymentChannel()));
            updatedPayment.setPaymentDate(Optional.ofNullable(newPayment.getPaymentDate())
                .orElse(existingPayment.getValue().getPaymentDate()));
            updatedPayment.setPaymentFeeId(Optional.ofNullable(newPayment.getPaymentFeeId())
                .orElse(existingPayment.getValue().getPaymentFeeId()));
            updatedPayment.setPaymentSiteId(Optional.ofNullable(newPayment.getPaymentSiteId())
                .orElse(existingPayment.getValue().getPaymentSiteId()));
            updatedPayment.setPaymentStatus(Optional.ofNullable(newPayment.getPaymentStatus())
                .orElse(existingPayment.getValue().getPaymentStatus()));
            updatedPayment.setPaymentTransactionId(Optional.ofNullable(newPayment.getPaymentTransactionId())
                .orElse(existingPayment.getValue().getPaymentTransactionId()));
            existingPayment.setValue(updatedPayment);
        }

        return existingPayment;
    }
}
