package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DnRefusalCaseData {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("DnClarificationResponse")
    private List<CollectionMember<String>> dnClarificationResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("DnClarificationUploadDocuments")
    private List<CollectionMember<String>> dnClarificationUploadDocuments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("DocumentsUploadedDnClarification")
    private List<CollectionMember<Document>> documentsUploadedDnClarification;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalClarificationReason")
    private List<String> refusalClarificationReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalClarificationAdditionalInfo")
    private String refusalClarificationAdditionalInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalRejectionReason")
    private List<String> refusalRejectionReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalRejectionAdditionalInfo")
    private String refusalRejectionAdditionalInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("RefusalAdminErrorInfo")
    private String refusalAdminErrorInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("DnOutcomeCase")
    private String dnOutcomeCase;

    // Previous case data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("PreviousReasonsForDivorceRefusal")
    private List<String> previousReasonsForDivorceRefusal;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("PreviousRefusalRejectionReason")
    private List<String> previousRefusalRejectionReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("PreviousRefusalRejectionAdditionalInfo")
    private String previousRefusalRejectionAdditionalInfo;
}