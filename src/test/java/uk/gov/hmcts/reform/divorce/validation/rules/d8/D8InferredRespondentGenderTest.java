package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.CoreCaseData;
import uk.gov.hmcts.reform.divorce.models.request.Gender;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class D8InferredRespondentGenderTest {

    private D8InferredRespondentGender rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8InferredRespondentGender();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8InferredRespondentGenderIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8InferredRespondentGenderIsNotNull() {
        coreCaseData.setD8InferredRespondentGender(Gender.FEMALE);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8InferredRespondentGender can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}