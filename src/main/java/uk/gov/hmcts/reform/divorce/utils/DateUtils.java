package uk.gov.hmcts.reform.divorce.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

@Slf4j
public class DateUtils {

    private static final DateTimeFormatter CLIENT_FACING_DATE_FORMAT = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.UK);

    private DateUtils() {
        // utility class
    }

    public static Instant parseToInstant(String date) {
        Instant instant = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            instant = simpleDateFormat
                .parse(date)
                .toInstant();
        } catch (ParseException e) {
            log.error("failed to parse date to instant ", e);
        }

        return instant;
    }

    public static String formatDate(Date date) {
        return formatDateFromLocalDate(
            date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );
    }

    public static String formatDate(Instant instant) {
        return formatDate(Date.from(instant));
    }

    public static String formatDateWithCustomerFacingFormat(LocalDate date) {
        return date.format(CLIENT_FACING_DATE_FORMAT);
    }

    public static String formatDateFromLocalDate(LocalDate date) {

        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String formatDateFromDateTime(LocalDateTime dateTime) {
        return formatDateFromLocalDate(dateTime.toLocalDate());
    }

    public static String formatTimeFromDateTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String formatNullableDate(Date date, String pattern) {
        return DateFormatUtils.format(
            Optional.ofNullable(date).orElse(new Date(0)),
            pattern
        );
    }
}
