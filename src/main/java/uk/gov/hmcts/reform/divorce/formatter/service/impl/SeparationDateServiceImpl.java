package uk.gov.hmcts.reform.divorce.formatter.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.formatter.service.SeparationDateService;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Date;

@Service
public class SeparationDateServiceImpl implements SeparationDateService {

    @Override
    public void updateSeparationDate(DivorceSession divorceSession) {
        Date separationDate = divorceSession.getReasonForDivorceDecisionDate();

        if (separationDate == null
            || divorceSession.getReasonForDivorceLivingApartDate() != null
            && separationDate.before(divorceSession.getReasonForDivorceLivingApartDate())) {
            separationDate = divorceSession.getReasonForDivorceLivingApartDate();
        }

        if (separationDate == null) {
            separationDate = divorceSession.getReasonForDivorceSeperationDate();
        }

        divorceSession.setReasonForDivorceSeperationDate(separationDate);
    }
}
