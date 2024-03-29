package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class D8ReasonForDivorceAdulteryDetailsTest {

    private D8ReasonForDivorceAdulteryDetails rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorceAdulteryDetails();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenFactIsAdulteryAndWhenD8ReasonForDivorceAdulteryDetailsIsNull() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnResultWhenFactIsAdulteryAndWhenD8ReasonForDivorceAdulteryDetailsIsEmpty() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        coreCaseData.setD8ReasonForDivorceAdulteryDetails("");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenFactIsNotAdulteryAndWhenD8ReasonForDivorceAdulteryDetailsIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8ReasonForDivorceAdulteryDetailsIsNotNull() {
        coreCaseData.setD8ReasonForDivorceAdulteryDetails("Yes");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        result = rule.execute(coreCaseData, result);

        assertEquals("D8ReasonForDivorceAdulteryDetails can not be null or empty. Actual data is: null",
            result.get(0));
    }
}