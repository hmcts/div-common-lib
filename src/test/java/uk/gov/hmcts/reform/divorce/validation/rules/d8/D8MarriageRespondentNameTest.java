package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class D8MarriageRespondentNameTest {

    private D8MarriageRespondentName rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8MarriageRespondentName();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8MarriageRespondentNameIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8MarriageRespondentNameIsNotNull() {
        coreCaseData.setD8MarriageRespondentName("Yes");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnResultWhenRespondentNameIsEmpty() {
        coreCaseData.setD8MarriageRespondentName("");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8MarriageRespondentName can not be null or empty. Actual data is: null", result.get(0));
    }
}