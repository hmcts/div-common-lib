package uk.gov.hmcts.reform.divorce.mapper.strategy.payment;

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
            Payment updatedPayment = Payment.builder()
                .paymentReference(newPayment.getPaymentReference())
                .paymentAmount(Optional.ofNullable(newPayment.getPaymentAmount())
                    .orElse(existingPayment.getValue().getPaymentAmount()))
                .paymentChannel(Optional.ofNullable(newPayment.getPaymentChannel())
                    .orElse(existingPayment.getValue().getPaymentChannel()))
                .paymentDate(Optional.ofNullable(newPayment.getPaymentDate())
                    .orElse(existingPayment.getValue().getPaymentDate()))
                .paymentFeeId(Optional.ofNullable(newPayment.getPaymentFeeId())
                    .orElse(existingPayment.getValue().getPaymentFeeId()))
                .paymentSiteId(Optional.ofNullable(newPayment.getPaymentSiteId())
                    .orElse(existingPayment.getValue().getPaymentSiteId()))
                .paymentStatus(Optional.ofNullable(newPayment.getPaymentStatus())
                    .orElse(existingPayment.getValue().getPaymentStatus()))
                .paymentTransactionId(Optional.ofNullable(newPayment.getPaymentTransactionId())
                    .orElse(existingPayment.getValue().getPaymentTransactionId()))
                .build();

            return existingPayment.toBuilder().value(updatedPayment).build();
        }

        return existingPayment;
    }
}
