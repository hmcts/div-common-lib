package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class D8PetitionerFirstNameTest {

    private D8PetitionerFirstName rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8PetitionerFirstName();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void shouldReturnResultWhenD8PetitionerFirstNameIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnResultWhenD8PetitionerFirstNameIsEmpty() {
        coreCaseData.setD8PetitionerFirstName("");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnEmptyResultWhenD8PetitionerFirstNameIsNotNull() {
        coreCaseData.setD8PetitionerFirstName("Yes");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnCorrectErrorMessageWithNull() {
        result = rule.execute(coreCaseData, result);

        assertEquals("D8PetitionerFirstName can not be null or empty. Actual data is: null", result.get(0));
    }
}