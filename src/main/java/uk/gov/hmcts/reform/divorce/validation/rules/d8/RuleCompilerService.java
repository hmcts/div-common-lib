package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;

public interface RuleCompilerService {

    List<String> executeRules(CoreCaseData coreCaseData);
}
