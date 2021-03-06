package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CaseLink {

    @JsonProperty(value = "CaseReference")
    private String caseReference;

}
