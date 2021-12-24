package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.apache.commons.lang3.StringUtils;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;
import java.util.Optional;

public class D8ReasonForDivorceAdulteryDetails extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String REASON_ADULTERY = "adultery";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8ReasonForDivorceAdulteryDetails can not be null or empty.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (Optional.ofNullable(coreCaseData.getD8ReasonForDivorce()).orElse("").equalsIgnoreCase(REASON_ADULTERY)
                && StringUtils.isBlank(coreCaseData.getD8ReasonForDivorceAdulteryDetails())) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8ReasonForDivorceAdulteryDetails())
            ));
        }
        return result;
    }

}
