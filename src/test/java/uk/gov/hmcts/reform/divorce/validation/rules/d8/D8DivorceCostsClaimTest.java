package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

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
    public void shouldReturnNonEmptyResultWhenD8DivorceCostsClaimIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void resultShouldBeEmptyWhenD8DivorceCostsClaimIsNotNull() {
        coreCaseData.setD8DivorceCostsClaim("Yes");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWhenD8DivorceCostsClaimIsNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8DivorceCostsClaim can not be null or empty. Actual data is: null", result.get(0));
    }
}