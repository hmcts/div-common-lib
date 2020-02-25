package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)

public class D8RespondentFirstNameTest {

    private D8RespondentFirstName rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8RespondentFirstName();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8RespondentFirstNameIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8RespondentFirstNameIsNotNull() {
        coreCaseData.setD8RespondentFirstName("Yes");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertFalse(result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8RespondentFirstName can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }
}