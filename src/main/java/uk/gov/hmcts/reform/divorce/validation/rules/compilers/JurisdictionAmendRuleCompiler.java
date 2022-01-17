package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

public class JurisdictionAmendRuleCompiler extends AmendRuleCompiler {

    public JurisdictionAmendRuleCompiler() {
        super();
        rulesList.remove(d8JurisdictionConnection);
    }
}
