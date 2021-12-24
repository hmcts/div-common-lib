package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.apache.commons.lang3.StringUtils;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class D8ReasonForDivorce extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE_NULL = "D8ReasonForDivorce can not be null or empty.";
    private static final String ERROR_MESSAGE_INVALID =
        "D8ReasonForDivorce is invalid for the current date of marriage.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (StringUtils.isBlank(coreCaseData.getD8ReasonForDivorce())
                || getAllowedReasonsForDivorce(coreCaseData.getD8MarriageDate()).stream()
                .noneMatch(reason -> reason.equalsIgnoreCase(coreCaseData.getD8ReasonForDivorce()))) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    Optional.ofNullable(coreCaseData.getD8ReasonForDivorce()).isPresent()
                            ? ERROR_MESSAGE_INVALID
                            : ERROR_MESSAGE_NULL,
                    String.format(ACTUAL_DATA, coreCaseData.getD8ReasonForDivorce())
            ));
        }
        return result;
    }

    private List<String> getAllowedReasonsForDivorce(String marriageDate) {
        // Exit early if marriageDate is null
        if (marriageDate == null) {
            return new ArrayList<>();
        }

        List<String> reasonsForDivorce = new ArrayList<>();

        // When marriageDate is more than one year ago
        if (DateUtils.parseToInstant(marriageDate).isBefore(Instant.now().minus(365, ChronoUnit.DAYS))) {
            reasonsForDivorce.add("adultery");
            reasonsForDivorce.add("unreasonable-behaviour");
        }

        // When marriageDate is more than two years ago
        if (DateUtils.parseToInstant(marriageDate).isBefore(Instant.now().minus(365 * 2, ChronoUnit.DAYS))) {
            reasonsForDivorce.add("separation-2-years");
            reasonsForDivorce.add("desertion");
        }

        // When marriageDate is more than five years ago
        if (DateUtils.parseToInstant(marriageDate).isBefore(Instant.now().minus(365 * 5, ChronoUnit.DAYS))) {
            reasonsForDivorce.add("separation-5-years");
        }

        return reasonsForDivorce;
    }
}