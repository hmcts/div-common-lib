package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

public class AboutDivorceAmendRuleCompiler extends AmendRuleCompiler {

    public AboutDivorceAmendRuleCompiler() {
        super();
        rulesList.remove(d8LegalProceedings);
        rulesList.remove(d8FinancialOrder);
    }
}
