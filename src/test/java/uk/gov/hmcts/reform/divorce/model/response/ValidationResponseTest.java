package uk.gov.hmcts.reform.divorce.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationResponseTest {

    @Test
    public void givenNoErrorsOrWarnings_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(emptyList())
            .warnings(emptyList())
            .build();

        assertTrue(validationResponse.isValid());
    }

    @Test
    public void givenNulls_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(null)
            .warnings(null)
            .build();

        assertTrue(validationResponse.isValid());
    }

    @Test
    public void givenValuesNotSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder().build();

        assertTrue(validationResponse.isValid());
    }

    @Test
    public void givenOnlyWarningsSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(emptyList())
            .warnings(singletonList("Warning!"))
            .build();

        assertFalse(validationResponse.isValid());
    }

    @Test
    public void givenOnlyErrorsSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(singletonList("ERROR!"))
            .warnings(emptyList())
            .build();

        assertFalse(validationResponse.isValid());
    }

    @Test
    public void givenBothSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(singletonList("ERROR!"))
            .warnings(singletonList("Warning!"))
            .build();

        assertFalse(validationResponse.isValid());
    }
}
