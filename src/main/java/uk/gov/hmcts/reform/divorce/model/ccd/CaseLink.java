package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "CaseLinkBuilder", toBuilder = true)
@JsonDeserialize(builder = CaseLink.CaseLinkBuilder.class)
@Data
public class CaseLink {

    @JsonProperty(value = "CaseReference")
    private String caseReference;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CaseLinkBuilder {

    }
}
