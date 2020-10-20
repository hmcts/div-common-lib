package uk.gov.hmcts.reform.divorce.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AOS {

    private String received;

    private String letterHolderId;

    @JsonFormat(pattern = SIMPLE_DATE_FORMAT)
    private Date dateReceived;

    @JsonFormat(pattern = SIMPLE_DATE_FORMAT)
    private Date dueDate;

    private String linked;

    @JsonFormat(pattern = SIMPLE_DATE_FORMAT)
    private Date linkedDate;

}
