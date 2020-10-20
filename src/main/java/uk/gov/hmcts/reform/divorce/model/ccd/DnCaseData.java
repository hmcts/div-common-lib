package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class DnCaseData extends DnRefusalCaseData {

    @JsonProperty("DNApplicationSubmittedDate")
    private String dnApplicationSubmittedDate;

    @JsonProperty("PetitionChangedYesNoDN")
    private String petitionChangedYesNoDN;

    @JsonProperty("PetitionChangedDetailsDN")
    private String petitionChangedDetailsDN;

    @JsonProperty("PetitionChangedDetailsDNTrans")
    private String petitionChangedDetailsDNTrans;

    @JsonProperty("PetitionChangedDetailsDNTransLang")
    private String petitionChangedDetailsDNTransLang;

    @JsonProperty("ConfirmPetitionDN")
    private String confirmPetitionDN;

    @JsonProperty("DivorceCostsOptionDN")
    private String divorceCostsOptionDN;

    @JsonProperty("CostsDifferentDetails")
    private String costsDifferentDetails;

    @JsonProperty("CostsDifferentDetailsTrans")
    private String costsDifferentDetailsTrans;

    @JsonProperty("CostsDifferentDetailsTransLang")
    private String costsDifferentDetailsTransLang;

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

    @JsonProperty("AdulteryTimeLivedTogetherDetailsDNTrans")
    private String adulteryTimeLivedTogetherDetailsDNTrans;

    @JsonProperty("AdulteryTimeLivedTogetherDetailsDNTransLang")
    private String adulteryTimeLivedTogetherDetailsDNTransLang;

    @JsonProperty("BehaviourStillHappeningDN")
    private String behaviourStillHappeningDN;

    @JsonProperty("BehaviourMostRecentIncidentDateDN")
    private String behaviourMostRecentIncidentDateDN;

    @JsonProperty("BehaviourLivedApartSinceEventDN")
    private String behaviourLivedApartSinceEventDN;

    @JsonProperty("BehaviourTimeLivedTogetherDetailsDN")
    private String behaviourTimeLivedTogetherDetailsDN;

    @JsonProperty("BehaviourTimeLivedTogetherDetailsDNTrans")
    private String behaviourTimeLivedTogetherDetailsDNTrans;

    @JsonProperty("BehaviourTimeLivedTogetherDetailsDNTransLang")
    private String behaviourTimeLivedTogetherDetailsDNTransLang;

    @JsonProperty("DesertionLivedApartSinceEventDN")
    private String desertionLivedApartSinceEventDN;

    @JsonProperty("DesertionTimeLivedTogetherDetailsDN")
    private String desertionTimeLivedTogetherDetailsDN;

    @JsonProperty("DesertionTimeLivedTogetherDetailsDNTrans")
    private String desertionTimeLivedTogetherDetailsDNTrans;

    @JsonProperty("DesertionTimeLivedTogetherDetailsDNTransLang")
    private String desertionTimeLivedTogetherDetailsDNTransLang;

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
}
