package uk.gov.hmcts.reform.divorce.validation.service;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;

public interface ValidationService {

    ValidationResponse validate(CoreCaseData coreCaseData, String caseEventId);
}
