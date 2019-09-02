package uk.gov.hmcts.reform.divorce.formatter.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.formatter.service.InferredGenderService;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;

import java.util.Locale;
import java.util.Map;

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
        if ("yes".equalsIgnoreCase(isSameSexMarriage)) {
            return roleGender.get(respondentRole.toLowerCase(Locale.ENGLISH));
        } else if ("no".equalsIgnoreCase(isSameSexMarriage)) {
            return genderMap.get(respondentRole.toLowerCase(Locale.ENGLISH));
        }
        return null;
    }
}
