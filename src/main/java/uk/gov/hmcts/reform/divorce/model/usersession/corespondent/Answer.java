package uk.gov.hmcts.reform.divorce.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {

    private String received;

    @JsonFormat(pattern = SIMPLE_DATE_FORMAT)
    private Date dateReceived;

}