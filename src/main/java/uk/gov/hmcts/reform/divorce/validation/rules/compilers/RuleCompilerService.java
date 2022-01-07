package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;

public interface RuleCompilerService {

    List<String> executeRules(CoreCaseData coreCaseData);
}
