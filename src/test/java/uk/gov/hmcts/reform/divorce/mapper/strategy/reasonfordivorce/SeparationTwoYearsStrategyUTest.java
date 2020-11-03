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

public class SeparationTwoYearsStrategyUTest {
    private static final String SEPARATION_TWO_YEARS = "separation-2-years";

    private SeparationStrategy separationStrategy;

    private SeparationTwoYearsStrategy separationTwoYearsStrategy;

    @Before
    public void setup() {
        separationStrategy = mock(SeparationStrategy.class);
        separationTwoYearsStrategy = new SeparationTwoYearsStrategy(separationStrategy);
    }

    @Test
    public void testSeparationTwoYearsStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_TWO_YEARS);
        divorceSession.setDivorceWho("husband");
        divorceSession.setReasonForDivorceSeperationDate(
            LocalDate.of(2015, Month.JANUARY, 1)
        );

        final String derivedStatementOfCase = separationTwoYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase,
            equalTo("I have been separated from my husband for 2 years or more from the 01 January 2015."));
    }

    @Test
    public void testSeparationTwoYearsWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(SEPARATION_TWO_YEARS);

        final String derivedStatementOfCase = separationTwoYearsStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been separated from my null for 2 years or more from the 01 January 1970."));
    }

    @Test
    public void testAcceptsMatchesTwoYearSeparation() {
        assertTrue(separationTwoYearsStrategy.accepts("sEparAtion-2-Years"));
    }

    @Test
    public void testSetLivedApartFieldsFromDivorceSessionCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationTwoYearsStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);
    }

    @Test
    public void testSetLivedApartFieldsFromCoreCaseDataCallsSeparationStrategy() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        separationTwoYearsStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        Mockito.verify(separationStrategy).setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);
    }
}
