package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SeparationFiveYearsStrategyUTest {
    private static final String SEPARATION_FIVE_YEARS = "separation-5-years";

    private SeparationStrategy separationStrategy;

    private SeparationFiveYearsStrategy separationFiveYearsStrategy;

    @Before
    public void setup() {
        separationStrategy = mock(SeparationStrategy.class);
        separationFiveYearsStrategy = new SeparationFiveYearsStrategy(separationStrategy);
    }

    @Test
    public void testSeparationFiveYearsStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_FIVE_YEARS);
        divorceSession.setDivorceWho("wife");
        divorceSession.setReasonForDivorceSeperationDate(
            LocalDate.of(2015, Month.JANUARY, 1)
        );

        final String derivedStatementOfCase = separationFiveYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("I have been separated from my wife for 5 years or more from the 01 January 2015."));
    }

    @Test
    public void testSeparationFiveYearsWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_FIVE_YEARS);

        final String derivedStatementOfCase = separationFiveYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been separated from my null for 5 years or more from the 01 January 1970."));
    }

    @Test
    public void testAcceptsMatchesFiiveYearSeparation() {
        assertTrue(separationFiveYearsStrategy.accepts("sEparAtion-5-Years"));
    }

    @Test
    public void testSetLivedApartFieldsFromDivorceSessionCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationFiveYearsStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);
    }

    @Test
    public void testSetLivedApartFieldsFromCoreCaseDataCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationFiveYearsStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);
    }
}
