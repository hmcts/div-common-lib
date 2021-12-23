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
public class D8ReasonForDivorceDesertionDetailsTest {

    private D8ReasonForDivorceDesertionDetails rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorceDesertionDetails();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenFactIsDesertionAndD8ReasonForDivorceDesertionDetailsIsNull() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenFactIsNotDesertionAndD8ReasonForDivorceDesertionDetailsIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8ReasonForDivorceDesertionDetailsIsNotNull() {
        coreCaseData.setD8ReasonForDivorceDesertionDetails("Yes");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        result = rule.execute(coreCaseData, result);


        assertEquals("D8ReasonForDivorceDesertionDetails can not be null or empty. Actual data is: null",
            result.get(0));
    }
}