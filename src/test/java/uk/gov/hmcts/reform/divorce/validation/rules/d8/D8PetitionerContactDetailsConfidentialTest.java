package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class D8PetitionerContactDetailsConfidentialTest {

    private D8PetitionerContactDetailsConfidential rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8PetitionerContactDetailsConfidential();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8PetitionerContactDetailsConfidentialIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnResultWhenD8PetitionerContactDetailsConfidentialIsEmpty() {
        coreCaseData.setD8PetitionerContactDetailsConfidential("");
        result = rule.execute(coreCaseData, result);
        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnFalseWhenD8PetitionerContactDetailsConfidentialIsNotNull() {
        coreCaseData.setD8PetitionerContactDetailsConfidential("Yes");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8PetitionerContactDetailsConfidential can not be null or empty. Actual data is: null", result.get(0));
    }
}