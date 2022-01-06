package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

public class RuleCompilerFactory {

    public static RuleCompilerService getRuleCompiler(CoreCaseData coreCaseData, String caseEventId) {
        return new AmendRuleCompiler();
    }
}
