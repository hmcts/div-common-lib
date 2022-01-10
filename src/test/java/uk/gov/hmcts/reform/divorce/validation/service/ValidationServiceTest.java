package uk.gov.hmcts.reform.divorce.validation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.model.ccd.Address;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static uk.gov.hmcts.reform.divorce.validation.service.ValidationStatus.FAILED;
import static uk.gov.hmcts.reform.divorce.validation.service.ValidationStatus.SUCCESS;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    MappingConfig.class
})
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    private CoreCaseData coreCaseData = new CoreCaseData();

    @Before
    public void setup() {
        assertNotNull(validationService);

        // Populate with valid data
        coreCaseData.setD8ScreenHasMarriageBroken("YES");
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithNoData_thenValidationWillFail() {
        assertEquals(FAILED.getValue(), validationService.validate(new CoreCaseData(), "testplaceholder").getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithValidButIncompleteData_thenValidationWillFail() {
        assertEquals(FAILED.getValue(), validationService.validate(coreCaseData, "testplaceholder").getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        coreCaseData.setD8ScreenHasMarriageBroken(null);
        ValidationResponse response = validationService.validate(coreCaseData, "testplaceholder");
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        assertNotEquals(0, response.getErrors().size());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithNull_thenValidationWilLFail() {
        ValidationResponse response = validationService.validate(null, "testplaceholder");
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        assertEquals("Core Case Data was null", response.getErrors().get(0));
    }

    @Test
    public void givenNull_whenValidationIsCalledWithNull_thenValidationWillFail() {
        ValidationResponse response = validationService.validate(null, null);
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        assertEquals("Core Case Data was null", response.getErrors().get(0));
    }

    @Test
    public void givenNull_WhenValidationIsCalledWithValidData_ThenValidationWillFail() {
        ValidationResponse response = validationService.validate(generateValidDummyCaseData(), null);
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        assertEquals("caseEventId was null", response.getErrors().get(0));
    }

    @Test
    public void givenCaseId_WhenValidationIsCalledWithValidData_thenValidationWillSucceed() {
        assertEquals(SUCCESS.getValue(), validationService.validate(generateValidDummyCaseData(), "testplaceholder").getValidationStatus());
    }

    @Test
    public void givenNull_WhenValidationCalledWithMockitoAny_thenValidationWillFail() {
        ValidationResponse response = validationService.validate(any(), anyString());
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        assertEquals("Core Case Data was null", response.getErrors().get(0));
    }

    private CoreCaseData generateValidDummyCaseData() {
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
        coreCaseData.setD8ReasonForDivorceBehaviourDetails("test");
        coreCaseData.setD8ReasonForDivorceDesertionDetails(null);
        coreCaseData.setD8ReasonForDivorceDesertionDate(null);
        coreCaseData.setD8ReasonForDivorceSeperation(null);
        coreCaseData.setD8FinancialOrder("Yes");
        coreCaseData.setD8MarriageDate(DateUtils.formatDate(Instant.now().minus(365 * 2, ChronoUnit.DAYS)));
        coreCaseData.setD8PetitionerLastName("dummyD8PetitionerLastName");
        coreCaseData.setD8PetitionerContactDetailsConfidential("keep");
        coreCaseData.setD8LegalProceedings("No");
        coreCaseData.setD8ReasonForDivorce("unreasonable-behaviour");
        coreCaseData.setD8DivorceCostsClaim("no");
        coreCaseData.setD8JurisdictionConnection(List.of("test"));

        return coreCaseData;
    }

}
