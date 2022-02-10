package uk.gov.hmcts.reform.divorce.validation.rules.compilers;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class RuleCompilerFactory {

    public static final String AMEND_PETITION_FOR_REFUSAL_REJECTION = "amendPetitionForRefusalRejection";
    public static final String NO_JURISDICTION = "noJurisdiction";
    public static final String INSUFFICENT_DETAILS = "insufficentDetails";
    public static final String NO_CRITERIA = "noCriteria";

    public static RuleCompilerService getRuleCompiler(CoreCaseData coreCaseData, String caseEventId) {

        List<?> rejectionReasons = Optional.ofNullable(coreCaseData.getRefusalRejectionReason())
                    .map(List.class::cast)
                    .orElse(
                            Optional.ofNullable(coreCaseData.getPreviousRefusalRejectionReason())
                                    .map(List.class::cast)
                                    .orElse(emptyList())
                    );

        if (caseEventId.equals(AMEND_PETITION_FOR_REFUSAL_REJECTION)) {
            if (rejectionReasons.contains(NO_JURISDICTION)) {
                return new JurisdictionAmendRuleCompiler();
            }

            if (rejectionReasons.contains(NO_CRITERIA) || rejectionReasons.contains(INSUFFICENT_DETAILS)) {
                return new AboutDivorceAmendRuleCompiler();
            }

            return new AmendRuleCompiler();
        }

        return new BaseRuleCompiler();
    }
}
