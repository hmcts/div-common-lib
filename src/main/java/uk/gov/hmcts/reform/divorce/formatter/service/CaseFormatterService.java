package uk.gov.hmcts.reform.divorce.formatter.service;

import uk.gov.hmcts.reform.divorce.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnRefusalCaseData;
import uk.gov.hmcts.reform.divorce.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.List;
import java.util.Map;

public interface CaseFormatterService {

    CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation);

    DivorceSession transformToDivorceSession(CoreCaseData coreCaseData);

    Map<String, Object> addDocuments(Map<String, Object> coreCaseData,
                                     List<GeneratedDocumentInfo> generatedDocumentInfos);

    Map<String, Object> removeAllPetitionDocuments(Map<String, Object> coreCaseData);

    Map<String, Object> removeAllDocumentsByType(Map<String, Object> coreCaseData, String documentType);

    AosCaseData getAosCaseData(DivorceSession divorceSession);

    DnCaseData getDnCaseData(DivorceSession divorceSession);

    DnRefusalCaseData getDnRefusalCaseData(DivorceCaseWrapper divorceCaseWrapper);

    DaCaseData getDaCaseData(DivorceSession divorceSession);

}
