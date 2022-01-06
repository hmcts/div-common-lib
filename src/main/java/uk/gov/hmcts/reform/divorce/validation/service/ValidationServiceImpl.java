package uk.gov.hmcts.reform.divorce.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.RuleCompiler;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    private RuleCompiler ruleCompiler;

    @Override
    public ValidationResponse validate(CoreCaseData coreCaseData, String caseEventId) {
        log.info("Validating CoreCaseData");

        ValidationResponse validationResponse = ValidationResponse.builder()
            .validationStatus(ValidationStatus.SUCCESS.getValue())
            .build();

        if (Optional.ofNullable(coreCaseData).isEmpty()) {
            log.info("CoreCaseData is empty");
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
            return validationResponse;
        }

        ruleCompiler = new RuleCompiler(caseEventId);
        List<String> result = ruleCompiler.executeRules(coreCaseData);

        if (!result.isEmpty()) {
            log.info("There were invalid fields in CoreCaseData, adding errors to validationResponse");
            validationResponse.setErrors(result);
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
        }

        return validationResponse;
    }
}