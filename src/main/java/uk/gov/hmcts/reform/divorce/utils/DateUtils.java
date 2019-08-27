package uk.gov.hmcts.reform.divorce.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtils {

    private DateUtils() {
        // utility class
    }

    public static Instant parseToInstant(String date) {
        Instant instant = null;
        try {
            instant = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).toInstant();
        } catch (ParseException e) {
            log.error("failed to parse date to instant ", e);
        }
        return instant;
    }

    public static String getFullFormattedDateFromInstant(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return formatter.format(instant);
    }

    public static String getFormattedDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
    }

    public static String getFormattedDate(Instant instant) {
        return getFormattedDate(Date.from(instant));
    }
}