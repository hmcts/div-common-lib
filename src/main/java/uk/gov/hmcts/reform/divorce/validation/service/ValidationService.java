package uk.gov.hmcts.reform.divorce.validation.service;

import uk.gov.hmcts.reform.divorce.models.request.CoreCaseData;
import uk.gov.hmcts.reform.divorce.models.response.ValidationResponse;

public interface ValidationService {

    ValidationResponse validate(CoreCaseData coreCaseData);
}
