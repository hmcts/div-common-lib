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
public class D8StatementOfTruthTest {

    private D8StatementOfTruth rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8StatementOfTruth();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8StatementOfTruthIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8StatementOfTruthIsNotYes() {
        coreCaseData.setD8StatementOfTruth("No");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8StatementOfTruthIsNotNull() {
        coreCaseData.setD8StatementOfTruth("Yes");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8StatementOfTruth must be 'YES'. Actual data is: null", result.get(0));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNo() {
        coreCaseData.setD8StatementOfTruth("No");

        result = rule.execute(coreCaseData, result);

        assertEquals("D8StatementOfTruth must be 'YES'. Actual data is: No", result.get(0));
    }
}