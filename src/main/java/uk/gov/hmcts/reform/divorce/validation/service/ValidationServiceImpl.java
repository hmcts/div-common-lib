package uk.gov.hmcts.reform.divorce.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;
import uk.gov.hmcts.reform.divorce.validation.rules.compilers.RuleCompilerFactory;
import uk.gov.hmcts.reform.divorce.validation.rules.compilers.RuleCompilerService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    private RuleCompilerService ruleCompiler;

    @Override
    public ValidationResponse validate(CoreCaseData coreCaseData, String caseEventId) {
        log.info("Validating CoreCaseData");

        ValidationResponse validationResponse = ValidationResponse.builder()
            .validationStatus(ValidationStatus.SUCCESS.getValue())
            .build();

        if (Optional.ofNullable(coreCaseData).isEmpty()) {
            log.info("CoreCaseData is null");
            validationResponse.setErrors(List.of("Core Case Data was null"));
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
            return validationResponse;
        }

        if (Optional.ofNullable(caseEventId).isEmpty()) {
            log.info("caseEventId is null");
            validationResponse.setErrors(List.of("caseEventId was null"));
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
            return validationResponse;
        }

        ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, caseEventId);
        List<String> result = ruleCompiler.executeRules(coreCaseData);

        if (!result.isEmpty()) {
            log.info("There were invalid fields in CoreCaseData, adding errors to validationResponse");
            validationResponse.setErrors(result);
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
        }

        return validationResponse;
    }

}