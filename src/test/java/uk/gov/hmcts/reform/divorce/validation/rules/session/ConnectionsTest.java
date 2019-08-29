package uk.gov.hmcts.reform.divorce.validation.rules.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class ConnectionsTest {

    private Connections rule;
    private DivorceSession divorceSession;
    private uk.gov.hmcts.reform.divorce.models.request.Connections connections;

    @Before
    public void setup() {
        rule = new Connections();
        divorceSession = new DivorceSession();
    }

    @Test
    public void whenShouldReturnTrueWhenConnectionsIsNull() {
        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(true, result);
    }

    @Test
    public void whenShouldReturnFalseWhenConnectionsIsNotNull() {
        connections = new uk.gov.hmcts.reform.divorce.models.request.Connections();
        divorceSession.setConnections(connections);

        rule.setDivorceSession(divorceSession);
        boolean result = rule.when();

        assertEquals(false, result);
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        rule.setDivorceSession(divorceSession);

        rule.setResult(new ArrayList<>());
        rule.then();

        assertEquals("connections can not be null or empty. Actual data is: null", rule.getResult().get(0));
    }
}