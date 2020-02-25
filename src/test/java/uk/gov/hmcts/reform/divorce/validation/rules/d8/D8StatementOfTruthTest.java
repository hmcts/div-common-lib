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
public class D8StatementOfTruthTest {

    private D8StatementOfTruth rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8StatementOfTruth();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8StatementOfTruthIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnTrueWhenD8StatementOfTruthIsNotYes() {
        coreCaseData.setD8StatementOfTruth("No");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8StatementOfTruthIsNotNull() {
        coreCaseData.setD8StatementOfTruth("Yes");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertFalse(result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8StatementOfTruth must be 'YES'. Actual data is: null", rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNo() {
        coreCaseData.setD8StatementOfTruth("No");

        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8StatementOfTruth must be 'YES'. Actual data is: No", rule.getResult().get(0));
    }
}