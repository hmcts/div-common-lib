package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8JurisdictionConnection;
import uk.gov.hmcts.reform.divorce.validation.rules.d8.D8MarriageDate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static uk.gov.hmcts.reform.divorce.utils.Constants.JURISDICTION_AMEND_COMPILER_RULE_COUNT;

public class JurisdictionAmendRuleCompilerTest {

    CoreCaseData coreCaseData;
    JurisdictionAmendRuleCompiler jurisdictionAmendRuleCompiler;
    List<String> result;
    D8MarriageDate d8MarriageDate;
    D8JurisdictionConnection d8JurisdictionConnection;

    @Before
    public void setup() {
        jurisdictionAmendRuleCompiler = new JurisdictionAmendRuleCompiler();
        coreCaseData = new CoreCaseData();
        result = new ArrayList<>();
        d8MarriageDate = new D8MarriageDate();
        d8JurisdictionConnection = new D8JurisdictionConnection();
    }

    @Test
    public void shouldReturnListWithMandatoryFieldErrorsWhenCoreCaseDataIsEmpty() {
        result = jurisdictionAmendRuleCompiler.executeRules(coreCaseData);

        assertThat(result.size(), is(JURISDICTION_AMEND_COMPILER_RULE_COUNT));
        assertTrue(result.contains(d8MarriageDate.execute(coreCaseData, new ArrayList<>()).get(0)));
        assertFalse(result.contains(d8JurisdictionConnection.execute(coreCaseData, new ArrayList<>()).get(0)));
    }
}