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
public class MarriageDateTest {

    private MarriageDate rule;
    private DivorceSession divorceSession;

    @Before
    public void setup() {
        rule = new MarriageDate();
        divorceSession = new DivorceSession();
    }

    @Test
    public void whenShouldReturnTrueWhenMarriageDateIsNull() {
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenMarriageDateLessThanOneYearAgo() {
        Date marriageDate = Date.from(Instant.now().minus(100, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenMarriageDateMoreThanOneHundredYearsAgo() {
        Date marriageDate = Date.from(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnTrueWhenMarriageDateIsInTheFuture() {
        Date marriageDate = Date.from(Instant.now().plus(100, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenMarriageDateIsValid() {
        Date marriageDate = Date.from(Instant.now().minus(365 * 2, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("marriageDate can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenMarriageDateLessThanOneYearAgo() {
        Date marriageDate = Date.from(Instant.now().minus(100, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("marriageDate can not be less than one year ago. Actual data is: "
            .concat(marriageDate.toString()), rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenMarriageDateMoreThanOneHundredYearsAgo() {
        Date marriageDate = Date.from(Instant.now().minus(365 * 105, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("marriageDate can not be more than 100 years ago. Actual data is: "
            .concat(marriageDate.toString()), rule.getResult().get(0));
    }

    @Test
    public void thenShouldReturnErrorMessageWhenMarriageDateIsInTheFuture() {
        Date marriageDate = Date.from(Instant.now().plus(100, ChronoUnit.DAYS));
        divorceSession.setMarriageDate(marriageDate);

        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("marriageDate can not be in the future. Actual data is: "
            .concat(marriageDate.toString()), rule.getResult().get(0));
    }
}