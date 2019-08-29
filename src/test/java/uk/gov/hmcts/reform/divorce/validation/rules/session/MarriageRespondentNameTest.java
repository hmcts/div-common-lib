package uk.gov.hmcts.reform.divorce.validation.rules.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class MarriageRespondentNameTest {

    private MarriageRespondentName rule;
    private DivorceSession divorceSession;

    @Before
    public void setup() {
        rule = new MarriageRespondentName();
        divorceSession = new DivorceSession();
    }

    @Test
    public void whenShouldReturnTrueWhenMarriageRespondentNameIsNull() {
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenMarriageRespondentNameIsNotNull() {
        divorceSession.setMarriageRespondentName("Yes");

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("marriageRespondentName can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}