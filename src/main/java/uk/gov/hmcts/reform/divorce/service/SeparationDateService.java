package uk.gov.hmcts.reform.divorce.service;

import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

public interface SeparationDateService {
    void updateSeparationDate(DivorceSession divorceSession);
}
