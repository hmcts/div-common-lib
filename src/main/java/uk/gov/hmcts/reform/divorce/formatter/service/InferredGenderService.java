package uk.gov.hmcts.reform.divorce.formatter.service;

import uk.gov.hmcts.reform.divorce.model.ccd.Gender;

public interface InferredGenderService {
    Gender getRespondentGender(String respondentRole);

    Gender getPetitionerGender(String isSameSexMarriage, String respondentRole);
}
