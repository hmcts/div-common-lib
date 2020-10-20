package uk.gov.hmcts.reform.divorce.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class PaymentCollection {

    private String id;
    private Payment value;

}
