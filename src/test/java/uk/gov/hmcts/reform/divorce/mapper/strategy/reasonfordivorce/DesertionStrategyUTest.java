package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

public class DesertionStrategyUTest {
    private static final String DESERTION = "desertion";

    private DesertionStrategy desertionStrategy;

    @Before
    public void setup() {
        desertionStrategy = new DesertionStrategy();
    }

    @Test
    public void testDesertionStatementOfCase() throws ParseException {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(DESERTION);
        divorceSession.setDivorceWho("husband");
        divorceSession
            .setReasonForDivorceDesertionDate(LocalDate.of(2015, Month.FEBRUARY, 1));
        divorceSession.setReasonForDivorceDesertionDetails("He told me that he is going to his mother.");

        final String derivedStatementOfCase = desertionStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been deserted by my husband on the 01 February 2015.\nHe told me that he is going to his mother."));
    }

    @Test
    public void testDesertionWithNullValuesShouldNotThrowException() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorce(DESERTION);

        final String derivedStatementOfCase = desertionStrategy.deriveStatementOfCase(divorceSession);

        assertThat(derivedStatementOfCase, equalTo(
            "I have been deserted by my null on the 01 January 1970.\n"));
    }

    @Test
    public void testSetLivedApartFieldsFromDivorceSessionSetsFieldsOnCoreCaseDataObject() {
        //given
        DivorceSession divorceSession = new DivorceSession();
        divorceSession.setLivedApartEntireTime("Yes");
        divorceSession.setLivedTogetherMoreTimeThanPermitted("No");
        divorceSession.setTimeLivedTogetherPermitted("1 month");

        CoreCaseData coreCaseData = new CoreCaseData();

        //when
        desertionStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        //then
        assertEquals("YES", coreCaseData.getDesertionLivedApartEntireTime());
        assertEquals("NO", coreCaseData.getDesertionLivedTogetherMoreTimeThanPermitted());
        assertEquals("1 month", coreCaseData.getDesertionTimeTogetherPermitted());
    }

    @Test
    public void testLivedApartFieldsFromCoreCaseDataSetsFieldsoOnDivorceSessionObject() {
        //given
        CoreCaseData coreCaseData = new CoreCaseData();

        coreCaseData.setDesertionLivedApartEntireTime("YES");
        coreCaseData.setDesertionLivedTogetherMoreTimeThanPermitted("NO");
        coreCaseData.setDesertionTimeTogetherPermitted("1 month");

        DivorceSession divorceSession = new DivorceSession();

        //when
        desertionStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        //then
        assertEquals("Yes", divorceSession.getLivedApartEntireTime());
        assertEquals("No", divorceSession.getLivedTogetherMoreTimeThanPermitted());
        assertEquals("1 month", divorceSession.getTimeLivedTogetherPermitted());
    }
}
