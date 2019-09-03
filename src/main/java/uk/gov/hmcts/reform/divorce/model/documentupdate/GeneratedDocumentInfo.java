package uk.gov.hmcts.reform.divorce.model.documentupdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "GeneratedDocumentInfoBuilder", toBuilder = true)
@JsonDeserialize(builder = GeneratedDocumentInfo.GeneratedDocumentInfoBuilder.class)
@Data
@JsonIgnoreProperties
public class GeneratedDocumentInfo {
    private String url;
    private String documentType;
    private String fileName;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GeneratedDocumentInfoBuilder {

    }
}
