package uk.gov.hmcts.reform.divorce.validation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.models.request.CoreCaseData;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;
import uk.gov.hmcts.reform.divorce.models.request.ValidationRequest;
import uk.gov.hmcts.reform.divorce.models.response.ValidationResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = BeanConfig.class)
@RunWith(SpringRunner.class)
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    private CoreCaseData coreCaseData = new CoreCaseData();
    private DivorceSession divorceSession = new DivorceSession();

    @Before
    public void setup() {
        assertNotNull(validationService);

        // Populate with valid data
        coreCaseData.setD8ScreenHasMarriageBroken("YES");

        divorceSession.setScreenHasMarriageBroken("YES");
    }

    @Test
    public void givenNoFormId_whenValidationIsCalled_thenValidationWillSucceed() {
        ValidationRequest request = ValidationRequest.builder().formId("").data(new Object()).build();
        assertEquals("success", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithNoData_thenValidationWillFail() {
        ValidationRequest request = ValidationRequest.builder().formId("case").data(new CoreCaseData()).build();
        assertEquals("failed", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenSessionId_whenValidationIsCalledWithNoData_thenValidationWillFail() {
        ValidationRequest request = ValidationRequest.builder().formId("session").data(new DivorceSession()).build();
        assertEquals("failed", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithValidData_thenValidationWillSucceed() {
        ValidationRequest request = ValidationRequest.builder().formId("case").data(coreCaseData).build();
        assertEquals("success", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenSessionId_whenValidationIsCalledWithValidData_thenValidationWillSucceed() {
        ValidationRequest request = ValidationRequest.builder().formId("session").data(divorceSession).build();
        assertEquals("success", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithSessionData_thenValidationWillFail() {
        ValidationRequest request = ValidationRequest.builder().formId("case").data(divorceSession).build();
        assertEquals("failed", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenSessionId_whenValidationIsCalledWithCaseData_thenValidationWillFail() {
        ValidationRequest request = ValidationRequest.builder().formId("session").data(coreCaseData).build();
        assertEquals("failed", validationService.validate(request).getValidationStatus());
    }

    @Test
    public void givenCaseId_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        coreCaseData.setD8ScreenHasMarriageBroken(null);
        ValidationRequest request = ValidationRequest.builder().formId("case").data(coreCaseData).build();
        ValidationResponse response = validationService.validate(request);
        assertEquals("failed", response.getValidationStatus());
        assertEquals(1, response.getErrors().size());
    }

    @Test
    public void givenSessionId_whenValidationIsCalledWithInvalidData_thenValidationWillFail() {
        divorceSession.setScreenHasMarriageBroken(null);
        ValidationRequest request = ValidationRequest.builder().formId("session").data(divorceSession).build();
        ValidationResponse response = validationService.validate(request);
        assertEquals("failed", response.getValidationStatus());
        assertEquals(1, response.getErrors().size());
    }
}
