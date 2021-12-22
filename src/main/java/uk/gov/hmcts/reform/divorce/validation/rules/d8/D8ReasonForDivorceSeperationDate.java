package uk.gov.hmcts.reform.divorce.validation.rules.d8;


import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;
import java.util.Optional;

public class D8ReasonForDivorceSeperationDate extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String REASON_SEPARATION_2_YEARS = "separation-2-years";
    private static final String REASON_SEPARATION_5_YEARS = "separation-5-years";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8ReasonForDivorceSeperationDate can not be null or empty.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if ((Optional.ofNullable(coreCaseData.getD8ReasonForDivorce()).orElse("")
                .equalsIgnoreCase(REASON_SEPARATION_2_YEARS)
                || Optional.ofNullable(coreCaseData.getD8ReasonForDivorce()).orElse("")
                .equalsIgnoreCase(REASON_SEPARATION_5_YEARS))
                && Optional.ofNullable(coreCaseData.getD8ReasonForDivorceSeperationDate()).isEmpty()) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8ReasonForDivorceSeperationDate())
            ));
        }
        return result;
    }

}