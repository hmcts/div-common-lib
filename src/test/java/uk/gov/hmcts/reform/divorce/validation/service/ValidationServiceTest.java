package uk.gov.hmcts.reform.divorce.validation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.response.ValidationResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static uk.gov.hmcts.reform.divorce.validation.service.ValidationStatus.FAILED;

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
        //assertEquals(1, response.getErrors().size());
    }

    @Test
    public void givenNull_whenValidationIsCalledWithValidData_thenValidationWillFail() {
        assertEquals(FAILED.getValue(), validationService.validate(null, "testplaceholder").getValidationStatus());
    }

}
