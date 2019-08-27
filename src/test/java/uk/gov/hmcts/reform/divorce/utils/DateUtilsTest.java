package uk.gov.hmcts.reform.divorce.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
    public void givenAnInsant_whenGetFullFormattedInstantIsCalled_thenInstantAsStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);

        String expectedInstantString = "2000-01-01T00:00:00Z";
        String instantString = DateUtils.getFullFormattedDateFromInstant(instant);

        assertEquals(expectedInstantString, instantString);
    }

    @Test
    public void givenDate_whenGetFormattedDateIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.getFormattedDate(date);

        assertEquals(expectedDateString, dateString);
    }

    @Test
    public void givenInstant_whenGetFormattedDateIsCalled_thenDateStringIsReturned() {
        LocalDateTime localDate = LocalDate.of(2000, 01, 01).atStartOfDay();
        Instant instant = localDate.toInstant(ZoneOffset.UTC);

        String expectedDateString = "2000-01-01";
        String dateString = DateUtils.getFormattedDate(instant);

        assertEquals(expectedDateString, dateString);
    }
}