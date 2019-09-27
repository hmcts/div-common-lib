package uk.gov.hmcts.reform.divorce.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactInfo {

    private String emailAddress;
    private String consentToReceivingEmails;
    private String contactMethodIsDigital;
    private String phoneNumber;

}
