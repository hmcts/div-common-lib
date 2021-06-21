package uk.gov.hmcts.reform.divorce.validation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Gender;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static uk.gov.hmcts.reform.divorce.validation.service.ValidationStatus.FAILED;
import static uk.gov.hmcts.reform.divorce.validation.service.ValidationStatus.SUCCESS;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    MappingConfig.class
})
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    private final CoreCaseData coreCaseData = new CoreCaseData();

    private static final String D8_SCREEN_HAS_MARRIAGE_BROKEN = "D8ScreenHasMarriageBroken";

    private static final String YES = "YES";

    private static final String ADULTERY = "adultery";

    @Before
    public void setup() {
        assertNotNull(validationService);

        // Populate with valid data
        coreCaseData.setD8ScreenHasMarriageBroken(YES);
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithNoData_thenValidationWillFail() {
        assertEquals(FAILED.getValue(), validationService.validate(new CoreCaseData()).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithInvalidData_thenValidationWillSucceed() {
        assertEquals(FAILED.getValue(), validationService.validate(coreCaseData).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        coreCaseData.setD8ScreenHasMarriageBroken(null);
        ValidationResponse response = validationService.validate(coreCaseData);
        assertEquals(FAILED.getValue(), response.getValidationStatus());
        // Previous versions would have stopped when 1 rule failed
        // Now get multiple reasons for failure so can no longer check error count=1
        assertTrue(response.getErrors().stream().anyMatch(e -> e.contains(D8_SCREEN_HAS_MARRIAGE_BROKEN)));
    }

    @Test
    public void givenNull_whenValidationIsCalledWithValidData_thenValidationWillFail() {
        assertEquals(FAILED.getValue(), validationService.validate(null).getValidationStatus());
    }

    @Test
    public void givenCompleteCaseData_whenValidationIsCalledWithValidData_thenValidationWillSucceed() {
        CoreCaseData coreCaseData = createDefaultCaseData();

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(SUCCESS.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingCostsClaim_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8DivorceCostsClaim(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingFinancialOrder_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8FinancialOrder(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingInferredPetitionerGender_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8InferredPetitionerGender(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingInferredRespondentGender_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8InferredRespondentGender(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingJurisdictionConnection_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8JurisdictionConnection(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingLegalProceedings_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8LegalProceedings(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingMarriageDate_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8MarriageDate(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingMarriageDateTInFuture_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().plus(100, ChronoUnit.DAYS));

        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8MarriageDate(d8MarriageDate);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingMarriagePetitionerName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8MarriagePetitionerName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingMarriageRespondentName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8MarriageRespondentName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingPetitionerContactDetailsConfidential_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8PetitionerContactDetailsConfidential(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingPetitionerFirstName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8PetitionerFirstName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingPetitionerLastName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8PetitionerLastName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingReasonForDivorce_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8ReasonForDivorce(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingReasonForDivorceAdulteryDetails_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8ReasonForDivorceAdulteryDetails(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingRespondentFirstName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8RespondentFirstName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingRespondentLastName_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8RespondentLastName(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingScreenHasMarriageBroken_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8ScreenHasMarriageBroken(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    @Test
    public void givenMissingStatementOfTruth_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        CoreCaseData coreCaseData = createDefaultCaseData();
        coreCaseData.setD8StatementOfTruth(null);

        ValidationResponse response = validationService.validate(coreCaseData);

        assertEquals(FAILED.getValue(), response.getValidationStatus());
    }

    private CoreCaseData createDefaultCaseData() {
        String d8MarriageDate = DateUtils.formatDate(Instant.now().minus(1000, ChronoUnit.DAYS));

        CoreCaseData coreCaseData = new CoreCaseData();

        coreCaseData.setD8DivorceCostsClaim(YES);
        coreCaseData.setD8FinancialOrder(YES);
        coreCaseData.setD8InferredPetitionerGender(Gender.MALE);
        coreCaseData.setD8InferredRespondentGender(Gender.FEMALE);
        coreCaseData.setD8JurisdictionConnection(Collections.emptyList());
        coreCaseData.setD8LegalProceedings(YES);
        coreCaseData.setD8MarriageDate(d8MarriageDate);
        coreCaseData.setD8MarriagePetitionerName(YES);
        coreCaseData.setD8MarriageRespondentName(YES);
        coreCaseData.setD8PetitionerContactDetailsConfidential(YES);
        coreCaseData.setD8PetitionerFirstName(YES);
        coreCaseData.setD8PetitionerLastName(YES);
        coreCaseData.setD8ReasonForDivorce(ADULTERY);
        coreCaseData.setD8ReasonForDivorceAdulteryDetails(YES);
        coreCaseData.setD8RespondentFirstName(YES);
        coreCaseData.setD8RespondentLastName(YES);
        coreCaseData.setD8ScreenHasMarriageBroken(YES);
        coreCaseData.setD8StatementOfTruth(YES);

        return coreCaseData;
    }
}
