package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class Organisation {

    @JsonProperty("OrganisationID")
    private String organisationId;

    @JsonProperty("OrganisationName")
    private String organisationName;

}