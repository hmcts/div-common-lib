package uk.gov.hmcts.reform.divorce.validation.rules.d8;


import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;
import java.util.Optional;


public class D8StatementOfTruth extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8StatementOfTruth must be 'YES'.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (Optional.ofNullable(coreCaseData.getD8StatementOfTruth()).isEmpty()
                || !coreCaseData.getD8StatementOfTruth().equalsIgnoreCase("YES")) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8StatementOfTruth())
            ));
        }

        return result;
    }
}