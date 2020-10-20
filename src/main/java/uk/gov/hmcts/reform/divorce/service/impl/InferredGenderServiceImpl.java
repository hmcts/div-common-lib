package uk.gov.hmcts.reform.divorce.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.service.InferredGenderService;

import java.util.Locale;
import java.util.Map;

import static uk.gov.hmcts.reform.divorce.utils.Constants.NO_VALUE;
import static uk.gov.hmcts.reform.divorce.utils.Constants.YES_VALUE;

@Component
public class InferredGenderServiceImpl implements InferredGenderService {
    private final Map<String, Gender> genderMap = ImmutableMap.of("husband", Gender.FEMALE, "wife", Gender.MALE);
    private final Map<String, Gender> roleGender = ImmutableMap.of("husband", Gender.MALE, "wife", Gender.FEMALE);

    @Override
    public Gender getRespondentGender(String respondentRole) {
        return roleGender.get(respondentRole.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public Gender getPetitionerGender(String isSameSexMarriage, String respondentRole) {
        if (YES_VALUE.equalsIgnoreCase(isSameSexMarriage)) {
            return roleGender.get(respondentRole.toLowerCase(Locale.ENGLISH));
        } else if (NO_VALUE.equalsIgnoreCase(isSameSexMarriage)) {
            return genderMap.get(respondentRole.toLowerCase(Locale.ENGLISH));
        }
        return null;
    }
}
