package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class D8PetitionerContactDetailsConfidentialTest {

    private D8PetitionerContactDetailsConfidential rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8PetitionerContactDetailsConfidential();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8PetitionerContactDetailsConfidentialIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8PetitionerContactDetailsConfidentialIsNotNull() {
        coreCaseData.setD8PetitionerContactDetailsConfidential("Yes");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8PetitionerContactDetailsConfidential can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}