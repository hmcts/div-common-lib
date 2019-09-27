package uk.gov.hmcts.reform.divorce.formatter.impl;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.formatter.service.InferredGenderService;
import uk.gov.hmcts.reform.divorce.formatter.service.impl.InferredGenderServiceImpl;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InferredGenderServiceImplUTest {
    private final InferredGenderService inferredGenderService = new InferredGenderServiceImpl();

    @Test
    public void shouldReturnMalePetitionerWhenSameSexAndDivorcingHusband() {
        final String respondentRole = "husband";
        assertEquals(Gender.MALE, inferredGenderService.getPetitionerGender("Yes", respondentRole));
    }

    @Test
    public void shouldReturnFemalePetitionerWhenSameSexAndDivorcingWife() {
        final String respondentRole = "wife";
        assertEquals(Gender.FEMALE, inferredGenderService.getPetitionerGender("Yes", respondentRole));
    }

    @Test
    public void shouldReturnFemalePetitionerWhenNotSameSexAndDivorcingHusband() {
        final String respondentRole = "husband";
        assertEquals(Gender.FEMALE, inferredGenderService.getPetitionerGender("No", respondentRole));
    }

    @Test
    public void shouldReturnMalePetitionerWhenNotSameSexAndDivorcingWife() {
        final String respondentRole = "wife";
        assertEquals(Gender.MALE, inferredGenderService.getPetitionerGender("No", respondentRole));
    }

    @Test
    public void shouldReturnMaleRespondentWhenRespondentIsHusband() {
        final String respondentRole = "husband";
        assertEquals(Gender.MALE, inferredGenderService.getRespondentGender(respondentRole));
    }

    @Test
    public void shouldReturnFemaleRespondentWhenRespondentIsWife() {
        final String respondentRole = "wife";
        assertEquals(Gender.FEMALE, inferredGenderService.getRespondentGender(respondentRole));
    }

    @Test
    public void shouldReturnNullIfRespondentRoleNotMatched() {
        assertNull(inferredGenderService.getPetitionerGender("Yes", "notValid"));
    }

    @Test
    public void shouldReturnNullIfIsSameSexNotMatched() {
        assertNull(inferredGenderService.getPetitionerGender("notValid", "husband"));
    }
}
