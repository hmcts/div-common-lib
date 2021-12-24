package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;
import java.util.Optional;

public class D8JurisdictionConnection extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8JurisdictionConnection can not be null or empty.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (isBlank(coreCaseData.getD8JurisdictionConnection())) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8JurisdictionConnection())
            ));
        }

        return result;
    }

    private boolean isBlank(List<String> d8JurisdictionConnection) {
        if (Optional.ofNullable(d8JurisdictionConnection).isEmpty()) {
            return true;
        }

        return d8JurisdictionConnection.isEmpty();
    }
}
