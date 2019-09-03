package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "UploadedFileBuilder", toBuilder = true)
@JsonDeserialize(builder = UploadedFile.UploadedFileBuilder.class)
public class UploadedFile {
    private String id;
    private int createdBy;
    private Date createdOn;
    private int lastModifiedBy;
    private Date modifiedOn;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private String status;
    @JsonIgnore
    private String fileType;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UploadedFileBuilder {

    }
}
