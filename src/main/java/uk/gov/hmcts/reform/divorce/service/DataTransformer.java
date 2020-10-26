package uk.gov.hmcts.reform.divorce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

@RequiredArgsConstructor
@Component
/**
 * If you have Maps, consider using <code>DataMapTransformer</code>
 */
public class DataTransformer {

    private final DivorceCaseToCCDMapper divorceCaseToCCDMapper;
    private final CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    public CoreCaseData transformDivorceCaseDataToCourtCaseData(DivorceSession divorceSession) {
        return divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession);
    }

    public DivorceSession transformCoreCaseDataToDivorceCaseData(CoreCaseData coreCaseData) {
        return ccdCaseToDivorceMapper.coreCaseDataToDivorceCaseData(coreCaseData);
    }

}