package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

public interface ReasonForDivorceStrategy {

    boolean accepts(String reasonForDivorce);

    String deriveStatementOfCase(DivorceSession divorceSession);

    default void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        //only applicable for some divorce reasons
    }

    default void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {
        //only applicable for some divorce reasons
    }
}
