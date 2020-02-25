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
public class D8ReasonForDivorceAdulteryDetailsTest {

    private D8ReasonForDivorceAdulteryDetails rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorceAdulteryDetails();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsAdulteryAndWhenD8ReasonForDivorceAdulteryDetailsIsNull() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsNotAdulteryAndWhenD8ReasonForDivorceAdulteryDetailsIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertFalse(result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8ReasonForDivorceAdulteryDetailsIsNotNull() {
        coreCaseData.setD8ReasonForDivorceAdulteryDetails("Yes");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertFalse(result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8ReasonForDivorceAdulteryDetails can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}