package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.validation.rules.d8.*;

public class AmendRuleCompiler extends BaseRuleCompiler {

    public AmendRuleCompiler() {
        super();
        rulesList.remove(new D8DivorceCostsClaim());
        rulesList.remove(new D8StatementOfTruth());
    }
}
