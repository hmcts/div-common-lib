package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class D8JurisdictionConnectionNewPolicyTest {

    private D8JurisdictionConnectionNewPolicy rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8JurisdictionConnectionNewPolicy();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8JurisdictionConnectionNewPolicyIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8JurisdictionConnectionNewPolicyIsNotNull() {
        List<String> d8JurisdictionConnectionNewPolicy = new ArrayList<>();
        coreCaseData.setD8JurisdictionConnectionNewPolicy(d8JurisdictionConnectionNewPolicy);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8JurisdictionConnectionNewPolicy can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}