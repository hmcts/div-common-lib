package uk.gov.hmcts.reform.divorce.model.documentupdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratedDocumentInfo {
    private String url;
    private String documentType;
    private String fileName;
}
