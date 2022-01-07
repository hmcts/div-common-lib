package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.Rule;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8InferredPetitionerGender;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8InferredRespondentGender;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriagePetitionerName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageRespondentName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerFirstName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerLastName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerContactDetailsConfidential;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8RespondentFirstName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8RespondentLastName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8LegalProceedings;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorce;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceBehaviourDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceDesertionDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceDesertionDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceSeperationDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceAdulteryDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8FinancialOrder;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8DivorceCostsClaim;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8JurisdictionConnection;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8StatementOfTruth;

import java.util.ArrayList;
import java.util.List;

public class BaseRuleCompiler implements RuleCompilerService {

    protected ArrayList<Rule> rulesList = new ArrayList<>();

    public BaseRuleCompiler() {
        rulesList.add(new D8InferredPetitionerGender()); //2
        rulesList.add(new D8InferredRespondentGender()); //3
        rulesList.add(new D8MarriageDate()); //4
        rulesList.add(new D8MarriagePetitionerName()); //5
        rulesList.add(new D8MarriageRespondentName()); //6
        rulesList.add(new D8PetitionerFirstName()); //8
        rulesList.add(new D8PetitionerLastName()); //9
        rulesList.add(new D8PetitionerContactDetailsConfidential()); //10
        rulesList.add(new D8RespondentFirstName()); //13
        rulesList.add(new D8RespondentLastName()); //14
        rulesList.add(new D8LegalProceedings()); //15
        rulesList.add(new D8ReasonForDivorce()); //16
        rulesList.add(new D8ReasonForDivorceBehaviourDetails()); //17
        rulesList.add(new D8ReasonForDivorceDesertionDate()); //18
        rulesList.add(new D8ReasonForDivorceDesertionDetails()); //20
        rulesList.add(new D8ReasonForDivorceSeperationDate()); //21
        rulesList.add(new D8ReasonForDivorceAdulteryDetails()); //22
        rulesList.add(new D8FinancialOrder()); //23
        rulesList.add(new D8DivorceCostsClaim()); //24
        rulesList.add(new D8JurisdictionConnection()); //25
        rulesList.add(new D8StatementOfTruth()); //27
    }

    public List<String> executeRules(CoreCaseData coreCaseData) {
        ArrayList<String> result = new ArrayList<>();
        rulesList.forEach(rule -> rule.execute(coreCaseData, result));
        return result;
    }
}
