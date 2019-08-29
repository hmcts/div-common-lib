package uk.gov.hmcts.reform.divorce.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;

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

        assertEquals(null, instant);
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
        LocalDateTime localDate = LocalDate.of(2000, 01, 01)
            .atTime(12,34);

        String expectedDateString = "12:34";
        String dateString = DateUtils.formatTimeFromDateTime(localDate);

        assertEquals(expectedDateString, dateString);
    }
}