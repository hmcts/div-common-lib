package uk.gov.hmcts.reform.divorce.validation.rules.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReasonForDivorceTest {

    private ReasonForDivorce rule;
    private DivorceSession divorceSession;

    @Before
    public void setup() {
        rule = new ReasonForDivorce();
        divorceSession = new DivorceSession();
    }

    @Test
    public void whenShouldReturnTrueWhenReasonForDivorceIsNull() {
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation2YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        divorceSession.setReasonForDivorce("separation-2-years");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsDesertionButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        divorceSession.setReasonForDivorce("desertion");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenOneAndTwoYearsAgo() {
        divorceSession.setReasonForDivorce("separation-5-years");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeparation5YearsButMarriageDateIsBetweenTwoAndFiveYearsAgo() {
        divorceSession.setReasonForDivorce("separation-5-years");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenReasonForDivorceIsInvalid() {
        divorceSession.setReasonForDivorce("Yes");

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsAdulteryAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        divorceSession.setReasonForDivorce("adultery");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsBehaviourAndMarriageDateIsBetweenOneAndTwoYearsAgo() {
        divorceSession.setReasonForDivorce("unreasonable-behaviour");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(500, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation2YearsAndMarriageDateIsMoreThanTwoYearsAgo() {
        divorceSession.setReasonForDivorce("separation-2-years");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsDesertionAndMarriageDateIsMoreThanTwoYearsAgo() {
        divorceSession.setReasonForDivorce("desertion");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(365 * 3, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsSeparation5YearsAndMarriageDateIsMoreThan5YearsAgo() {
        divorceSession.setReasonForDivorce("separation-5-years");
        divorceSession.setMarriageDate(Date.from(Instant.now().minus(365 * 6, ChronoUnit.DAYS)));

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }


    @Test
    public void thenShouldReturnErrorMessageWithNullWhenReasonForDivorceIsNotSet() {
        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("reasonForDivorce can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWithInvalidWhenReasonForDivorceIsInvalid() {
        divorceSession.setReasonForDivorce("Yes");

        rule.setDivorceSession(divorceSession);


        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("reasonForDivorce is invalid for the current date of marriage. Actual data is: Yes",
            rule.getResult().get(0));
    }
}