package uk.gov.hmcts.reform.divorce.models.request;

import lombok.Data;

@Data
public class PaymentCollection {

    private String id;

    private Payment value;

}
