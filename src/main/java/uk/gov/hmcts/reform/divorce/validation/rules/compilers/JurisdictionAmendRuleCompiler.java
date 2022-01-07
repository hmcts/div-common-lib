package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8JurisdictionConnection;

public class JurisdictionAmendRuleCompiler extends AmendRuleCompiler {

    public JurisdictionAmendRuleCompiler() {
        super();
        rulesList.remove(new D8JurisdictionConnection());
    }
}
