package uk.gov.hmcts.reform.divorce.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Costs {

    private String agreeToCosts;
    private String reason;

}
