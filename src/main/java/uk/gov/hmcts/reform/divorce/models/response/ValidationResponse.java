package uk.gov.hmcts.reform.divorce.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponse {

    private String validationStatus;

    private List<String> errors;

    private List<String> warnings;

    @JsonIgnore
    public boolean isValid() {
        boolean hasNoErrors = this.getErrors() == null || this.getErrors().isEmpty();
        boolean hasNoWarnings = this.getWarnings() == null || this.getWarnings().isEmpty();

        return hasNoErrors && hasNoWarnings;
    }

}
