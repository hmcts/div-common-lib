package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class D8ReasonForDivorceTest {

    private D8ReasonForDivorce rule;
    private CoreCaseData coreCaseData;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorce();
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void whenShouldReturnTrueWhenD8ReasonForDivorceIsNull() {
        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation2YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-2-years");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsDesertionButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenTwoAndFiveYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenD8ReasonForDivorceIsInvalid() {
        coreCaseData.setD8ReasonForDivorce("Yes");

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsAdulteryAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsBehaviourAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("unreasonable-behaviour");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation2YearsAndMarriageDateIsMoreThanTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-2-years");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsDesertionAndMarriageDateIsMoreThanTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation5YearsAndMarriageDateIsMoreThan5YearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.getFormattedDate(Instant.now().minus(365 * 6, ChronoUnit.DAYS)));

        rule.setCoreCaseData(coreCaseData);
        boolean result = rule.when();

        assertEquals(false, result);
    }


    @Test
    public void thenShouldReturnErrorMessageWithNullWhenD8ReasonForDivorceIsNotSet() {
        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8ReasonForDivorce can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWithInvalidWhenD8ReasonForDivorceIsInvalid() {
        coreCaseData.setD8ReasonForDivorce("Yes");

        rule.setCoreCaseData(coreCaseData);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("D8ReasonForDivorce is invalid for the current date of marriage. Actual data is: Yes",
            rule.getResult().get(0));
    }
}