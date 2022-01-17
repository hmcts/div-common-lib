package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8DivorceCostsClaim;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8FinancialOrder;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8InferredPetitionerGender;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8InferredRespondentGender;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8JurisdictionConnection;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8LegalProceedings;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriagePetitionerName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageRespondentName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerContactDetailsConfidential;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerFirstName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8PetitionerLastName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorce;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceAdulteryDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceBehaviourDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceDesertionDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceDesertionDetails;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8ReasonForDivorceSeperationDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8RespondentFirstName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8RespondentLastName;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8StatementOfTruth;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.Rule;

import java.util.ArrayList;
import java.util.List;

public class BaseRuleCompiler implements RuleCompilerService {

    protected final D8InferredPetitionerGender d8InferredPetitionerGender = new D8InferredPetitionerGender();
    protected final D8InferredRespondentGender d8InferredRespondentGender = new D8InferredRespondentGender();
    protected final D8MarriageDate d8MarriageDate = new D8MarriageDate();
    protected final D8MarriagePetitionerName d8MarriagePetitionerName = new D8MarriagePetitionerName();
    protected final D8MarriageRespondentName d8MarriageRespondentName = new D8MarriageRespondentName();
    protected final D8PetitionerFirstName d8PetitionerFirstName = new D8PetitionerFirstName();
    protected final D8PetitionerLastName d8PetitionerLastName = new D8PetitionerLastName();
    protected final D8PetitionerContactDetailsConfidential d8PetitionerContactDetailsConfidential = new D8PetitionerContactDetailsConfidential();
    protected final D8RespondentFirstName d8RespondentFirstName = new D8RespondentFirstName();
    protected final D8RespondentLastName d8RespondentLastName = new D8RespondentLastName();
    protected final D8LegalProceedings d8LegalProceedings = new D8LegalProceedings();
    protected final D8ReasonForDivorce d8ReasonForDivorce = new D8ReasonForDivorce();
    protected final D8ReasonForDivorceBehaviourDetails d8ReasonForDivorceBehaviourDetails = new D8ReasonForDivorceBehaviourDetails();
    protected final D8ReasonForDivorceDesertionDate d8ReasonForDivorceDesertionDate = new D8ReasonForDivorceDesertionDate();
    protected final D8ReasonForDivorceDesertionDetails d8ReasonForDivorceDesertionDetails = new D8ReasonForDivorceDesertionDetails();
    protected final D8ReasonForDivorceSeperationDate d8ReasonForDivorceSeperationDate = new D8ReasonForDivorceSeperationDate();
    protected final D8ReasonForDivorceAdulteryDetails d8ReasonForDivorceAdulteryDetails = new D8ReasonForDivorceAdulteryDetails();
    protected final D8FinancialOrder d8FinancialOrder = new D8FinancialOrder();
    protected final D8DivorceCostsClaim d8DivorceCostsClaim = new D8DivorceCostsClaim();
    protected final D8JurisdictionConnection d8JurisdictionConnection = new D8JurisdictionConnection();
    protected final D8StatementOfTruth d8StatementOfTruth = new D8StatementOfTruth();

    protected ArrayList<Rule> rulesList = new ArrayList<>();

    public BaseRuleCompiler() {
        rulesList.add(d8InferredPetitionerGender); //2
        rulesList.add(d8InferredRespondentGender); //3
        rulesList.add(d8MarriageDate); //4
        rulesList.add(d8MarriagePetitionerName); //5
        rulesList.add(d8MarriageRespondentName); //6
        rulesList.add(d8PetitionerFirstName); //8
        rulesList.add(d8PetitionerLastName); //9
        rulesList.add(d8PetitionerContactDetailsConfidential); //10
        rulesList.add(d8RespondentFirstName); //13
        rulesList.add(d8RespondentLastName); //14
        rulesList.add(d8LegalProceedings); //15
        rulesList.add(d8ReasonForDivorce); //16
        rulesList.add(d8ReasonForDivorceBehaviourDetails); //17
        rulesList.add(d8ReasonForDivorceDesertionDate); //18
        rulesList.add(d8ReasonForDivorceDesertionDetails); //20
        rulesList.add(d8ReasonForDivorceSeperationDate); //21
        rulesList.add(d8ReasonForDivorceAdulteryDetails); //22
        rulesList.add(d8FinancialOrder); //23
        rulesList.add(d8DivorceCostsClaim); //24
        rulesList.add(d8JurisdictionConnection); //25
        rulesList.add(d8StatementOfTruth); //27
    }

    public List<String> executeRules(CoreCaseData coreCaseData) {
        ArrayList<String> result = new ArrayList<>();
        rulesList.forEach(rule -> rule.execute(coreCaseData, result));
        return result;
    }
}
