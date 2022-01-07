package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.Address;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.Constants.BASE_COMPILER_RULE_COUNT;

public class BaseRuleCompilerTest {

    List<Rule> correctOrderRules = new ArrayList<>();
    CoreCaseData coreCaseData;
    BaseRuleCompiler baseRuleCompiler;
    List<String> result;


    @Before
    public void setup() {
        baseRuleCompiler = new BaseRuleCompiler();
        correctOrderRules = constructCorrectOrderRules();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void rulesShouldBeAddedInCorrectOrder() {
        BaseRuleCompiler baseRuleCompiler = new BaseRuleCompiler();

        AtomicInteger i = new AtomicInteger();
        baseRuleCompiler.rulesList.forEach(rule -> {
            assertEquals(rule.getClass(), correctOrderRules.get(i.get()).getClass());
            i.getAndIncrement();
        });
    }

    @Test
    public void shouldGenerateCorrectErrorListWithGivenCoreCaseData() {
        int invalidDummyFields = 7;
        coreCaseData = generateDummyCaseData();
        result = baseRuleCompiler.executeRules(coreCaseData);

        assertThat(result.size(), is(invalidDummyFields));
        assertThat(result.get(0), containsString("D8MarriageDate can not be in the future. Actual data is:"));
        assertThat(result.get(1), is("D8PetitionerLastName can not be null or empty. Actual data is: null"));
        assertThat(result.get(2), is("D8PetitionerContactDetailsConfidential can not be null or empty. Actual data is: null"));
        assertThat(result.get(3), is("D8LegalProceedings can not be null or empty. Actual data is: null"));
        assertThat(result.get(4), is("D8ReasonForDivorce can not be null or empty. Actual data is: null"));
        assertThat(result.get(5), is("D8DivorceCostsClaim can not be null or empty. Actual data is: null"));
        assertThat(result.get(6), is("D8JurisdictionConnection can not be null or empty. Actual data is: []"));
    }

    @Test
    public void shouldReturnListWithMandatoryFieldErrorsWhenCoreCaseDataIsEmpty() {
        result = baseRuleCompiler.executeRules(coreCaseData);

        assertThat(result.size(), is(BASE_COMPILER_RULE_COUNT));
    }

    private List<Rule> constructCorrectOrderRules() {
        correctOrderRules.add(new D8InferredPetitionerGender()); //2
        correctOrderRules.add(new D8InferredRespondentGender()); //3
        correctOrderRules.add(new D8MarriageDate()); //4
        correctOrderRules.add(new D8MarriagePetitionerName()); //5
        correctOrderRules.add(new D8MarriageRespondentName()); //6
        correctOrderRules.add(new D8PetitionerFirstName()); //8
        correctOrderRules.add(new D8PetitionerLastName()); //9
        correctOrderRules.add(new D8PetitionerContactDetailsConfidential()); //10
        correctOrderRules.add(new D8RespondentFirstName()); //13
        correctOrderRules.add(new D8RespondentLastName()); //14
        correctOrderRules.add(new D8LegalProceedings()); //15
        correctOrderRules.add(new D8ReasonForDivorce()); //16
        correctOrderRules.add(new D8ReasonForDivorceBehaviourDetails()); //17
        correctOrderRules.add(new D8ReasonForDivorceDesertionDate()); //18
        correctOrderRules.add(new D8ReasonForDivorceDesertionDetails()); //20
        correctOrderRules.add(new D8ReasonForDivorceSeperationDate()); //21
        correctOrderRules.add(new D8ReasonForDivorceAdulteryDetails()); //22
        correctOrderRules.add(new D8FinancialOrder()); //23
        correctOrderRules.add(new D8DivorceCostsClaim()); //24
        correctOrderRules.add(new D8JurisdictionConnection()); //25
        correctOrderRules.add(new D8StatementOfTruth()); //27

        return correctOrderRules;
    }

    private CoreCaseData generateDummyCaseData() {
        coreCaseData.setD8InferredPetitionerGender(Gender.FEMALE);
        coreCaseData.setD8InferredRespondentGender(Gender.MALE);
        coreCaseData.setD8RespondentFirstName("dummyD8RespondentFirstName");
        coreCaseData.setD8RespondentLastName("dummyD8RespondentLastName");
        coreCaseData.setD8RespondentCorrespondenceAddress(new Address());
        coreCaseData.setD8PetitionerFirstName("dummyD8PetitionerFirstName");
        coreCaseData.setD8MarriagePetitionerName("dummyD8MarriagePetitionerName");
        coreCaseData.setD8MarriageRespondentName("dummyD8MarriageRespondentName");
        coreCaseData.setD8StatementOfTruth("Yes");
        coreCaseData.setD8ReasonForDivorceAdulteryDetails(null);
        coreCaseData.setD8ReasonForDivorceBehaviourDetails(null);
        coreCaseData.setD8ReasonForDivorceDesertionDetails(null);
        coreCaseData.setD8ReasonForDivorceDesertionDate(null);
        coreCaseData.setD8ReasonForDivorceSeperation(null);
        coreCaseData.setD8FinancialOrder("Yes");

        // Set bad data (7 invalid fields):
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS)));
        coreCaseData.setD8PetitionerLastName(null);
        coreCaseData.setD8PetitionerContactDetailsConfidential(null);
        coreCaseData.setD8LegalProceedings(null);
        coreCaseData.setD8ReasonForDivorce(null);
        coreCaseData.setD8DivorceCostsClaim(null);
        coreCaseData.setD8JurisdictionConnection(new ArrayList<>());

        return coreCaseData;
    }

}
