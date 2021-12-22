package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)

public class D8ReasonForDivorceTest {

    private D8ReasonForDivorce rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorce();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenD8ReasonForDivorceIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation2YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-2-years");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsDesertionButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenTwoAndFiveYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnTrueWhenD8ReasonForDivorceIsInvalid() {
        coreCaseData.setD8ReasonForDivorce("Yes");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsAdulteryAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("adultery");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsBehaviourAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("unreasonable-behaviour");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(500, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation2YearsAndMarriageDateIsMoreThanTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-2-years");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsDesertionAndMarriageDateIsMoreThanTwoYearsAgo() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation5YearsAndMarriageDateIsMoreThan5YearsAgo() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(365 * 6, ChronoUnit.DAYS)));

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }


    @Test
    public void thenShouldReturnErrorMessageWithNullWhenD8ReasonForDivorceIsNotSet() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8ReasonForDivorce can not be null or empty. Actual data is: null", result.get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWithInvalidWhenD8ReasonForDivorceIsInvalid() {
        coreCaseData.setD8ReasonForDivorce("Yes");

        result = rule.execute(coreCaseData, result);

        assertEquals("D8ReasonForDivorce is invalid for the current date of marriage. Actual data is: Yes",
            result.get(0));
    }
}