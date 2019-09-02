package uk.gov.hmcts.reform.divorce.formatter.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.divorce.formatter.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToDaCaseMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DocumentCollectionDocumentRequestMapper;
import uk.gov.hmcts.reform.divorce.formatter.service.CaseFormatterService;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.documentupdate.GeneratedDocumentInfo;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.divorce.model.DocumentType.PETITION;

@Service
public class CaseFormatterServiceImpl implements CaseFormatterService {

    private static final String D8_DOCUMENTS_GENERATED_CCD_FIELD = "D8DocumentsGenerated";

    @Autowired
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Autowired
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @Autowired
    private DocumentCollectionDocumentRequestMapper documentCollectionDocumentRequestMapper;

    @Autowired
    private DivorceCaseToAosCaseMapper divorceCaseToAosCaseMapper;

    @Autowired
    private DivorceCaseToDnCaseMapper divorceCaseToDnCaseMapper;

    @Autowired
    private DivorceCaseToDaCaseMapper divorceCaseToDaCaseMapper;

    @Override
    public CoreCaseData transformToCCDFormat(DivorceSession divorceSession, String authorisation) {
        return divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(divorceSession);
    }

    @Override
    public DivorceSession transformToDivorceSession(CoreCaseData coreCaseData) {
        return ccdCaseToDivorceMapper.courtCaseDataToDivorceCaseData(coreCaseData);
    }

    @Override
    public Map<String, Object> addDocuments(Map<String, Object> coreCaseData,
                                            List<GeneratedDocumentInfo> generatedDocumentInfos) {

        if (coreCaseData == null) {
            throw new IllegalArgumentException("Existing case data must not be null.");
        }

        if (CollectionUtils.isNotEmpty(generatedDocumentInfos)) {
            List<CollectionMember<Document>> resultDocuments = new ArrayList<>();

            List<CollectionMember<Document>> newDocuments =
                generatedDocumentInfos.stream()
                    .map(documentCollectionDocumentRequestMapper::map)
                    .collect(Collectors.toList());

            List<CollectionMember<Document>> documentsGenerated =
                new ObjectMapper().convertValue(coreCaseData.get(D8_DOCUMENTS_GENERATED_CCD_FIELD),
                    new TypeReference<List<CollectionMember<Document>>>() {});

            if (CollectionUtils.isNotEmpty(documentsGenerated)) {
                List<CollectionMember<Document>> existingDocuments = documentsGenerated.stream()
                        .filter(documentCollectionMember ->
                            !generatedDocumentInfos.stream()
                                .map(GeneratedDocumentInfo::getDocumentType)
                                .collect(Collectors.toSet())
                                .contains(documentCollectionMember.getValue().getDocumentType()))
                        .collect(Collectors.toList());

                resultDocuments.addAll(existingDocuments);
            }

            resultDocuments.addAll(newDocuments);
            coreCaseData.put(D8_DOCUMENTS_GENERATED_CCD_FIELD, resultDocuments);
        }

        return coreCaseData;
    }

    @Override
    public Map<String, Object> removeAllPetitionDocuments(Map<String, Object> coreCaseData) {
        return removeAllDocumentsByType(coreCaseData, PETITION);
    }

    @Override
    public Map<String, Object> removeAllDocumentsByType(Map<String, Object> coreCaseData, String documentType) {
        if (coreCaseData == null) {
            throw new IllegalArgumentException("Existing case data must not be null.");
        }

        List<CollectionMember<Document>> allDocuments =
            new ObjectMapper().convertValue(coreCaseData.get(D8_DOCUMENTS_GENERATED_CCD_FIELD),
                new TypeReference<List<CollectionMember<Document>>>() {
                });

        if (CollectionUtils.isNotEmpty(allDocuments)) {
            allDocuments.removeIf(documents -> isDocumentType(documents, documentType));
            coreCaseData.replace(D8_DOCUMENTS_GENERATED_CCD_FIELD, allDocuments);
        }

        return coreCaseData;
    }

    @Override
    public AosCaseData getAosCaseData(DivorceSession divorceSession) {
        return divorceCaseToAosCaseMapper.divorceCaseDataToAosCaseData(divorceSession);
    }

    @Override
    public DnCaseData getDnCaseData(DivorceSession divorceSession) {
        return divorceCaseToDnCaseMapper.divorceCaseDataToDnCaseData(divorceSession);
    }

    @Override
    public DaCaseData getDaCaseData(DivorceSession divorceSession) {
        return divorceCaseToDaCaseMapper.divorceCaseDataToDaCaseData(divorceSession);
    }


    private boolean isDocumentType(CollectionMember<Document> document, String documentType) {
        return document.getValue().getDocumentType().equalsIgnoreCase(documentType);
    }
}
