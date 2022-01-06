package uk.gov.hmcts.reform.divorce.validation.rules.d8;

public class AboutDivorceAmendRuleCompiler extends AmendRuleCompiler {

    public AboutDivorceAmendRuleCompiler() {
        super();
        rulesList.remove(new D8LegalProceedings());
        rulesList.remove(new D8FinancialOrder());
    }
}
