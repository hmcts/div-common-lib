package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class D8MarriageDateTest {

    private D8MarriageDate rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8MarriageDate();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateLessThanOneYearAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateMoreThanOneHundredYearsAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnTrueWhenD8MarriageDateIsInTheFuture() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertTrue(result);
    }

    @Test
    public void whenShouldReturnFalseWhenD8MarriageDateIsValid() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 2, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertFalse(result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8MarriageDate can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateLessThanOneYearAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8MarriageDate can not be less than one year ago. Actual data is: ".concat(d8MarriageDate),
            rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateMoreThanOneHundredYearsAgo() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8MarriageDate can not be more than 100 years ago. Actual data is: ".concat(d8MarriageDate),
            rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenD8MarriageDateIsInTheFuture() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS));
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8MarriageDate can not be in the future. Actual data is: ".concat(d8MarriageDate),
            rule.getResult().get(0));
    }
}