package uk.gov.hmcts.reform.divorce.validation.rules.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReasonForDivorceDesertionDetailsTest {

    private ReasonForDivorceDesertionDetails rule;
    private DivorceSession divorceSession;

    @Before
    public void setup() {
        rule = new ReasonForDivorceDesertionDetails();
        divorceSession = new DivorceSession();
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsDesertionAndReasonForDivorceDesertionDetailsIsNull() {
        divorceSession.setReasonForDivorce("desertion");
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsNotDesertionAndReasonForDivorceDesertionDetailsIsNull() {
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void whenShouldReturnFalseWhenReasonForDivorceDesertionDetailsIsNotNull() {
        divorceSession.setReasonForDivorceDesertionDetails("Yes");

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("reasonForDivorceDesertionDetails can not be null or empty. Actual data is: null",
            rule.getResult().get(0));
    }
}