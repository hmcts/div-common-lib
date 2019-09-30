package uk.gov.hmcts.reform.divorce.model;

import lombok.Value;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

@Value
public class DivorceCaseWrapper {
    private CoreCaseData caseData;
    private DivorceSession divorceSession;
}
