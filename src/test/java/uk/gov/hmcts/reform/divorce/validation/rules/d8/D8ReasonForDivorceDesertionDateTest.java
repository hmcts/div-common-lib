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
public class D8ReasonForDivorceDesertionDateTest {

    private D8ReasonForDivorceDesertionDate rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorceDesertionDate();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsDesertionAndD8ReasonForDivorceDesertionDateIsNull() {
        coreCaseData.setD8ReasonForDivorce("desertion");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsNotDesertionAndD8ReasonForDivorceDesertionDateIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnFalseWhenD8ReasonForDivorceDesertionDateIsNotNull() {
        coreCaseData.setD8ReasonForDivorceDesertionDate("dateString");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
        coreCaseData.setD8ReasonForDivorce("desertion");

        result = rule.execute(coreCaseData, result);

        assertEquals("D8ReasonForDivorceDesertionDate can not be null or empty. Actual data is: null",
            result.get(0));
    }
}