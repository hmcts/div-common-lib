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
public class D8ReasonForDivorceSeperationDateTest {

    private D8ReasonForDivorceSeperationDate rule;
    private CoreCaseData coreCaseData;
    private List<String> result;

    @Before
    public void setup() {
        rule = new D8ReasonForDivorceSeperationDate();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeperation2YearsAndD8ReasonForDivorceSeperationDateIsNull() {
        coreCaseData.setD8ReasonForDivorce("separation-2-years");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenFactIsNotSeperationAndD8ReasonForDivorceSeperationDateIsNull() {
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenShouldReturnTrueWhenFactIsSeperation5YearsAndD8ReasonForDivorceSeperationDateIsNull() {
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(false));
    }

    @Test
    public void whenShouldReturnFalseWhenD8ReasonForDivorceSeperationDateIsNotNull() {
        coreCaseData.setD8ReasonForDivorceSeperationDate("dateString");

        result = rule.execute(coreCaseData, result);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void thenShouldReturnErrorMessageWithNull() {
//        rule.setCoreCaseData(coreCaseData);
//
//        rule.setResult(new ArrayList<>());
//        rule.then();
        coreCaseData.setD8ReasonForDivorce("separation-5-years");
        result = rule.execute(coreCaseData, result);

        assertEquals("D8ReasonForDivorceSeperationDate can not be null or empty. Actual data is: null",
            result.get(0));
    }
}