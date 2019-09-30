package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class D8JurisdictionConnectionTest {

    private D8JurisdictionConnection rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8JurisdictionConnection();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8JurisdictionConnectionIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8JurisdictionConnectionIsNotNull() {
        List<String> d8JurisdictionConnection = new ArrayList<>();
        coreCaseData.setD8JurisdictionConnection(d8JurisdictionConnection);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8JurisdictionConnection can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}