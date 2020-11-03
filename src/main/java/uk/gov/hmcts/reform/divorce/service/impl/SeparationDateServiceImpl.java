package uk.gov.hmcts.reform.divorce.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.service.SeparationDateService;

import java.time.LocalDate;

@Service
public class SeparationDateServiceImpl implements SeparationDateService {

    @Override
    public void updateSeparationDate(DivorceSession divorceSession) {
        LocalDate separationDate = divorceSession.getReasonForDivorceDecisionDate();

        if (separationDate == null
            || divorceSession.getReasonForDivorceLivingApartDate() != null
            && separationDate.isBefore(divorceSession.getReasonForDivorceLivingApartDate())) {
            separationDate = divorceSession.getReasonForDivorceLivingApartDate();
        }

        if (separationDate == null) {
            separationDate = divorceSession.getReasonForDivorceSeperationDate();
        }

        divorceSession.setReasonForDivorceSeperationDate(separationDate);
    }

}