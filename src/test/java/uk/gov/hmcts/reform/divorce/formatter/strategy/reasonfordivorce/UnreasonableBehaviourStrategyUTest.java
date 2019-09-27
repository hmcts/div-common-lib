package uk.gov.hmcts.reform.divorce.formatter.strategy.reasonfordivorce;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UnreasonableBehaviourStrategyUTest {
    private static final String UNREASONABLE_BEHAVIOUR = "unreasonable-behaviour";

    private final UnreasonableBehaviourStrategy unreasonableBehaviourStrategy = new UnreasonableBehaviourStrategy();

    @Test
    public void testUnreasonableBehaviourStatementOfCase() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(UNREASONABLE_BEHAVIOUR);

        final List<String> behaviourDetails = Arrays.asList("My wife is having an affair this week.",
            "This is totally not cool.");

        divorceSession.setReasonForDivorceBehaviourDetails(behaviourDetails);

        final String derivedStatementOfCase = unreasonableBehaviourStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("My wife is having an affair this week.\nThis is totally not cool."));
    }
}
