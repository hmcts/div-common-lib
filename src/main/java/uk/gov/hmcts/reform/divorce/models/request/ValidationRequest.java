package uk.gov.hmcts.reform.divorce.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationRequest {

    @JsonProperty(value = "formId", required = true)
    @NotBlank
    private final String formId;

    @JsonProperty(value = "sectionId")
    private final String section;

    @JsonProperty(value = "data", required = true)
    private final Object data;
}
