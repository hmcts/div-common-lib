package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class ReasonForDivorceContextUTest {
    private static final String SEPARATION_FIVE_YEARS = "separation-5-years";

    private ReasonForDivorceContext reasonForDivorceContext;

    @Before
    public void setup() {
        SeparationStrategy separationStrategy = mock(SeparationStrategy.class);
        reasonForDivorceContext = new ReasonForDivorceContext(separationStrategy);
    }

    @Test
    public void testSeparationFiveYearsStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_FIVE_YEARS);
        divorceSession.setDivorceWho("wife");
        divorceSession.setReasonForDivorceSeperationDate(
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2015-01-01T00:00:00.000Z"));

        final String derivedStatementOfCase = reasonForDivorceContext.deriveStatementOfWork(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("I have been separated from my wife for 5 years or more from the 01 January 2015."));
    }

}
