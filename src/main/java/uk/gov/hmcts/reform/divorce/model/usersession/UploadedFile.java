package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
