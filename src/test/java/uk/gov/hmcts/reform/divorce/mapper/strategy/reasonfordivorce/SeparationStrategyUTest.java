package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.junit.Assert.assertEquals;

public class SeparationStrategyUTest {

    private SeparationStrategy separationStrategy;

    @Before
    public void setup() {
        separationStrategy = new SeparationStrategy();
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
        separationStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);

        //then
        assertEquals("YES", coreCaseData.getSeparationLivedApartEntireTime());
        assertEquals("NO", coreCaseData.getSeparationLivedTogetherMoreTimeThanPermitted());
        assertEquals("1 month", coreCaseData.getSeparationTimeTogetherPermitted());
    }

    @Test
    public void testLivedApartFieldsFromCoreCaseDataSetsFieldsoOnDivorceSessionObject() {
        //given
        CoreCaseData coreCaseData = new CoreCaseData();

        coreCaseData.setSeparationLivedApartEntireTime("YES");
        coreCaseData.setSeparationLivedTogetherMoreTimeThanPermitted("NO");
        coreCaseData.setSeparationTimeTogetherPermitted("1 month");

        DivorceSession divorceSession = new DivorceSession();

        //when
        separationStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);

        //then
        assertEquals("Yes", divorceSession.getLivedApartEntireTime());
        assertEquals("No", divorceSession.getLivedTogetherMoreTimeThanPermitted());
        assertEquals("1 month", divorceSession.getTimeLivedTogetherPermitted());
    }
}
