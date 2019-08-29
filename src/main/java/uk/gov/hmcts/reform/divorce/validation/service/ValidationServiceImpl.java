package uk.gov.hmcts.reform.divorce.validation.service;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.models.request.CoreCaseData;
import uk.gov.hmcts.reform.divorce.models.response.ValidationResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    @Qualifier("D8RuleBook")
    private RuleBook<List<String>> d8RuleBook;

    @Override
    public ValidationResponse validate(CoreCaseData coreCaseData) {
        log.info("Validating CoreCaseData");

        ObjectMapper mapper = new ObjectMapper();
        NameValueReferableMap<CoreCaseData> facts = new FactMap<>();

        facts.setValue("coreCaseData", coreCaseData);
        d8RuleBook.setDefaultResult(new ArrayList<>());
        d8RuleBook.run(facts);

        ValidationResponse validationResponse = ValidationResponse.builder()
            .validationStatus(ValidationStatus.SUCCESS.getValue())
            .build();

        d8RuleBook.getResult().map(Result::getValue)
            .ifPresent(result -> errorResponse(validationResponse, result));

        return validationResponse;
    }

    private void errorResponse(ValidationResponse validationResponse, List<String> result) {
        if (!result.isEmpty()) {
            validationResponse.setErrors(result);
            validationResponse.setValidationStatus(ValidationStatus.FAILED.getValue());
        }
    }
}
