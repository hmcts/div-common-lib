package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DnCaseData {

    @JsonProperty("DNApplicationSubmittedDate")
    private String dnApplicationSubmittedDate;

    @JsonProperty("PetitionChangedYesNoDN")
    private String petitionChangedYesNoDN;

    @JsonProperty("PetitionChangedDetailsDN")
    private String petitionChangedDetailsDN;

    @JsonProperty("ConfirmPetitionDN")
    private String confirmPetitionDN;

    @JsonProperty("DivorceCostsOptionDN")
    private String divorceCostsOptionDN;

    @JsonProperty("CostsDifferentDetails")
    private String costsDifferentDetails;

    @JsonProperty("statementOfTruthDN")
    private String statementOfTruthDN;

    @JsonProperty("AlternativeRespCorrAddress")
    private String alternativeRespCorrAddress;

    @JsonProperty("AdulteryLifeIntolerable")
    private String adulteryLifeIntolerable;

    @JsonProperty("AdulteryDateFoundOut")
    private String adulteryDateFoundOut;

    @JsonProperty("DNApplyForDecreeNisi")
    private String applyForDecreeNisi;

    @JsonProperty("AdulteryLivedApartSinceEventDN")
    private String adulteryLivedApartSinceEventDN;

    @JsonProperty("AdulteryTimeLivedTogetherDetailsDN")
    private String adulteryTimeLivedTogetherDetailsDN;

    @JsonProperty("BehaviourStillHappeningDN")
    private String behaviourStillHappeningDN;

    @JsonProperty("BehaviourMostRecentIncidentDateDN")
    private String behaviourMostRecentIncidentDateDN;

    @JsonProperty("BehaviourLivedApartSinceEventDN")
    private String behaviourLivedApartSinceEventDN;

    @JsonProperty("BehaviourTimeLivedTogetherDetailsDN")
    private String behaviourTimeLivedTogetherDetailsDN;

    @JsonProperty("DesertionLivedApartSinceEventDN")
    private String desertionLivedApartSinceEventDN;

    @JsonProperty("DesertionTimeLivedTogetherDetailsDN")
    private String desertionTimeLivedTogetherDetailsDN;

    @JsonProperty("SeparationLivedApartSinceEventDN")
    private String separationLivedApartSinceEventDN;

    @JsonProperty("SeparationTimeLivedTogetherDetailsDN")
    private String separationTimeLivedTogetherDetailsDN;

    @JsonProperty("DocumentsUploadedDN")
    private List<CollectionMember<Document>> documentsUploadedDN;

    @JsonProperty("DocumentsUploadedQuestionDN")
    private String documentsUploadedQuestionDN;

    @JsonProperty("DesertionAskedToResumeDN")
    private String desertionAskedToResumeDN;

    @JsonProperty("DesertionAskedToResumeDNRefused")
    private String desertionAskedToResumeDNRefused;

    @JsonProperty("DesertionAskedToResumeDNDetails")
    private String desertionAskedToResumeDNDetails;

    @JsonProperty("DnClarificationResponse")
    private List<CollectionMember<String>> dnClarificationResponse;

    @JsonProperty("DocumentsUploadedDnClarification")
    private List<CollectionMember<Document>> documentsUploadedDnClarification;

    // Caseworker only fields so frontend submission does not modify these
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalClarificationReason")
    private List<String> refusalClarificationReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalClarificationAdditionalInfo")
    private String refusalClarificationAdditionalInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("DnOutcomeCase")
    private String dnOutcomeCase;
}
