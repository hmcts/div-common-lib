package uk.gov.hmcts.reform.divorce.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.DateUtils.formatDateTimeForCcd;
import static uk.gov.hmcts.reform.divorce.utils.DateUtils.formatDateTimeForDocument;
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
        LocalDateTime localDate = LocalDate.of(2000, 1, 1).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDate(date);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenInstant_whenFormatDateIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 1, 1).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDate(instant);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDate_whenFormatDateFromLocalDateIsCalled_thenDateStringIsReturned() {
        LocalDate localDate = LocalDate.of(2000, 1, 1)
            .atStartOfDay()
            .toLocalDate();

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDateFromLocalDate(localDate);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDateTime_whenFormatDateFromDateTimeIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 1, 1).atStartOfDay();

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.formatDateFromDateTime(localDate);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenLocalDateTime_whenFormatTimeFromDateTimeIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate
            .of(2000, 1, 1)
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
    public void formatDateWithCustomerFacingFormatStringReturnsFormattedDateString() {
        assertThat(
            formatDateWithCustomerFacingFormat("2020-06-04"),
            is("4 June 2020")
        );
        assertThat(formatDateWithCustomerFacingFormat("2020-06-04"), is("4 June 2020"));
        assertThat(formatDateWithCustomerFacingFormat("1999-10-20"), is("20 October 1999"));
    }

    @Test
    public void formatDateTimeForCcdReturnsValidString() {
        assertThat(
            formatDateTimeForCcd(LocalDateTime.of(2020, Month.JUNE, 4, 2, 1, 5)),
            is("2020-06-04T02:01:05.000")
        );
    }

    @Test
    public void formatDateTimeForDocumentReturnsValidString() {
        assertThat(
                formatDateTimeForDocument(LocalDateTime.of(2020, Month.OCTOBER, 14, 2, 1, 5)),
                is("2020-10-14:02:01")
        );
    }

    @Test
    public void givenDateBeforeYear2000_whenformatDateTimeForDocument_thenReturnsValidString() {
        assertThat(
                formatDateTimeForDocument(LocalDateTime.of(1999, Month.MAY, 1, 2, 1, 5)),
                is("1999-05-01:02:01")
        );
    }

    @Test
    public void givenYearWith3Digits_whenformatDateTimeForDocument_thenReturnsValidString() {
        assertThat(
                formatDateTimeForDocument(LocalDateTime.of(345, Month.OCTOBER, 20, 2, 1, 5)),
                is("0345-10-20:02:01")
        );
    }

    @Test
    public void givenAfternoonTime_whenformatDateTimeForDocument_thenReturnsValidString() {
        assertThat(
                formatDateTimeForDocument(LocalDateTime.of(2020, Month.OCTOBER, 14, 13, 1, 5)),
                is("2020-10-14:13:01")
        );
    }

    @Test
    public void nonNullDateReturnsFormattedDateString() {
        assertEquals("01 01 2001", DateUtils.format(
            Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
            "dd MM yyyy")
        );
    }

    @Test
    public void nullDateReturnsFormattedZeroDateString() {
        assertEquals("01 01 1970", DateUtils.format(
            null,
            "dd MM yyyy")
        );
    }

}