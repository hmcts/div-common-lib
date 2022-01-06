package uk.gov.hmcts.reform.divorce.validation.rules.d8;

public class JurisdictionAmendRuleCompiler extends AmendRuleCompiler {

    public JurisdictionAmendRuleCompiler() {
        super();
        rulesList.remove(new D8JurisdictionConnection());
    }
}
