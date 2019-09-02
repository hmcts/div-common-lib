package uk.gov.hmcts.reform.divorce.model.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PaymentCollection {
    private String id;
    private Payment value;
}
