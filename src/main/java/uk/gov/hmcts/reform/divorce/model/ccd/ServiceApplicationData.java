package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServiceApplicationData {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Payment")
    private String payment;

    @JsonProperty("AddedDate")
    private String addedDate;

    @JsonProperty("DecisionDate")
    private String decisionDate;

    @JsonProperty("ReceivedDate")
    private String receivedDate;

    @JsonProperty("RefusalReason")
    private String refusalReason;

    @JsonProperty("ApplicationGranted")
    private String applicationGranted;
}
