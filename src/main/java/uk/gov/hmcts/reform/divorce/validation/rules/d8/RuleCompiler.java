package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

public class RuleCompiler {

    public ArrayList<Rule> rulesList = new ArrayList<>();

    public void setUpRules() {
        rulesList.add(new D8PetitionerFirstName()); //8
        rulesList.add(new D8PetitionerLastName()); //9
        rulesList.add(new D8RespondentFirstName()); //13
        rulesList.add(new D8RespondentLastName()); //14?
        rulesList.add(new D8ReasonForDivorce()); //16
        rulesList.add(new D8ReasonForDivorceBehaviourDetails()); //17
        rulesList.add(new D8ReasonForDivorceDesertionDate()); //18
        rulesList.add(new D8ReasonForDivorceDesertionDetails()); //20
        rulesList.add(new D8ReasonForDivorceSeperationDate()); //21
        rulesList.add(new D8ReasonForDivorceAdulteryDetails()); //22
        rulesList.add(new D8DivorceCostsClaim());
    }

    public List<String> executeRules(CoreCaseData coreCaseData) {
        ArrayList<String> result = new ArrayList<>();
        rulesList.forEach(rule -> rule.execute(coreCaseData, result) );
        return result;
    }
}
