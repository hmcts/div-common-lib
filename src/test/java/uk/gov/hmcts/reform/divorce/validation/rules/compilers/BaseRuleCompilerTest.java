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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.Constants.BASE_COMPILER_RULE_COUNT;

public class BaseRuleCompilerTest {

    CoreCaseData coreCaseData;
    BaseRuleCompiler baseRuleCompiler;
    List<String> result;


    @Before
    public void setup() {
        baseRuleCompiler = new BaseRuleCompiler();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void rulesShouldBeAddedInCorrectOrder() {
        BaseRuleCompiler baseRuleCompiler = new BaseRuleCompiler();

        assertThat(baseRuleCompiler.rulesList, contains(
                instanceOf(D8InferredPetitionerGender.class),
                instanceOf(D8InferredRespondentGender.class),
                instanceOf(D8MarriageDate.class),
                instanceOf(D8MarriagePetitionerName.class),
                instanceOf(D8MarriageRespondentName.class),
                instanceOf(D8PetitionerFirstName.class),
                instanceOf(D8PetitionerLastName.class),
                instanceOf(D8PetitionerContactDetailsConfidential.class),
                instanceOf(D8RespondentFirstName.class),
                instanceOf(D8RespondentLastName.class),
                instanceOf(D8LegalProceedings.class),
                instanceOf(D8ReasonForDivorce.class),
                instanceOf(D8ReasonForDivorceBehaviourDetails.class),
                instanceOf(D8ReasonForDivorceDesertionDate.class),
                instanceOf(D8ReasonForDivorceDesertionDetails.class),
                instanceOf(D8ReasonForDivorceSeperationDate.class),
                instanceOf(D8ReasonForDivorceAdulteryDetails.class),
                instanceOf(D8FinancialOrder.class),
                instanceOf(D8DivorceCostsClaim.class),
                instanceOf(D8JurisdictionConnection.class),
                instanceOf(D8StatementOfTruth.class)
        ));
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
