package uk.gov.hmcts.reform.divorce.formatter.service;

import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

public interface SeparationDateService {
    void updateSeparationDate(DivorceSession divorceSession);
}
