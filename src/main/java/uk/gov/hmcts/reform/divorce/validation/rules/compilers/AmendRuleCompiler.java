package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8DivorceCostsClaim;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8StatementOfTruth;

public class AmendRuleCompiler extends BaseRuleCompiler {

    public AmendRuleCompiler() {
        super();
        rulesList.remove(new D8DivorceCostsClaim());
        rulesList.remove(new D8StatementOfTruth());
    }
}
