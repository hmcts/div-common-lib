package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaseLink {

    @JsonProperty(value = "CaseReference")
    private String caseReference;

}
