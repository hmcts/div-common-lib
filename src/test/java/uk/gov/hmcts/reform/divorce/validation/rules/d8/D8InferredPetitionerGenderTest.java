package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8InferredPetitionerGenderTest {

    private D8InferredPetitionerGender rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8InferredPetitionerGender();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenD8InferredPetitionerGenderIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenD8InferredPetitionerGenderIsNotNull() {
        coreCaseData.setD8InferredPetitionerGender(Gender.MALE);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8InferredPetitionerGender can not be null or empty. Actual data is: null", result.get(0));
    }
}