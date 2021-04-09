package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class OrganisationPolicy {

    public OrganisationPolicy(String organisationId, String organisationName, String organisationPolicyReference, String assignedRole) {
        this.organisation = new Organisation(organisationId, organisationName);
        this.organisationPolicyReference = organisationPolicyReference;
        this.previousOrganisations = new ArrayList<>();
        this.organisationPolicyCaseAssignedRole = assignedRole;
    }

    @JsonProperty("Organisation")
    private Organisation organisation;

    @JsonProperty("OrgPolicyReference")
    private String organisationPolicyReference;

    @JsonProperty("PreviousOrganisations")
    private List<?> previousOrganisations;

    @JsonProperty("OrgPolicyCaseAssignedRole")
    private String organisationPolicyCaseAssignedRole;

}