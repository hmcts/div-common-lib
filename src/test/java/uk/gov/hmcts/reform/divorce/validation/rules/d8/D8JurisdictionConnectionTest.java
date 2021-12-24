package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8JurisdictionConnectionTest {

    private D8JurisdictionConnection rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8JurisdictionConnection();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8JurisdictionConnectionIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnResultWhenD8JurisdictionConnectionIsEmpty() {
        List<String> d8JurisdictionConnection = new ArrayList<>();
        coreCaseData.setD8JurisdictionConnection(d8JurisdictionConnection);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8JurisdictionConnectionIsNotEmpty() {
        List<String> d8JurisdictionConnection = new ArrayList<>();
        d8JurisdictionConnection.add("test");
        coreCaseData.setD8JurisdictionConnection(d8JurisdictionConnection);

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8JurisdictionConnection can not be null or empty. Actual data is: null", result.get(0));
    }
}