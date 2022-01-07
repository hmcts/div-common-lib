package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

public class AmendRuleCompiler extends BaseRuleCompiler {

    public AmendRuleCompiler() {
        super();
        rulesList.remove(d8DivorceCostsClaim);
        rulesList.remove(d8StatementOfTruth);
    }
}
