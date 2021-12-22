package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

public class RuleCompiler {

    public ArrayList<Rule> rulesList = new ArrayList<>();

    public void setUpRules() {
        rulesList.add(new D8DivorceCostsClaim());
    }

    public List<String> executeRules(CoreCaseData coreCaseData) {
        ArrayList<String> result = new ArrayList<>();
        rulesList.forEach(rule -> rule.execute(coreCaseData, result) );
        return result;
    }
}
