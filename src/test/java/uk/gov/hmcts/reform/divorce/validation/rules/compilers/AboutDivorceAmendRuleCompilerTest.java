package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8FinancialOrder;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8LegalProceedings;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageDate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static uk.gov.hmcts.reform.divorce.utils.Constants.ABOUT_DIVORCE_AMEND_COMPILER_RULE_COUNT;

public class AboutDivorceAmendRuleCompilerTest {

    CoreCaseData coreCaseData;
    AboutDivorceAmendRuleCompiler aboutDivorceAmendRuleCompiler;
    List<String> result;
    D8MarriageDate d8MarriageDate;
    D8LegalProceedings d8LegalProceedings;
    D8FinancialOrder d8FinancialOrder;

    @Before
    public void setup() {
        aboutDivorceAmendRuleCompiler = new AboutDivorceAmendRuleCompiler();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
        d8MarriageDate = new D8MarriageDate();
        d8LegalProceedings = new D8LegalProceedings();
        d8FinancialOrder = new D8FinancialOrder();
    }

    @Test
    public void shouldReturnListWithMandatoryFieldErrorsWhenCoreCaseDataIsEmpty() {
        result = aboutDivorceAmendRuleCompiler.executeRules(coreCaseData);

        assertThat(result.size(), is(ABOUT_DIVORCE_AMEND_COMPILER_RULE_COUNT));
        assertTrue(result.contains(d8MarriageDate.execute(coreCaseData, new ArrayList<>()).get(0)));
        assertFalse(result.contains(d8LegalProceedings.execute(coreCaseData, new ArrayList<>()).get(0)));
        assertFalse(result.contains(d8FinancialOrder.execute(coreCaseData, new ArrayList<>()).get(0)));
    }
}