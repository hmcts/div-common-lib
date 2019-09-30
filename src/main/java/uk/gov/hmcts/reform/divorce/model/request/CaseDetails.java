package uk.gov.hmcts.reform.divorce.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseDetails {

    @JsonProperty("case_data")
    private CoreCaseData caseData;

    @JsonProperty("id")
    private String caseId;

    @JsonProperty("state")
    private String state;

}
