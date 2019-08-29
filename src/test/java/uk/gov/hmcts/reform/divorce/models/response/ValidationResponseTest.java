package uk.gov.hmcts.reform.divorce.models.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationResponseTest {

    @Test
    public void givenNoErrorsOrWarnings_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(Collections.emptyList())
            .warnings(Collections.emptyList())
            .build();


        assertEquals(true, validationResponse.isValid());
    }

    @Test
    public void givenNulls_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(null)
            .warnings(null)
            .build();

        assertEquals(true, validationResponse.isValid());
    }

    @Test
    public void givenValuesNotSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder().build();

        assertEquals(true, validationResponse.isValid());
    }

    @Test
    public void givenOnlyWarningsSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(Collections.emptyList())
            .warnings(Arrays.asList("Warning!"))
            .build();

        assertEquals(false, validationResponse.isValid());
    }

    @Test
    public void givenOnlyErrorsSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(Arrays.asList("ERROR!"))
            .warnings(Collections.emptyList())
            .build();

        assertEquals(false, validationResponse.isValid());
    }

    @Test
    public void givenBothSet_whenIsValidCalled_thenReturnsTrue() {
        ValidationResponse validationResponse = ValidationResponse.builder()
            .errors(Arrays.asList("ERROR!"))
            .warnings(Arrays.asList("Warning!"))
            .build();

        assertEquals(false, validationResponse.isValid());
    }

}