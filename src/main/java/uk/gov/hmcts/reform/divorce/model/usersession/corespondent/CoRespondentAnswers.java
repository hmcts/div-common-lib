package uk.gov.hmcts.reform.divorce.model.usersession.corespondent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoRespondentAnswers {

    private ContactInfo contactInfo;
    private AOS aos;

    /**
     * When the co-respondent defends the divorce they prepare a paper defence submission.
     * When this is received, a caseworker will update CCD to set that an answer has been received and the date it
     * arrived.
     * This information is captured in the answer attribute.
     */
    private Answer answer;

    private String confirmReadPetition;
    private String statementOfTruth;
    private String admitAdultery;
    private String defendsDivorce;

    private Costs costs;

}
