package uk.gov.hmcts.reform.divorce.formatter.strategy.reasonfordivorce;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AdulteryStrategyUTest {
    private static final String ADULTERY = "adultery";

    private final AdulteryStrategy adulteryStrategy = new AdulteryStrategy();

    @Test
    public void testAdulteryWithoutKnowStatementOfCase() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(ADULTERY);
        divorceSession.setReasonForDivorceAdulteryKnowWhere("No");
        divorceSession.setReasonForDivorceAdulteryKnowWhen("No");
        divorceSession.setReasonForDivorceAdulteryWhereDetails("On a washing machine.");
        divorceSession.setReasonForDivorceAdulteryWhenDetails("Some time ago.");
        divorceSession.setReasonForDivorceAdulteryDetails("It hurts inside.");

        final String derivedStatementOfCase = adulteryStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo("It hurts inside."));
    }

    @Test
    public void testAdulteryWithKnowWhereStatementOfCase() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(ADULTERY);
        divorceSession.setReasonForDivorceAdulteryKnowWhere("Yes");
        divorceSession.setReasonForDivorceAdulteryKnowWhen("No");
        divorceSession.setReasonForDivorceAdulteryWhereDetails("On a washing machine.");
        divorceSession.setReasonForDivorceAdulteryWhenDetails("Some time ago.");
        divorceSession.setReasonForDivorceAdulteryDetails("It hurts inside.");

        final String derivedStatementOfCase = adulteryStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo("On a washing machine.\nIt hurts inside."));
    }

    @Test
    public void testAdulteryWithKnowWhenStatementOfCase() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(ADULTERY);
        divorceSession.setReasonForDivorceAdulteryKnowWhere("No");
        divorceSession.setReasonForDivorceAdulteryKnowWhen("Yes");
        divorceSession.setReasonForDivorceAdulteryWhereDetails("On a washing machine.");
        divorceSession.setReasonForDivorceAdulteryWhenDetails("Some time ago.");
        divorceSession.setReasonForDivorceAdulteryDetails("It hurts inside.");

        final String derivedStatementOfCase = adulteryStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo("Some time ago.\nIt hurts inside."));
    }

    @Test
    public void testAdulteryWithKnowBothStatementOfCase() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(ADULTERY);
        divorceSession.setReasonForDivorceAdulteryKnowWhere("Yes");
        divorceSession.setReasonForDivorceAdulteryKnowWhen("Yes");
        divorceSession.setReasonForDivorceAdulteryWhereDetails("On a washing machine.");
        divorceSession.setReasonForDivorceAdulteryWhenDetails("Some time ago.");
        divorceSession.setReasonForDivorceAdulteryDetails("It hurts inside.");

        final String derivedStatementOfCase = adulteryStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo("On a washing machine.\nSome time ago.\nIt hurts inside."));
    }

    @Test
    public void testAdulteryWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(ADULTERY);

        final String derivedStatementOfCase = adulteryStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(""));
    }
}
