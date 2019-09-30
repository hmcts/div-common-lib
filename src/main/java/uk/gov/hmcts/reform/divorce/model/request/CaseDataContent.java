package uk.gov.hmcts.reform.divorce.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import uk.gov.hmcts.reform.divorce.model.ccd.Event;

import java.util.Map;

@Data
public class CaseDataContent {
    private Event event;

    private Map<String, JsonNode> data;

    @JsonProperty("security_classification")
    private Map<String, JsonNode> securityClassification;

    @JsonProperty("event_token")
    private String token;

    @JsonProperty("ignore_warning")
    private Boolean ignoreWarning;

}
