package uk.gov.hmcts.reform.divorce.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.service.impl.SeparationDateServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SeparationDateServiceImplUTest {

    private static final SeparationDateService classUnderTest = new SeparationDateServiceImpl();

    private final LocalDate separationDate;
    private final LocalDate decisionDate;
    private final LocalDate livingApartDate;
    private final LocalDate expected;

    public SeparationDateServiceImplUTest(LocalDate separationDate, LocalDate decisionDate, LocalDate livingApartDate, LocalDate expected) {
        this.separationDate = separationDate;
        this.decisionDate = decisionDate;
        this.livingApartDate = livingApartDate;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<LocalDate[]> data() {
        return Arrays.asList(new LocalDate[][] {
            {null, null, null, null},
            {getDate("2015-01-01"), null, null, getDate("2015-01-01")},
            {null, getDate("2015-01-01"), null, getDate("2015-01-01")},
            {null, null, getDate("2015-01-01"), getDate("2015-01-01")},
            {getDate("2015-01-01"), getDate("2015-01-02"), null, getDate("2015-01-02")},
            {getDate("2015-01-02"), getDate("2015-01-01"), null, getDate("2015-01-01")},
            {getDate("2015-01-01"), null, getDate("2015-01-02"), getDate("2015-01-02")},
            {getDate("2015-01-02"), null, getDate("2015-01-01"), getDate("2015-01-01")},
            {null, getDate("2015-01-01"), getDate("2015-01-02"), getDate("2015-01-02")},
            {null, getDate("2015-01-02"), getDate("2015-01-01"), getDate("2015-01-02")},
            {getDate("2015-01-01"), getDate("2015-01-01"), getDate("2015-01-01"), getDate("2015-01-01")},
            {getDate("2015-01-01"), getDate("2015-01-02"), getDate("2015-01-03"), getDate("2015-01-03")},
            {getDate("2015-01-03"), getDate("2015-01-02"), getDate("2015-01-01"), getDate("2015-01-02")},
            {getDate("2015-01-03"), getDate("2015-01-01"), getDate("2015-01-02"), getDate("2015-01-02")},
        });
    }

    @Test
    public void givenSeparationDates_whenUpdateSeparationDate_thenReturnSeparationDate() {
        final DivorceSession divorceSession = new DivorceSession();
        divorceSession.setReasonForDivorceSeperationDate(separationDate);
        divorceSession.setReasonForDivorceDecisionDate(decisionDate);
        divorceSession.setReasonForDivorceLivingApartDate(livingApartDate);

        classUnderTest.updateSeparationDate(divorceSession);

        assertEquals(expected, divorceSession.getReasonForDivorceSeperationDate());
    }

    private static LocalDate getDate(String date) {
        return LocalDate.parse(date);
    }

}