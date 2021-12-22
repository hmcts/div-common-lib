package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class D8MarriageDate extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE_NULL = "D8MarriageDate can not be null or empty.";
    private static final String ERROR_MESSAGE_LESS_THAN_ONE_YEAR_AGO =
        "D8MarriageDate can not be less than one year ago.";
    private static final String ERROR_MESSAGE_MORE_THAN_ONE_HUNDRED_YEARS_AGO =
        "D8MarriageDate can not be more than 100 years ago.";
    private static final String ERROR_MESSAGE_IN_THE_FUTURE = "D8MarriageDate can not be in the future.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        String marriageDate = coreCaseData.getD8MarriageDate();
        if (isNull(coreCaseData)
                || isLessThanOneYearAgo(marriageDate)
                || isOverOneHundredYearsAgo(marriageDate)
                || isInTheFuture(marriageDate)) {
            String errorMessage = deriveErrorMessage(coreCaseData);
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    errorMessage,
                    String.format(ACTUAL_DATA, coreCaseData.getD8MarriageDate())
            ));
        }

        return result;
    }

    private boolean isNull(CoreCaseData coreCaseData) {
        return Optional.ofNullable(coreCaseData.getD8MarriageDate()).isEmpty();
    }

    private boolean isLessThanOneYearAgo(String date) {
        return !DateUtils.parseToInstant(date).isAfter(Instant.now())
            && DateUtils.parseToInstant(date).isAfter(Instant.now().minus(365, ChronoUnit.DAYS));
    }

    private boolean isOverOneHundredYearsAgo(String date) {
        return DateUtils.parseToInstant(date).isBefore(Instant.now().minus(365 * 100, ChronoUnit.DAYS));
    }

    private boolean isInTheFuture(String date) {
        return DateUtils.parseToInstant(date).isAfter(Instant.now());
    }

    private String deriveErrorMessage(CoreCaseData coreCaseData) {
        String marriageDate = coreCaseData.getD8MarriageDate();
        return isNull(coreCaseData)
            ? ERROR_MESSAGE_NULL
            : Stream.of(
            isLessThanOneYearAgo(marriageDate) ? ERROR_MESSAGE_LESS_THAN_ONE_YEAR_AGO : "",
            isOverOneHundredYearsAgo(marriageDate) ? ERROR_MESSAGE_MORE_THAN_ONE_HUNDRED_YEARS_AGO : "",
            isInTheFuture(marriageDate) ? ERROR_MESSAGE_IN_THE_FUTURE : ""
        ).filter(string -> !string.isEmpty()).collect(Collectors.joining(BLANK_SPACE));
    }
}