package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8DivorceCostsClaim;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageDate;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8StatementOfTruth;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static uk.gov.hmcts.reform.divorce.utils.Constants.AMEND_COMPILER_RULE_COUNT;

public class AmendRuleCompilerTest {

    CoreCaseData coreCaseData;
    AmendRuleCompiler amendRuleCompiler;
    List<String> result;
    D8MarriageDate d8MarriageDate;
    D8DivorceCostsClaim d8DivorceCostsClaim;
    D8StatementOfTruth d8StatementOfTruth;

    @Before
    public void setup() {
        amendRuleCompiler = new AmendRuleCompiler();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
        d8MarriageDate = new D8MarriageDate();
        d8DivorceCostsClaim = new D8DivorceCostsClaim();
        d8StatementOfTruth = new D8StatementOfTruth();
    }

    @Test
    public void shouldReturnListWithMandatoryFieldErrorsWhenCoreCaseDataIsEmpty() {
        result = amendRuleCompiler.executeRules(coreCaseData);

        assertThat(result.size(), is(AMEND_COMPILER_RULE_COUNT));
        assertTrue(result.contains(d8MarriageDate.execute(coreCaseData, new ArrayList<>()).get(0)));
        assertFalse(result.contains(d8DivorceCostsClaim.execute(coreCaseData, new ArrayList<>()).get(0)));
        assertFalse(result.contains(d8StatementOfTruth.execute(coreCaseData, new ArrayList<>()).get(0)));
    }
}