package uk.gov.hmcts.reform.divorce.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.DateUtils.formatDateForDocuments;
import static uk.gov.hmcts.reform.divorce.utils.DateUtils.formatDateWithCustomerFacingFormat;

@RunWith(SpringJUnit4ClassRunner.class)
public class DateUtilsTest {

    @Test
    public void givenDateInStringFormat_whenParseToInstantIsCalled_thenAnInstantOfTheDateStringIsReturned() {
        String dateString = "2000-01-01";

        LocalDateTime date = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant expectedInstant = date.toInstant(ZoneOffset.UTC);

        Instant instant = DateUtils.parseToInstant(dateString);

        assertEquals(expectedInstant, instant);
    }

    @Test
    public void givenInvalidDateInStringFormat_whenParseToInstantIsCalled_thenNullIsReturned() {
        String dateString = "abcdefg";
        Instant instant = DateUtils.parseToInstant(dateString);

        assertNull(instant);
    }

    @Test
    public void givenDate_whenFormatDateIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDate(date);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenInstant_whenFormatDateIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDate(instant);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDate_whenFormatDateFromLocalDateIsCalled_thenDateStringIsReturned() {
        LocalDate localDate = LocalDate.of(2000, 01, 01)
            .atStartOfDay()
            .toLocalDate();

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDateFromLocalDate(localDate);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDateTime_whenFormatDateFromDateTimeIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDateFromDateTime(localDate);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDateTime_whenFormatTimeFromDateTimeIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate
            .of(2000, 01, 01)
            .atTime(12, 34);

        String expectedDateString = "12:34";
        String dateString = DateUtils.formatTimeFromDateTime(localDate);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenNull_whenFormatNullableDateIsCalled_thenDateStringIsReturned() {
        String expectedDateString = "1970 00 01";
        String dateString = DateUtils.formatNullableDate(null, "YYYY mm dd");

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenDate_whenFormatNullableDateIsCalled_thenDateStringIsReturned() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String expectedDateString = String.valueOf(calendar.get(Calendar.YEAR));
        String dateString = DateUtils.formatNullableDate(date, "YYYY");

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void formatDateWithCustomerFacingFormatReturnsFormattedDateString() {
        assertThat(
            formatDateWithCustomerFacingFormat(LocalDate.of(2020, Month.JUNE, 4)),
            is("4 June 2020")
        );
    }

    @Test
    public void formatDateForDocumentsReturnsFormattedDateStringLeadingZerosForDay() {
        assertThat(
            formatDateForDocuments(LocalDate.of(2020, Month.JUNE, 4)),
            is("04 Jun 2020")
        );
        assertThat(formatDateForDocuments("2020-06-04"), is("04 Jun 2020"));
        assertThat(
                formatDateForDocuments(LocalDate.of(1999, Month.OCTOBER, 20)),
                is("20 Oct 1999")
        );
        assertThat(formatDateForDocuments("1999-10-20"), is("20 Oct 1999"));
    }
}
