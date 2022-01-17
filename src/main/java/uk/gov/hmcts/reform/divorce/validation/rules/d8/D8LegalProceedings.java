package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.apache.commons.lang3.StringUtils;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;

public class D8LegalProceedings extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8LegalProceedings can not be null or empty.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (StringUtils.isBlank(coreCaseData.getD8LegalProceedings())) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8LegalProceedings())
            ));
        }

        return result;
    }
}