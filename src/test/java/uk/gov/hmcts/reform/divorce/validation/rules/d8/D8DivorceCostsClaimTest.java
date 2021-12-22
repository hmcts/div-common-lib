package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8DivorceCostsClaimTest {

    private D8DivorceCostsClaim rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8DivorceCostsClaim();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenD8DivorceCostsClaimIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

//    @Test
//    public void whenShouldReturnFalseWhenD8DivorceCostsClaimIsNotNull() {
//        coreCaseData.setD8DivorceCostsClaim("Yes");
//
//        rule.setCoreCaseData(coreCaseData);
//        boolean result = rule.when();
//
//        assertEquals(false, result);
//    }
//
//    @Test
//    public void thenShouldReturnErrorMessageWithNull() {
//        rule.setCoreCaseData(coreCaseData);
//
//        rule.setResult(new ArrayList<>());
//        rule.then();
//
//        assertEquals("D8DivorceCostsClaim can not be null or empty. Actual data is: null", rule.getResult().get(0));
//    }
}