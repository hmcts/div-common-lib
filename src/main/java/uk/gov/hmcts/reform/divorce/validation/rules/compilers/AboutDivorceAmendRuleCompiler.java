package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8FinancialOrder;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8LegalProceedings;

public class AboutDivorceAmendRuleCompiler extends AmendRuleCompiler {

    public AboutDivorceAmendRuleCompiler() {
        super();
        rulesList.remove(new D8LegalProceedings());
        rulesList.remove(new D8FinancialOrder());
    }
}
