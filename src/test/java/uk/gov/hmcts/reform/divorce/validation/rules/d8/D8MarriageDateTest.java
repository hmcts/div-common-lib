package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8MarriageDateTest {

    private D8MarriageDate rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8MarriageDate();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateLessThanOneYearAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateMoreThanOneHundredYearsAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateIsInTheFuture() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenD8MarriageDateIsValid() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 2, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8MarriageDate can not be null or empty. Actual data is: null", result.get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateLessThanOneYearAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertEquals("D8MarriageDate can not be less than one year ago. Actual data is: ".concat(d8MarriageDate),
            result.get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateMoreThanOneHundredYearsAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertEquals("D8MarriageDate can not be more than 100 years ago. Actual data is: ".concat(d8MarriageDate),
            result.get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateIsInTheFuture() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        result = rule.execute(coreCaseData, result);

        assertEquals("D8MarriageDate can not be in the future. Actual data is: ".concat(d8MarriageDate),
            result.get(0));
    }
}