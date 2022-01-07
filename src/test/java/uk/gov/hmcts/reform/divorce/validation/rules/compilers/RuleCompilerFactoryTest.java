package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleCompilerFactoryTest {

    private CoreCaseData coreCaseData;

    public static final String AMEND_PETITION_FOR_REFUSAL_REJECTION = "amendPetitionForRefusalRejection";
    public static final String NO_JURISDICTION = "noJurisdiction";
    public static final String INSUFFICENT_DETAILS = "insufficentDetails";
    public static final String NO_CRITERIA = "noCriteria";

    @Before
    public void setUp() {
        coreCaseData = new CoreCaseData();
    }

    @Test
    public void shouldBuildAmendRuleCompilerWhenRejectionReasonIsAmendForRefusalRejection() {
        coreCaseData.setRefusalRejectionReason(List.of(AMEND_PETITION_FOR_REFUSAL_REJECTION));

        RuleCompilerService ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, AMEND_PETITION_FOR_REFUSAL_REJECTION);

        assertEquals(ruleCompiler.getClass(), AmendRuleCompiler.class);
    }

    @Test
    public void shouldBuildBaseRuleCompilerWhenCaseEventIdIsNotAmendForRefusalRejection() {
        String notAmendPetition = "Other Case Event Id";

        RuleCompilerService ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, notAmendPetition);

        assertEquals(ruleCompiler.getClass(), BaseRuleCompiler.class);
    }

    @Test
    public void shouldBuildJurisdictionCompilerWhenEventIdIsAmendRefusalAndReasonsContainsNoJurisdiction() {
        coreCaseData.setRefusalRejectionReason(List.of(AMEND_PETITION_FOR_REFUSAL_REJECTION, NO_JURISDICTION));

        RuleCompilerService ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, AMEND_PETITION_FOR_REFUSAL_REJECTION);

        assertEquals(ruleCompiler.getClass(), JurisdictionAmendRuleCompiler.class);
    }

    @Test
    public void shouldBuildAboutDivorceCompilerWhenReasonsContainsNoCriteria() {
        coreCaseData.setRefusalRejectionReason(List.of(AMEND_PETITION_FOR_REFUSAL_REJECTION, NO_CRITERIA));

        RuleCompilerService ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, AMEND_PETITION_FOR_REFUSAL_REJECTION);

        assertEquals(ruleCompiler.getClass(), AboutDivorceAmendRuleCompiler.class);
    }

    @Test
    public void shouldBuildAboutDivorceCompilerWhenReasonsContainsInsufficientDetails() {
        coreCaseData.setRefusalRejectionReason(List.of(AMEND_PETITION_FOR_REFUSAL_REJECTION, INSUFFICENT_DETAILS));

        RuleCompilerService ruleCompiler = RuleCompilerFactory.getRuleCompiler(coreCaseData, AMEND_PETITION_FOR_REFUSAL_REJECTION);

        assertEquals(ruleCompiler.getClass(), AboutDivorceAmendRuleCompiler.class);
    }

}
