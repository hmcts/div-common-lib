package uk.gov.hmcts.reform.divorce.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce.ReasonForDivorceContext;
import uk.gov.hmcts.reform.divorce.model.ccd.CaseLink;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.HearingDateTime;
import uk.gov.hmcts.reform.divorce.model.ccd.Organisation;
import uk.gov.hmcts.reform.divorce.model.ccd.OrganisationPolicy;
import uk.gov.hmcts.reform.divorce.model.ccd.ServiceApplication;
import uk.gov.hmcts.reform.divorce.model.usersession.Address;
import uk.gov.hmcts.reform.divorce.model.usersession.AddressType;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.AOS;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.Answer;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.CoRespondentAnswers;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.ContactInfo;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.Costs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.toYesNoNeverPascalCase;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.toYesNoPascalCase;
import static uk.gov.hmcts.reform.divorce.utils.Constants.YES_VALUE;

@Slf4j
@Mapper(componentModel = "spring", uses = {DocumentCollectionDivorceFormatMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings({"PMD.GodClass", "common-java:DuplicatedBlocks"})
public abstract class CCDCaseToDivorceMapper {

    private static final String LINE_BREAK = "\n";

    @Autowired
    private ReasonForDivorceContext reasonForDivorceContext;

    @Mapping(source = "d8HelpWithFeesReferenceNumber", target = "helpWithFeesReferenceNumber")
    @Mapping(source = "d8caseReference", target = "caseReference")
    @Mapping(source = "d8DivorceWho", target = "divorceWho")
    @Mapping(source = "d8MarriageDate", dateFormat = SIMPLE_DATE_FORMAT, target = "marriageDate")
    @Mapping(source = "d8ReasonForDivorceDesertionDay", target = "reasonForDivorceDesertionDay")
    @Mapping(source = "d8ReasonForDivorceDesertionMonth", target = "reasonForDivorceDesertionMonth")
    @Mapping(source = "d8ReasonForDivorceDesertionYear", target = "reasonForDivorceDesertionYear")
    @Mapping(source = "d8ReasonForDivorceDesertionDate", dateFormat = SIMPLE_DATE_FORMAT, target = "reasonForDivorceDesertionDate")
    @Mapping(source = "d8CountryName", target = "countryName")
    @Mapping(source = "d8MarriagePlaceOfMarriage", target = "placeOfMarriage")
    @Mapping(source = "d8PetitionerContactDetailsConfidential", target = "petitionerContactDetailsConfidential")
    @Mapping(source = "respondentContactDetailsConfidential", target = "respondentContactDetailsConfidential")
    @Mapping(source = "d8PetitionerHomeAddress.postCode", target = "petitionerHomeAddress.postcode")
    @Mapping(source = "d8PetitionerCorrespondenceAddress.postCode", target = "petitionerCorrespondenceAddress.postcode")
    @Mapping(source = "d8RespondentHomeAddress.postCode", target = "respondentHomeAddress.postcode")
    @Mapping(source = "d8RespondentCorrespondenceAddress.postCode", target = "respondentCorrespondenceAddress.postcode")
    @Mapping(source = "d8PetitionerFirstName", target = "petitionerFirstName")
    @Mapping(source = "d8PetitionerLastName", target = "petitionerLastName")
    @Mapping(source = "d8RespondentFirstName", target = "respondentFirstName")
    @Mapping(source = "d8RespondentLastName", target = "respondentLastName")
    @Mapping(source = "d8PetitionerNameChangedHowOtherDetails", target = "petitionerNameChangedHowOtherDetails")
    @Mapping(source = "d8PetitionerNameChangedHowOtherDetailsTrans", target = "petitionerNameChangedHowOtherDetailsTrans")
    @Mapping(source = "d8PetitionerNameChangedHowOtherDetailsTransLang", target = "petitionerNameChangedHowOtherDetailsTransLang")
    @Mapping(source = "d8PetitionerEmail", target = "petitionerEmail")
    @Mapping(source = "d8PetitionerPhoneNumber", target = "petitionerPhoneNumber")
    @Mapping(source = "d8LivingArrangementsLiveTogether", target = "livingArrangementsLiveTogether")
    @Mapping(source = "d8LivingArrangementsLastLivedTogethAddr.postCode",
        target = "livingArrangementsLastLivedTogetherAddress.postcode")
    @Mapping(source = "d8ReasonForDivorce", target = "reasonForDivorce")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyFName", target = "reasonForDivorceAdultery3rdPartyFirstName")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdPartyLName", target = "reasonForDivorceAdultery3rdPartyLastName")
    @Mapping(source = "d8ReasonForDivorceAdulteryDetails", target = "reasonForDivorceAdulteryDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryDetailsTrans", target = "reasonForDivorceAdulteryDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceAdulteryDetailsTransLang", target = "reasonForDivorceAdulteryDetailsTransLang")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhenDetails", target = "reasonForDivorceAdulteryWhenDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhenDetailsTrans", target = "reasonForDivorceAdulteryWhenDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhenDetailsTransLang", target = "reasonForDivorceAdulteryWhenDetailsTransLang")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhereDetails", target = "reasonForDivorceAdulteryWhereDetails")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhereDetailsTrans", target = "reasonForDivorceAdulteryWhereDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceAdulteryWhereDetailsTransLang", target = "reasonForDivorceAdulteryWhereDetailsTransLang")
    @Mapping(source = "d8ReasonForDivorceAdultery3rdAddress.postCode",
        target = "reasonForDivorceAdultery3rdAddress.postcode")
    @Mapping(source = "d8ReasonForDivorceAdultery2ndHandDetails",
        target = "reasonForDivorceAdulterySecondHandInfoDetails")
    @Mapping(source = "d8ReasonForDivorceAdultery2ndHandDetailsTrans",
        target = "reasonForDivorceAdulterySecondHandInfoDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceAdultery2ndHandDetailsTransLang",
        target = "reasonForDivorceAdulterySecondHandInfoDetailsTransLang")
    @Mapping(source = "d8LegalProceedingsDetails", target = "legalProceedingsDetails")
    @Mapping(source = "d8LegalProceedingsDetailsTrans", target = "legalProceedingsDetailsTrans")
    @Mapping(source = "d8LegalProceedingsDetailsTransLang", target = "legalProceedingsDetailsTransLang")
    @Mapping(source = "d8ResidualJurisdictionEligible", target = "residualJurisdictionEligible")
    @Mapping(source = "d8ReasonForDivorceDesertionDetails", target = "reasonForDivorceDesertionDetails")
    @Mapping(source = "d8ReasonForDivorceDesertionDetailsTrans", target = "reasonForDivorceDesertionDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceDesertionDetailsTransLang", target = "reasonForDivorceDesertionDetailsTransLang")
    @Mapping(source = "d8JurisdictionConnection", target = "jurisdictionConnection")
    @Mapping(source = "d8JurisdictionConnectionNewPolicy", target = "jurisdictionConnectionNewPolicy")
    @Mapping(source = "d8FinancialOrderFor", target = "financialOrderFor")
    @Mapping(source = "d8PetitionerNameChangedHow", target = "petitionerNameChangedHow")
    @Mapping(source = "d8LegalProceedingsRelated", target = "legalProceedingsRelated")
    @Mapping(source = "d8DivorceClaimFrom", target = "claimsCostsFrom")
    @Mapping(source = "d8DivorceCostsClaimExplain", target = "claimsCostExplain")
    @Mapping(source = "d8MarriagePetitionerName", target = "marriagePetitionerName")
    @Mapping(source = "d8MarriageRespondentName", target = "marriageRespondentName")
    @Mapping(source = "d8ReasonForDivorceSeperationDay", target = "reasonForDivorceSeperationDay")
    @Mapping(source = "d8ReasonForDivorceSeperationMonth", target = "reasonForDivorceSeperationMonth")
    @Mapping(source = "d8ReasonForDivorceSeperationYear", target = "reasonForDivorceSeperationYear")
    @Mapping(source = "d8ReasonForDivorceSeperationDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceSeperationDate")
    @Mapping(source = "d8RespondentCorrespondenceUseHomeAddress", target = "respondentCorrespondenceUseHomeAddress")
    @Mapping(source = "d8Connections", target = "connections")
    @Mapping(source = "d8ConnectionSummary", target = "connectionSummary")
    @Mapping(source = "d8DivorceUnit", target = "courts")
    @Mapping(source = "d8DocumentsUploaded", target = "marriageCertificateFiles")
    @Mapping(source = "d8Documents", target = "d8Documents")
    @Mapping(source = "d8RespondentSolicitorName", target = "respondentSolicitorName")
    @Mapping(source = "d8RespondentSolicitorCompany", target = "respondentSolicitorCompany")
    @Mapping(source = "d8RespondentSolicitorEmail", target = "respondentSolicitorEmail")
    @Mapping(source = "d8RespondentSolicitorPhone", target = "respondentSolicitorPhoneNumber")
    @Mapping(source = "d8RespondentSolicitorAddress.postCode", target = "respondentSolicitorAddress.postcode")
    @Mapping(source = "createdDate", dateFormat = SIMPLE_DATE_FORMAT, target = "createdDate")
    @Mapping(source = "issueDate", dateFormat = SIMPLE_DATE_FORMAT, target = "issueDate")
    @Mapping(source = "dueDate", dateFormat = SIMPLE_DATE_FORMAT, target = "dueDate")
    @Mapping(source = "reasonForDivorceDecisionDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceDecisionDate")
    @Mapping(source = "reasonForDivorceLivingApartDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceLivingApartDate")
    @Mapping(source = "coRespConfirmReadPetition", target = "coRespondentAnswers.confirmReadPetition")
    @Mapping(source = "coRespAdmitAdultery", target = "coRespondentAnswers.admitAdultery")
    @Mapping(source = "coRespConsentToEmail", target = "coRespondentAnswers.contactInfo.consentToReceivingEmails")
    @Mapping(source = "coRespContactMethodIsDigital", target = "coRespondentAnswers.contactInfo.contactMethodIsDigital")
    @Mapping(source = "coRespAgreeToCosts", target = "coRespondentAnswers.costs.agreeToCosts")
    @Mapping(source = "coRespCostsReason", target = "coRespondentAnswers.costs.reason")
    @Mapping(source = "coRespCostsReasonTrans", target = "coRespondentAnswers.costs.reasonTrans")
    @Mapping(source = "coRespCostsReasonTransLang", target = "coRespondentAnswers.costs.reasonTransLang")
    @Mapping(source = "coRespDefendsDivorce", target = "coRespondentAnswers.defendsDivorce")
    @Mapping(source = "coRespEmailAddress", target = "coRespondentAnswers.contactInfo.emailAddress")
    @Mapping(source = "coRespPhoneNumber", target = "coRespondentAnswers.contactInfo.phoneNumber")
    @Mapping(source = "coRespStatementOfTruth", target = "coRespondentAnswers.statementOfTruth")
    @Mapping(source = "receivedAnswerFromCoResp", target = "coRespondentAnswers.answer.received")
    @Mapping(source = "receivedAnswerFromCoRespDate", target = "coRespondentAnswers.answer.dateReceived",
        dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "receivedAosFromCoResp", target = "coRespondentAnswers.aos.received")
    @Mapping(source = "receivedAosFromCoRespDate", target = "coRespondentAnswers.aos.dateReceived",
        dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "dueDateCoResp", target = "coRespondentAnswers.aos.dueDate",
        dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "coRespLinkedToCaseDate", target = "coRespondentAnswers.aos.linkedDate",
        dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(source = "coRespLetterHolderId", target = "coRespondentAnswers.aos.letterHolderId")
    @Mapping(source = "receivedAosFromResp", target = "receivedAosFromResp")
    @Mapping(source = "receivedAosFromRespDate", target = "receivedAosFromRespDate", dateFormat = SIMPLE_DATE_FORMAT)
    @Mapping(ignore = true, target = "previousCaseId")
    @Mapping(source = "previousIssueDate", dateFormat = SIMPLE_DATE_FORMAT, target = "previousIssueDate")
    @Mapping(source = "previousReasonsForDivorce", target = "previousReasonsForDivorce")
    @Mapping(source = "decreeNisiGrantedDate", dateFormat = SIMPLE_DATE_FORMAT, target = "decreeNisiGrantedDate")
    @Mapping(source = "decreeAbsoluteEligibleFromDate", dateFormat = SIMPLE_DATE_FORMAT, target = "decreeAbsoluteEligibleFromDate")
    @Mapping(source = "dateRespondentEligibleForDA", dateFormat = SIMPLE_DATE_FORMAT, target = "dateRespondentEligibleForDA")
    @Mapping(source = "dateCaseNoLongerEligibleForDA", dateFormat = SIMPLE_DATE_FORMAT, target = "dateCaseNoLongerEligibleForDA")
    @Mapping(source = "refusalClarificationReason", target = "refusalClarificationReason")
    @Mapping(source = "refusalClarificationAdditionalInfo", target = "refusalClarificationAdditionalInfo")
    @Mapping(source = "refusalRejectionReason", target = "refusalRejectionReason")
    @Mapping(source = "refusalRejectionAdditionalInfo", target = "refusalRejectionAdditionalInfo")
    @Mapping(source = "refusalAdminErrorInfo", target = "refusalAdminErrorInfo")
    @Mapping(source = "dnOutcomeCase", target = "dnOutcomeCase")
    @Mapping(source = "petitionerPcqId", target = "petitionerPcqId")
    @Mapping(source = "respondentPcqId", target = "respondentPcqId")
    @Mapping(source = "coRespondentPcqId", target = "coRespondentPcqId")
    @Mapping(source = "d8ReasonForDivorceBehaviourDetailsTrans", target = "reasonForDivorceBehaviourDetailsTrans")
    @Mapping(source = "d8ReasonForDivorceBehaviourDetailsTransLang", target = "reasonForDivorceBehaviourDetailsTransLang")
    @Mapping(source = "successfulServedByBailiff", target = "successfulServedByBailiff")
    @Mapping(source = "reasonFailureToServe", target = "reasonFailureToServe")
    @Mapping(source = "newLegalConnectionPolicy", target = "newLegalConnectionPolicy")
    public abstract DivorceSession coreCaseDataToDivorceCaseData(CoreCaseData coreCaseData);

    private String translateToBooleanString(final String value) {
        if (Strings.isBlank(value)) {
            return null;
        }
        return String.valueOf(YES_VALUE.equalsIgnoreCase(value));
    }

    @AfterMapping
    protected void mapLastCompletedServiceApplicationToServiceApplicationValues(CoreCaseData caseData,
                                                  @MappingTarget DivorceSession divorceSession) {

        Optional<List<ServiceApplication>> serviceApplications = Optional.ofNullable(caseData.getServiceApplications());
        if (!serviceApplications.isPresent()) {
            return;
        }

        ServiceApplication latestApplication = serviceApplications.get()
                .stream().max(ServiceApplication::compareTo).get();

        divorceSession.extractServiceApplicationValuesFrom(latestApplication);
    }

    @AfterMapping
    protected void mapExtraDocumentsToD8Documents(CoreCaseData caseData,
                                                  @MappingTarget DivorceSession divorceSession) {
        Optional.ofNullable(divorceSession.getServiceApplicationDocuments())
            .ifPresent(docs -> docs.forEach(divorceSession::addD8Document));

        Optional.ofNullable(divorceSession.getGeneralOrders())
            .ifPresent(docs -> docs.forEach(divorceSession::addD8Document));
    }

    @AfterMapping
    protected void mapRespContactMethodIsDigital(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        Optional.ofNullable(caseData.getRespContactMethodIsDigital())
            .map(YES_VALUE::equalsIgnoreCase)
            .ifPresent(divorceSession::setRespContactMethodIsDigital);
    }

    @AfterMapping
    protected void mapMarriageDate(CoreCaseData caseData,
                                   @MappingTarget DivorceSession divorceSession) {
        if (caseData.getD8MarriageDate() != null) {
            LocalDate marriageDate =
                LocalDate.parse(caseData.getD8MarriageDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

            divorceSession.setMarriageDateDay(marriageDate.getDayOfMonth());
            divorceSession.setMarriageDateMonth(marriageDate.getMonthValue());
            divorceSession.setMarriageDateYear(marriageDate.getYear());
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationDate(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        if (caseData.getD8ReasonForDivorceSeperationDate() != null) {
            LocalDate date = LocalDate.parse(
                caseData.getD8ReasonForDivorceSeperationDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

            if (caseData.getD8MarriageDate() != null) {
                divorceSession.setReasonForDivorceSeperationDateBeforeMarriageDate(String.valueOf(date.isBefore(
                    LocalDate.parse(
                        caseData.getD8MarriageDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
                )));
            }

            if (caseData.getCreatedDate() != null) {
                divorceSession.setReasonForDivorceSeperationDateInFuture(String.valueOf(date.isAfter(
                    LocalDate.parse(
                        caseData.getCreatedDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
                )));
            }

            divorceSession.setReasonForDivorceSeperationDay(date.getDayOfMonth());
            divorceSession.setReasonForDivorceSeperationMonth(date.getMonthValue());
            divorceSession.setReasonForDivorceSeperationYear(date.getYear());
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionDate(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        if (caseData.getD8ReasonForDivorceDesertionDate() != null) {
            LocalDate date = LocalDate.parse(
                caseData.getD8ReasonForDivorceDesertionDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));

            if (caseData.getCreatedDate() != null) {
                divorceSession.setReasonForDivorceDesertionDateInFuture(String.valueOf(date.isAfter(
                    LocalDate.parse(
                        caseData.getCreatedDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
                )));
            }

            divorceSession.setReasonForDivorceDesertionDay(date.getDayOfMonth());
            divorceSession.setReasonForDivorceDesertionMonth(date.getMonthValue());
            divorceSession.setReasonForDivorceDesertionYear(date.getYear());
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceBehaviourDetails(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceBehaviourDetails(
            StringUtils.isBlank(caseData.getD8ReasonForDivorceBehaviourDetails())
                ? null : Arrays.asList(caseData.getD8ReasonForDivorceBehaviourDetails().split(LINE_BREAK)));
    }

    @AfterMapping
    protected void mapScreenHasMarriageBroken(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasMarriageBroken(toYesNoPascalCase(caseData.getD8ScreenHasMarriageBroken()));
    }

    @AfterMapping
    protected void mapScreenHasRespondentAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasRespondentAddress(toYesNoPascalCase(
            caseData.getD8ScreenHasRespondentAddress()));
    }

    @AfterMapping
    protected void mapScreenHasMarriageCert(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasMarriageCert(toYesNoPascalCase(caseData.getD8ScreenHasMarriageCert()));
    }

    @AfterMapping
    protected void mapScreenHasPrinter(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setScreenHasPrinter(toYesNoPascalCase(caseData.getD8ScreenHasPrinter()));
    }

    @AfterMapping
    protected void mapMarriageIsSameSexCouple(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageIsSameSexCouple(toYesNoPascalCase(caseData.getD8MarriageIsSameSexCouple()));
    }

    @AfterMapping
    protected void mapMarriedInUk(CoreCaseData caseData,
                                  @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriedInUk(toYesNoPascalCase(caseData.getD8MarriedInUk()));
    }

    @AfterMapping
    protected void mapCertificateInEnglish(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setCertificateInEnglish(toYesNoPascalCase(caseData.getD8CertificateInEnglish()));
    }

    @AfterMapping
    protected void mapCertifiedTranslation(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setCertifiedTranslation(toYesNoPascalCase(caseData.getD8CertifiedTranslation()));
    }

    @AfterMapping
    protected void mapPetitionerNameDifferentToMarriageCert(CoreCaseData caseData,
                                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setPetitionerNameDifferentToMarriageCertificate(
            toYesNoPascalCase(caseData.getD8PetitionerNameDifferentToMarriageCert()));
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceUseHomeAddress(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setPetitionerCorrespondenceUseHomeAddress(
            toYesNoPascalCase(caseData.getD8PetitionerCorrespondenceUseHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentNameAsOnMarriageCertificate(CoreCaseData caseData,
                                                            @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentNameAsOnMarriageCertificate(
            toYesNoPascalCase(caseData.getD8RespondentNameAsOnMarriageCertificate()));
    }

    @AfterMapping
    protected void mapRespondentSolicitorReferenceDataId(CoreCaseData caseData,
                                                            @MappingTarget DivorceSession divorceSession) {
        OrganisationPolicy respondentOrganisationPolicy = caseData.getRespondentOrganisationPolicy();

        if (respondentOrganisationPolicy != null) {
            Organisation organisation = respondentOrganisationPolicy.getOrganisation();

            if (organisation != null && organisation.getOrganisationId() != null) {
                divorceSession.setRespondentSolicitorReferenceDataId(organisation.getOrganisationId());
            }
        }
    }

    @AfterMapping
    protected void mapMarriageCertificateFiles(CoreCaseData caseData,
                                               @MappingTarget DivorceSession divorceSession) {
        Optional.ofNullable(divorceSession.getMarriageCertificateFiles())
            .ifPresent(uploadedFiles -> divorceSession.setMarriageCertificateFiles(uploadedFiles.stream()
                .filter(uploadedFile -> {
                    boolean fileIdExists = uploadedFile.getId() != null;
                    if (!fileIdExists) {
                        log.warn("Missing uploaded file properties in Case ID: {} - skipping file", caseData.getD8caseReference());
                    }
                    return fileIdExists;
                })
                .collect(Collectors.toCollection(ArrayList::new))));
    }

    @AfterMapping
    protected void mapRespondentSolicitorRepresented(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentSolicitorRepresented(
            toYesNoPascalCase(caseData.getRespondentSolicitorRepresented()));
    }

    @AfterMapping
    protected void mapRespondentKnowsHomeAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentKnowsHomeAddress(
            toYesNoPascalCase(caseData.getD8RespondentKnowsHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentLivesAtLastAddress(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setRespondentLivesAtLastAddress(
            toYesNoPascalCase(caseData.getD8RespondentLivesAtLastAddress()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLastLivedTogether(CoreCaseData caseData,
                                                          @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLivingArrangementsLastLivedTogether(
            toYesNoNeverPascalCase(caseData.getD8LivingArrangementsLastLivedTogether()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLiveTogether(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLivingArrangementsLiveTogether(
            toYesNoPascalCase(caseData.getD8LivingArrangementsLiveTogether()));
    }

    @AfterMapping
    protected void mapLegalProceedings(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLegalProceedings(toYesNoPascalCase(caseData.getD8LegalProceedings()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAgreed(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionAgreed(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceDesertionAgreed()));
    }

    @AfterMapping
    protected void mapJurisdictionPath(CoreCaseData caseData, @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionPath(caseData.getD8JurisdictionPath());
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhen(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryKnowWhen(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryKnowWhen()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryWishToName(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryWishToName(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryWishToName()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhere(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryKnowWhere(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryKnowWhere()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryIsNamed(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryIsNamed(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryIsNamed()));
    }

    @AfterMapping
    protected void mapFinancialOrder(CoreCaseData caseData,
                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setFinancialOrder(toYesNoPascalCase(caseData.getD8FinancialOrder()));
    }

    @AfterMapping
    protected void mapHelpWithFeesNeedHelp(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setHelpWithFeesNeedHelp(toYesNoPascalCase(caseData.getD8HelpWithFeesNeedHelp()));
    }

    @AfterMapping
    protected void mapApplyForDecreeNisi(CoreCaseData caseData,
                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setApplyForDecreeNisi(toYesNoPascalCase(caseData.getApplyForDecreeNisi()));
    }


    @AfterMapping
    protected void mapHelpWithFeesAppliedForFees(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setHelpWithFeesAppliedForFees(
            toYesNoPascalCase(caseData.getD8HelpWithFeesAppliedForFees()));
    }

    @AfterMapping
    protected void mapDivorceCostsClaim(CoreCaseData caseData,
                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClaimsCosts(toYesNoPascalCase(caseData.getD8DivorceCostsClaim()));
    }

    @AfterMapping
    protected void mapDivorceIsNamed(CoreCaseData caseData,
                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulteryIsNamed(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryIsNamed()));
    }

    @AfterMapping
    protected void mapJurisdictionConfidentLegal(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionConfidentLegal(
            toYesNoPascalCase(caseData.getD8JurisdictionConfidentLegal()));
    }

    @AfterMapping
    protected void mapJurisdictionLastTwelveMonths(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionLastTwelveMonths(
            toYesNoPascalCase(caseData.getD8JurisdictionLastTwelveMonths()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerDomicile(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionPetitionerDomicile(
            toYesNoPascalCase(caseData.getD8JurisdictionPetitionerDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerResidence(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionPetitionerResidence(
            toYesNoPascalCase(caseData.getD8JurisdictionPetitionerResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentDomicile(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionRespondentDomicile(
            toYesNoPascalCase(caseData.getD8JurisdictionRespondentDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentResidence(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionRespondentResidence(
            toYesNoPascalCase(caseData.getD8JurisdictionRespondentResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionHabituallyResLast6Months(CoreCaseData caseData,
                                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setJurisdictionLastHabitualResident(
            toYesNoPascalCase(caseData.getD8JurisdictionHabituallyResLast6Months()));
    }

    @AfterMapping
    protected void mapResidualJurisdictionEligible(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setResidualJurisdictionEligible(
            toYesNoPascalCase(caseData.getD8ResidualJurisdictionEligible()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowAdultery(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowAdultery(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowUnreasonableBehavior(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowUnreasonableBehaviour(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowUnreasonableBehaviour()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowTwoYearsSeparation(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowTwoYearsSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowTwoYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowDesertion(CoreCaseData caseData,
                                                    @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowDesertion(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowDesertion()));
    }

    @AfterMapping
    protected void mapReasonForDivorceLimitReasons(CoreCaseData caseData,
                                                   @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceLimitReasons(
            translateToBooleanString(caseData.getD8ReasonForDivorceLimitReasons()));
    }

    @AfterMapping
    protected void mapReasonForDivorceEnableAdultery(CoreCaseData caseData,
                                                     @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceEnableAdultery(
            translateToBooleanString(caseData.getD8ReasonForDivorceEnableAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAlright(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionAlright(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionAlright()));
    }

    @AfterMapping
    protected void mapClaimsCostsAppliedForFees(CoreCaseData caseData,
                                                @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClaimsCostsAppliedForFees(
            translateToBooleanString(caseData.getD8ClaimsCostsAppliedForFees()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaimingAdultery(CoreCaseData caseData,
                                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaimingAdultery(
            translateToBooleanString(caseData.getD8ReasonForDivorceClaimingAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationIsSameOrAftr(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationDateIsSameOrAfterLimitDate(
            translateToBooleanString(caseData.getD8ReasonForDivorceSeperationIsSameOrAftr()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationInFuture(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationInFuture(
            translateToBooleanString(caseData.getD8ReasonForDivorceSeperationInFuture()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionBeforeMarriage(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionBeforeMarriage(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionBeforeMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionInFuture(CoreCaseData caseData,
                                                        @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceDesertionInFuture(
            translateToBooleanString(caseData.getD8ReasonForDivorceDesertionInFuture()));
    }

    @AfterMapping
    protected void mapMarriageCanDivorce(CoreCaseData caseData,
                                         @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageCanDivorce(
            translateToBooleanString(caseData.getD8MarriageCanDivorce()));
    }

    @AfterMapping
    protected void mapMarriageIsFuture(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageIsFuture(toYesNoPascalCase(caseData.getD8MarriageIsFuture()));
    }

    @AfterMapping
    protected void mapMarriageMoreThan100(CoreCaseData caseData,
                                          @MappingTarget DivorceSession divorceSession) {
        divorceSession.setMarriageMoreThan100(toYesNoPascalCase(caseData.getD8MarriageMoreThan100()));
    }

    @AfterMapping
    protected void mapPetitionerHomeAddress(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedPetitionerHomeAddress())) {
            if (divorceSession.getPetitionerHomeAddress() == null) {
                divorceSession.setPetitionerHomeAddress(new Address());
            }

            divorceSession.getPetitionerHomeAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedPetitionerHomeAddress().split(LINE_BREAK)));

            divorceSession.getPetitionerHomeAddress().setAddressConfirmed("true");

            if (caseData.getD8PetitionerHomeAddress() != null
                && StringUtils.isNotBlank(caseData.getD8PetitionerHomeAddress().getPostCode())) {
                divorceSession.getPetitionerHomeAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getPetitionerHomeAddress().setValidPostcode(true);
                divorceSession.getPetitionerHomeAddress().setPostcodeError("false");
            } else {
                divorceSession.getPetitionerHomeAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getPetitionerHomeAddress().setAddressAbroad(
                    caseData.getD8DerivedPetitionerHomeAddress());
            }
        }
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceAddress(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedPetitionerCorrespondenceAddress())) {
            if (divorceSession.getPetitionerCorrespondenceAddress() == null) {
                divorceSession.setPetitionerCorrespondenceAddress(new Address());
            }

            divorceSession.getPetitionerCorrespondenceAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedPetitionerCorrespondenceAddress().split(LINE_BREAK)));

            divorceSession.getPetitionerCorrespondenceAddress().setAddressConfirmed("true");

            if (caseData.getD8PetitionerCorrespondenceAddress() != null
                && StringUtils.isNotBlank(caseData.getD8PetitionerCorrespondenceAddress().getPostCode())) {
                divorceSession.getPetitionerCorrespondenceAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getPetitionerCorrespondenceAddress().setValidPostcode(true);
                divorceSession.getPetitionerCorrespondenceAddress().setPostcodeError("false");
            } else {
                divorceSession.getPetitionerCorrespondenceAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getPetitionerCorrespondenceAddress().setAddressAbroad(
                    caseData.getD8DerivedPetitionerCorrespondenceAddress());
            }
        }
    }

    @AfterMapping
    protected void mapRespondentHomeAddress(CoreCaseData caseData,
                                            @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentHomeAddress())) {
            if (divorceSession.getRespondentHomeAddress() == null) {
                divorceSession.setRespondentHomeAddress(new Address());
            }

            divorceSession.getRespondentHomeAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentHomeAddress().split(LINE_BREAK)));

            divorceSession.getRespondentHomeAddress().setAddressConfirmed("true");

            if (caseData.getD8RespondentHomeAddress() != null
                && StringUtils.isNotBlank(caseData.getD8RespondentHomeAddress().getPostCode())) {
                divorceSession.getRespondentHomeAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentHomeAddress().setValidPostcode(true);
                divorceSession.getRespondentHomeAddress().setPostcodeError("false");
            } else {
                divorceSession.getRespondentHomeAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getRespondentHomeAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentHomeAddress());
            }
        }
    }

    @AfterMapping
    protected void mapRespondentCorrespondenceAddress(CoreCaseData caseData,
                                                      @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentCorrespondenceAddr())) {
            if (divorceSession.getRespondentCorrespondenceAddress() == null) {
                divorceSession.setRespondentCorrespondenceAddress(new Address());
            }

            divorceSession.getRespondentCorrespondenceAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentCorrespondenceAddr().split(LINE_BREAK)));

            divorceSession.getRespondentCorrespondenceAddress().setAddressConfirmed("true");

            if (caseData.getD8RespondentCorrespondenceAddress() != null
                && StringUtils.isNotBlank(caseData.getD8RespondentCorrespondenceAddress().getPostCode())) {
                divorceSession.getRespondentCorrespondenceAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentCorrespondenceAddress().setValidPostcode(true);
                divorceSession.getRespondentCorrespondenceAddress().setPostcodeError("false");
            } else {
                divorceSession.getRespondentCorrespondenceAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getRespondentCorrespondenceAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentCorrespondenceAddr());
            }

        }
    }

    @AfterMapping
    protected void mapReasonForDivorceAdultery3rdAddress(CoreCaseData caseData,
                                                         @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedReasonForDivorceAdultery3rdAddr())) {
            if (divorceSession.getReasonForDivorceAdultery3rdAddress() == null) {
                divorceSession.setReasonForDivorceAdultery3rdAddress(new Address());
            }

            divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedReasonForDivorceAdultery3rdAddr().split(LINE_BREAK)));

            divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressConfirmed("true");

            if (caseData.getD8ReasonForDivorceAdultery3rdAddress() != null
                && StringUtils.isNotBlank(caseData.getD8ReasonForDivorceAdultery3rdAddress().getPostCode())) {
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getReasonForDivorceAdultery3rdAddress().setValidPostcode(true);
                divorceSession.getReasonForDivorceAdultery3rdAddress().setPostcodeError("false");
            } else {
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getReasonForDivorceAdultery3rdAddress().setAddressAbroad(
                    caseData.getD8DerivedReasonForDivorceAdultery3rdAddr());
            }

        }
    }

    @AfterMapping
    protected void mapReasonForDivorceHasMarriage(CoreCaseData caseData,
                                                  @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceHasMarriageDate(
            translateToBooleanString(caseData.getD8ReasonForDivorceHasMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowFiveYearsSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceShowFiveYearsSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceShowFiveYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaiming5YearSeparation(CoreCaseData caseData,
                                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceClaiming5YearSeparation(
            translateToBooleanString(caseData.getD8ReasonForDivorceClaiming5YearSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationBeforeMarriage(CoreCaseData caseData,
                                                               @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceSeperationBeforeMarriage(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceSeperationBeforeMarriage()));
    }

    @AfterMapping
    protected void mapDerivedLivingArrangementsLastLivedAddr(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedLivingArrangementsLastLivedAddr())) {
            if (divorceSession.getLivingArrangementsLastLivedTogetherAddress() == null) {
                divorceSession.setLivingArrangementsLastLivedTogetherAddress(new Address());
            }

            divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedLivingArrangementsLastLivedAddr().split(LINE_BREAK)));

            divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressConfirmed("true");

            if (caseData.getD8LivingArrangementsLastLivedTogethAddr() != null
                && StringUtils.isNotBlank(caseData.getD8LivingArrangementsLastLivedTogethAddr().getPostCode())) {
                divorceSession.getLivingArrangementsLastLivedTogetherAddress()
                    .setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setValidPostcode(true);
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setPostcodeError("false");
            } else {
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressType(
                    AddressType.MANUAL.getType());
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().setAddressAbroad(
                    caseData.getD8DerivedLivingArrangementsLastLivedAddr());

            }
        }
    }

    @AfterMapping
    protected void mapRespondentSolicitorAddress(CoreCaseData caseData,
                                                 @MappingTarget DivorceSession divorceSession) {
        if (StringUtils.isNotBlank(caseData.getD8DerivedRespondentSolicitorAddr())) {
            if (divorceSession.getRespondentSolicitorAddress() == null) {
                divorceSession.setRespondentSolicitorAddress(new Address());
            }

            divorceSession.getRespondentSolicitorAddress().setAddressField(
                Arrays.asList(caseData.getD8DerivedRespondentSolicitorAddr().split(LINE_BREAK)));

            divorceSession.getRespondentSolicitorAddress().setAddressConfirmed("true");

            if (caseData.getD8RespondentSolicitorAddress() != null
                && StringUtils.isNotBlank(caseData.getD8RespondentSolicitorAddress().getPostCode())) {
                divorceSession.getRespondentSolicitorAddress().setAddressType(AddressType.POST_CODE.getType());
                divorceSession.getRespondentSolicitorAddress().setValidPostcode(true);
                divorceSession.getRespondentSolicitorAddress().setPostcodeError("false");
            } else {
                divorceSession.getRespondentSolicitorAddress().setAddressType(AddressType.MANUAL.getType());
                divorceSession.getRespondentSolicitorAddress().setAddressAbroad(
                    caseData.getD8DerivedRespondentSolicitorAddr());
            }
        }
    }

    @AfterMapping
    protected void mapStatementOfTruth(CoreCaseData caseData,
                                       @MappingTarget DivorceSession divorceSession) {
        divorceSession.setConfirmPrayer(toYesNoPascalCase(caseData.getD8StatementOfTruth()));
    }

    @AfterMapping
    protected void mapPayments(CoreCaseData caseData,
                               @MappingTarget DivorceSession divorceSession) {
        if (CollectionUtils.isNotEmpty(caseData.getPayments())) {
            divorceSession.setPayment(
                caseData.getPayments().remove(caseData.getPayments().size() - 1).getValue());

            divorceSession.getPayment().setPaymentDate(LocalDate.parse(
                divorceSession.getPayment().getPaymentDate(), DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT))
                .format(DateTimeFormatter.ofPattern("ddMMyyyy")));

        }

        if (CollectionUtils.isNotEmpty(caseData.getPayments())) {
            divorceSession.setExistingPayments(caseData.getPayments());
        }
    }

    @AfterMapping
    protected void mapCoRespondentAnswers(CoreCaseData caseData,
                                          @MappingTarget DivorceSession divorceSession) {
        //Convert Yes or No answers
        divorceSession.getCoRespondentAnswers().setConfirmReadPetition(
            toYesNoPascalCase(caseData.getCoRespConfirmReadPetition()));
        divorceSession.getCoRespondentAnswers().setAdmitAdultery(
            toYesNoPascalCase(caseData.getCoRespAdmitAdultery()));
        divorceSession.getCoRespondentAnswers().getContactInfo().setConsentToReceivingEmails(
            toYesNoPascalCase(caseData.getCoRespConsentToEmail()));
        divorceSession.getCoRespondentAnswers().getContactInfo().setContactMethodIsDigital(
            toYesNoPascalCase(caseData.getCoRespContactMethodIsDigital()));
        divorceSession.getCoRespondentAnswers().getCosts().setAgreeToCosts(
            toYesNoPascalCase(caseData.getCoRespAgreeToCosts()));
        divorceSession.getCoRespondentAnswers().setDefendsDivorce(
            toYesNoPascalCase(caseData.getCoRespDefendsDivorce()));
        divorceSession.getCoRespondentAnswers().setStatementOfTruth(
            toYesNoPascalCase(caseData.getCoRespStatementOfTruth()));
        divorceSession.getCoRespondentAnswers().getAnswer().setReceived(
            toYesNoPascalCase(caseData.getReceivedAnswerFromCoResp()));
        divorceSession.getCoRespondentAnswers().getAos().setReceived(
            toYesNoPascalCase(caseData.getReceivedAosFromCoResp()));
        divorceSession.getCoRespondentAnswers().getAos().setLinked(
            toYesNoPascalCase(caseData.getCoRespLinkedToCase()));

        //Remove empty objects
        CoRespondentAnswers coRespondentAnswers = divorceSession.getCoRespondentAnswers();

        ContactInfo contactInfo = coRespondentAnswers.getContactInfo();
        if (contactInfo.getEmailAddress() == null
            && contactInfo.getConsentToReceivingEmails() == null
            && contactInfo.getContactMethodIsDigital() == null
            && contactInfo.getPhoneNumber() == null) {

            coRespondentAnswers.setContactInfo(null);

        }

        AOS aos = coRespondentAnswers.getAos();
        if (aos.getReceived() == null
            && aos.getLetterHolderId() == null
            && aos.getDateReceived() == null
            && aos.getDueDate() == null
            && aos.getLinked() == null
            && aos.getLinkedDate() == null) {

            coRespondentAnswers.setAos(null);

        }

        Answer answer = coRespondentAnswers.getAnswer();
        if (answer.getReceived() == null
            && answer.getDateReceived() == null) {

            coRespondentAnswers.setAnswer(null);

        }

        Costs costs = coRespondentAnswers.getCosts();
        if (costs.getAgreeToCosts() == null
            && costs.getReason() == null) {

            coRespondentAnswers.setCosts(null);

        }

        if (coRespondentAnswers.getContactInfo() == null
            && coRespondentAnswers.getAos() == null
            && coRespondentAnswers.getAnswer() == null
            && coRespondentAnswers.getConfirmReadPetition() == null
            && coRespondentAnswers.getStatementOfTruth() == null
            && coRespondentAnswers.getAdmitAdultery() == null
            && coRespondentAnswers.getDefendsDivorce() == null
            && coRespondentAnswers.getCosts() == null) {

            divorceSession.setCoRespondentAnswers(null);

        }

    }

    @AfterMapping
    protected void mapReceivedAosFromRespondent(CoreCaseData caseData, @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReceivedAosFromResp(toYesNoPascalCase(caseData.getReceivedAosFromResp()));
    }

    @AfterMapping
    protected void mapD8PetitionerConsent(CoreCaseData caseData,
                                          @MappingTarget DivorceSession divorceSession) {
        divorceSession.setPetitionerConsent(toYesNoPascalCase(caseData.getD8PetitionerConsent()));
    }

    @AfterMapping
    protected void mapTimeLivedTogetherFields(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        reasonForDivorceContext.setLivedApartFieldsFromCoreCaseData(caseData, divorceSession);
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulterySecondHandInfo(CoreCaseData caseData,
                                                             @MappingTarget DivorceSession divorceSession) {
        divorceSession.setReasonForDivorceAdulterySecondHandInfo(
            toYesNoPascalCase(caseData.getD8ReasonForDivorceAdulteryAnyInfo2ndHand())
        );
    }

    @AfterMapping
    protected void mapPreviousCaseId(CoreCaseData caseData, @MappingTarget DivorceSession divorceSession) {

        String caseLink = translateCaseLinkToString(caseData.getPreviousCaseId());
        divorceSession.setPreviousCaseId(caseLink);
    }

    @AfterMapping
    protected void mapCourtHearingDateAndTime(CoreCaseData caseData, @MappingTarget DivorceSession divorceSession) {
        List<CollectionMember<HearingDateTime>> hearingDateTimeList = caseData.getDateAndTimeOfHearing();
        if (CollectionUtils.isNotEmpty(hearingDateTimeList)) {
            CollectionMember<HearingDateTime> hearingDateTime = hearingDateTimeList.get(hearingDateTimeList.size() - 1);
            divorceSession.setHearingDate(hearingDateTime.getValue().getDateOfHearing());
            divorceSession.setHearingTime(hearingDateTime.getValue().getTimeOfHearing());
        }
    }

    @AfterMapping
    protected void mapClarificationDigital(CoreCaseData caseData,
                                           @MappingTarget DivorceSession divorceSession) {
        divorceSession.setClarificationDigital(
            toYesNoPascalCase(caseData.getClarificationDigital()));
    }

    @AfterMapping
    protected void mapLanguagePreferenceWelsh(CoreCaseData caseData,
                                              @MappingTarget DivorceSession divorceSession) {
        divorceSession.setLanguagePreferenceWelsh(
            toYesNoPascalCase(caseData.getLanguagePreferenceWelsh()));
    }

    private String translateCaseLinkToString(final CaseLink caseLink) {
        // translate from CaseLink type to String
        if (Objects.isNull(caseLink)) {
            return null;
        }

        return caseLink.getCaseReference();
    }
}
