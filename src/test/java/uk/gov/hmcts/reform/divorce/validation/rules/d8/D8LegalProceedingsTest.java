package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8LegalProceedingsTest {

    private D8LegalProceedings rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8LegalProceedings();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8LegalProceedingsIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnResultWhenD8LegalProceedingsIsEmpty() {
        coreCaseData.setD8LegalProceedings(" ");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnFalseWhenD8LegalProceedingsIsNotNull() {
        coreCaseData.setD8LegalProceedings("Yes");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8LegalProceedings can not be null or empty. Actual data is: null", result.get(0));
    }
}