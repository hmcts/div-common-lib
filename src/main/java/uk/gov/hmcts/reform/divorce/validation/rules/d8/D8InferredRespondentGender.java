package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;

import java.util.List;
import java.util.Optional;

public class D8InferredRespondentGender extends Rule {

    private static final String BLANK_SPACE = " ";
    private static final String ACTUAL_DATA = "Actual data is: %s";
    private static final String ERROR_MESSAGE = "D8InferredRespondentGender can not be null or empty.";

    @Override
    public List<String> execute(CoreCaseData coreCaseData, List<String> result) {
        if (isBlank(coreCaseData.getD8InferredRespondentGender())) {
            result.add(String.join(
                    BLANK_SPACE, // delimiter
                    ERROR_MESSAGE,
                    String.format(ACTUAL_DATA, coreCaseData.getD8InferredRespondentGender())
            ));
        }

        return result;
    }

    private boolean isBlank(Gender d8InferredRespondentGender) {
        if (Optional.ofNullable(d8InferredRespondentGender).isEmpty()) {
            return true;
        }

        return d8InferredRespondentGender.getValue().isEmpty();
    }
}
