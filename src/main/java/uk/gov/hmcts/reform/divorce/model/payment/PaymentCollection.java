package uk.gov.hmcts.reform.divorce.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCollection {
    private String id;
    private Payment value;

}
