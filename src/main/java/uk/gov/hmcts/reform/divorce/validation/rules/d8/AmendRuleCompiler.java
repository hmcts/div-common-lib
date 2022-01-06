package uk.gov.hmcts.reform.divorce.validation.rules.d8;

public class AmendRuleCompiler extends BaseRuleCompiler {

    public AmendRuleCompiler() {
        super();
        rulesList.remove(new D8DivorceCostsClaim());
        rulesList.remove(new D8StatementOfTruth());
        rulesList.remove(new D8ReasonForDivorceBehaviourDetails());
        rulesList.remove(new D8ReasonForDivorceDesertionDetails());
        rulesList.remove(new D8ReasonForDivorceSeperationDate());
        rulesList.remove(new D8ReasonForDivorceAdulteryDetails());
    }
}
