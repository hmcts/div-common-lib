package uk.gov.hmcts.reform.divorce.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import uk.gov.hmcts.reform.divorce.mapper.config.DataFormatterConfiguration;
import uk.gov.hmcts.reform.divorce.mapper.strategy.payment.PaymentContext;
import uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce.ReasonForDivorceContext;
import uk.gov.hmcts.reform.divorce.model.ccd.CaseLink;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.service.InferredGenderService;
import uk.gov.hmcts.reform.divorce.service.SeparationDateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.join;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.toYesNoNeverUpperCase;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.toYesNoUpperCase;

@Mapper(componentModel = "spring", uses = {DocumentCollectionCCDFormatMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings({"PMD.GodClass", "common-java:DuplicatedBlocks"})
public abstract class DivorceCaseToCCDMapper {

    private static final String BLANK_SPACE = " ";
    private static final String LINE_SEPARATOR = "\n";

    private static final String SHARE_DETAILS = "share";

    @Autowired
    private ReasonForDivorceContext reasonForDivorceContext;

    @Autowired
    private PaymentContext paymentContext;

    @Autowired
    @Lazy
    private DataFormatterConfiguration dataFormatterConfiguration;

    @Autowired
    private InferredGenderService inferredGenderService;

    @Autowired
    private SeparationDateService separationDateService;

    @Mapping(source = "helpWithFeesReferenceNumber", target = "d8HelpWithFeesReferenceNumber")
    @Mapping(source = "caseReference", target = "d8caseReference")
    @Mapping(source = "divorceWho", target = "d8DivorceWho")
    @Mapping(source = "marriageDate", dateFormat = SIMPLE_DATE_FORMAT, target = "d8MarriageDate")
    @Mapping(source = "reasonForDivorceDesertionDay", target = "d8ReasonForDivorceDesertionDay")
    @Mapping(source = "reasonForDivorceDesertionMonth", target = "d8ReasonForDivorceDesertionMonth")
    @Mapping(source = "reasonForDivorceDesertionYear", target = "d8ReasonForDivorceDesertionYear")
    @Mapping(source = "reasonForDivorceDesertionDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "d8ReasonForDivorceDesertionDate")
    @Mapping(source = "countryName", target = "d8CountryName")
    @Mapping(source = "placeOfMarriage", target = "d8MarriagePlaceOfMarriage")
    @Mapping(source = "petitionerContactDetailsConfidential", target = "d8PetitionerContactDetailsConfidential")
    @Mapping(source = "respondentContactDetailsConfidential", target = "respondentContactDetailsConfidential")
    @Mapping(source = "petitionerHomeAddress.postcode", target = "d8PetitionerHomeAddress.postCode")
    @Mapping(source = "petitionerCorrespondenceAddress.postcode", target = "d8PetitionerCorrespondenceAddress.postCode")
    @Mapping(source = "respondentHomeAddress.postcode", target = "d8RespondentHomeAddress.postCode")
    @Mapping(source = "respondentCorrespondenceAddress.postcode", target = "d8RespondentCorrespondenceAddress.postCode")
    @Mapping(source = "petitionerFirstName", target = "d8PetitionerFirstName")
    @Mapping(source = "petitionerLastName", target = "d8PetitionerLastName")
    @Mapping(source = "respondentFirstName", target = "d8RespondentFirstName")
    @Mapping(source = "respondentLastName", target = "d8RespondentLastName")
    @Mapping(source = "petitionerNameChangedHowOtherDetails", target = "d8PetitionerNameChangedHowOtherDetails")
    @Mapping(source = "petitionerNameChangedHowOtherDetailsTrans", target = "d8PetitionerNameChangedHowOtherDetailsTrans")
    @Mapping(source = "petitionerNameChangedHowOtherDetailsTransLang", target = "d8PetitionerNameChangedHowOtherDetailsTransLang")
    @Mapping(source = "petitionerEmail", target = "d8PetitionerEmail")
    @Mapping(source = "petitionerPhoneNumber", target = "d8PetitionerPhoneNumber")
    @Mapping(source = "livingArrangementsLiveTogether", target = "d8LivingArrangementsLiveTogether")
    @Mapping(source = "livingArrangementsLastLivedTogetherAddress.postcode",
        target = "d8LivingArrangementsLastLivedTogethAddr.postCode")
    @Mapping(source = "reasonForDivorce", target = "d8ReasonForDivorce")
    @Mapping(source = "reasonForDivorceAdultery3rdPartyFirstName", target = "d8ReasonForDivorceAdultery3rdPartyFName")
    @Mapping(source = "reasonForDivorceAdultery3rdPartyLastName", target = "d8ReasonForDivorceAdultery3rdPartyLName")
    @Mapping(source = "reasonForDivorceAdulteryDetails", target = "d8ReasonForDivorceAdulteryDetails")
    @Mapping(source = "reasonForDivorceAdulteryDetailsTrans", target = "d8ReasonForDivorceAdulteryDetailsTrans")
    @Mapping(source = "reasonForDivorceAdulteryDetailsTransLang", target = "d8ReasonForDivorceAdulteryDetailsTransLang")
    @Mapping(source = "reasonForDivorceAdulteryWhenDetails", target = "d8ReasonForDivorceAdulteryWhenDetails")
    @Mapping(source = "reasonForDivorceAdulteryWhenDetailsTrans", target = "d8ReasonForDivorceAdulteryWhenDetailsTrans")
    @Mapping(source = "reasonForDivorceAdulteryWhenDetailsTransLang", target = "d8ReasonForDivorceAdulteryWhenDetailsTransLang")
    @Mapping(source = "reasonForDivorceAdulteryWhereDetails", target = "d8ReasonForDivorceAdulteryWhereDetails")
    @Mapping(source = "reasonForDivorceAdulteryWhereDetailsTrans", target = "d8ReasonForDivorceAdulteryWhereDetailsTrans")
    @Mapping(source = "reasonForDivorceAdulteryWhereDetailsTransLang", target = "d8ReasonForDivorceAdulteryWhereDetailsTransLang")
    @Mapping(source = "reasonForDivorceAdultery3rdAddress.postcode",
        target = "d8ReasonForDivorceAdultery3rdAddress.postCode")
    @Mapping(source = "reasonForDivorceAdulterySecondHandInfoDetails",
        target = "d8ReasonForDivorceAdultery2ndHandDetails")
    @Mapping(source = "reasonForDivorceAdulterySecondHandInfoDetailsTrans",
        target = "d8ReasonForDivorceAdultery2ndHandDetailsTrans")
    @Mapping(source = "reasonForDivorceAdulterySecondHandInfoDetailsTransLang",
        target = "d8ReasonForDivorceAdultery2ndHandDetailsTransLang")
    @Mapping(source = "legalProceedingsDetails", target = "d8LegalProceedingsDetails")
    @Mapping(source = "legalProceedingsDetailsTrans", target = "d8LegalProceedingsDetailsTrans")
    @Mapping(source = "legalProceedingsDetailsTransLang", target = "d8LegalProceedingsDetailsTransLang")
    @Mapping(source = "residualJurisdictionEligible", target = "d8ResidualJurisdictionEligible")
    @Mapping(source = "reasonForDivorceDesertionDetails", target = "d8ReasonForDivorceDesertionDetails")
    @Mapping(source = "reasonForDivorceDesertionDetailsTrans", target = "d8ReasonForDivorceDesertionDetailsTrans")
    @Mapping(source = "reasonForDivorceDesertionDetailsTransLang", target = "d8ReasonForDivorceDesertionDetailsTransLang")
    @Mapping(source = "jurisdictionConnection", target = "d8JurisdictionConnectionNewPolicy")
    @Mapping(source = "financialOrderFor", target = "d8FinancialOrderFor")
    @Mapping(source = "petitionerNameChangedHow", target = "d8PetitionerNameChangedHow")
    @Mapping(source = "legalProceedingsRelated", target = "d8LegalProceedingsRelated")
    @Mapping(source = "claimsCostsFrom", target = "d8DivorceClaimFrom")
    @Mapping(source = "marriagePetitionerName", target = "d8MarriagePetitionerName")
    @Mapping(source = "marriageRespondentName", target = "d8MarriageRespondentName")
    @Mapping(source = "reasonForDivorceSeperationDay", target = "d8ReasonForDivorceSeperationDay")
    @Mapping(source = "reasonForDivorceSeperationMonth", target = "d8ReasonForDivorceSeperationMonth")
    @Mapping(source = "reasonForDivorceSeperationYear", target = "d8ReasonForDivorceSeperationYear")
    @Mapping(source = "reasonForDivorceSeperationDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "d8ReasonForDivorceSeperationDate")
    @Mapping(source = "respondentCorrespondenceUseHomeAddress", target = "d8RespondentCorrespondenceUseHomeAddress")
    @Mapping(source = "connections", target = "d8Connections")
    @Mapping(source = "connectionSummary", target = "d8ConnectionSummary")
    @Mapping(source = "courts", target = "d8DivorceUnit")
    @Mapping(source = "marriageCertificateFiles", target = "d8DocumentsUploaded")
    @Mapping(target = "createdDate",
        expression =
            "java(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")))")
    @Mapping(source = "d8Documents", target = "d8Documents")
    @Mapping(source = "respondentSolicitorName", target = "d8RespondentSolicitorName")
    @Mapping(source = "respondentSolicitorCompany", target = "d8RespondentSolicitorCompany")
    @Mapping(source = "respondentSolicitorEmail", target = "d8RespondentSolicitorEmail")
    @Mapping(source = "respondentSolicitorPhoneNumber", target = "d8RespondentSolicitorPhone")
    @Mapping(source = "reasonForDivorceDecisionDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceDecisionDate")
    @Mapping(source = "reasonForDivorceLivingApartDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "reasonForDivorceLivingApartDate")
    @Mapping(ignore = true, target = "previousCaseId")
    @Mapping(source = "previousIssueDate", dateFormat = SIMPLE_DATE_FORMAT, target = "previousIssueDate")
    @Mapping(source = "previousReasonsForDivorce", target = "previousReasonsForDivorce")
    @Mapping(source = "previousReasonsForDivorceRefusal", target = "previousReasonsForDivorceRefusal")
    @Mapping(ignore = true, target = "refusalClarificationReason")
    @Mapping(ignore = true, target = "refusalClarificationAdditionalInfo")
    @Mapping(ignore = true, target = "refusalRejectionReason")
    @Mapping(ignore = true, target = "refusalRejectionAdditionalInfo")
    @Mapping(ignore = true, target = "refusalAdminErrorInfo")
    @Mapping(ignore = true, target = "dnOutcomeCase")
    @Mapping(source = "refusalRejectionReason", target = "previousRefusalRejectionReason")
    @Mapping(source = "refusalRejectionAdditionalInfo", target = "previousRefusalRejectionAdditionalInfo")
    @Mapping(source = "petitionerPcqId", target = "petitionerPcqId")
    @Mapping(source = "respondentPcqId", target = "respondentPcqId")
    @Mapping(source = "reasonForDivorceBehaviourDetailsTrans", target = "d8ReasonForDivorceBehaviourDetailsTrans")
    @Mapping(source = "reasonForDivorceBehaviourDetailsTransLang", target = "d8ReasonForDivorceBehaviourDetailsTransLang")
    @Mapping(source = "newLegalConnectionPolicy", target = "newLegalConnectionPolicy")
    public abstract CoreCaseData divorceCaseDataToCourtCaseData(DivorceSession divorceSession);

    @BeforeMapping
    protected void updateSeparationDate(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        separationDateService.updateSeparationDate(divorceSession);
    }

    @AfterMapping
    protected void mapReasonForDivorceBehaviourDetails(DivorceSession divorceSession,
                                                       @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getReasonForDivorceBehaviourDetails())) {
            result.setD8ReasonForDivorceBehaviourDetails(
                join(LINE_SEPARATOR, divorceSession.getReasonForDivorceBehaviourDetails())
            );
        }
    }

    @AfterMapping
    protected void mapScreenHasMarriageBroken(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ScreenHasMarriageBroken(toYesNoUpperCase(divorceSession.getScreenHasMarriageBroken()));
    }

    @AfterMapping
    protected void mapScreenHasRespondentAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ScreenHasRespondentAddress(toYesNoUpperCase(divorceSession.getScreenHasRespondentAddress()));
    }

    @AfterMapping
    protected void mapScreenHasMarriageCert(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ScreenHasMarriageCert(toYesNoUpperCase(divorceSession.getScreenHasMarriageCert()));
    }

    @AfterMapping
    protected void mapScreenHasPrinter(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ScreenHasPrinter(toYesNoUpperCase(divorceSession.getScreenHasPrinter()));
    }

    @AfterMapping
    protected void mapMarriageIsSameSexCouple(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8MarriageIsSameSexCouple(toYesNoUpperCase(divorceSession.getMarriageIsSameSexCouple()));
    }

    @AfterMapping
    protected void mapMarriedInUk(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8MarriedInUk(toYesNoUpperCase(divorceSession.getMarriedInUk()));
    }

    @AfterMapping
    protected void mapCertificateInEnglish(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8CertificateInEnglish(toYesNoUpperCase(divorceSession.getCertificateInEnglish()));
    }

    @AfterMapping
    protected void mapJurisdictionPath(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionPath(divorceSession.getJurisdictionPath());
    }

    @AfterMapping
    protected void mapCertifiedTranslation(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8CertifiedTranslation(toYesNoUpperCase(divorceSession.getCertifiedTranslation()));
    }

    @AfterMapping
    protected void mapPetitionerNameDifferentToMarriageCert(DivorceSession divorceSession,
                                                            @MappingTarget CoreCaseData result) {
        result.setD8PetitionerNameDifferentToMarriageCert(
            toYesNoUpperCase(divorceSession.getPetitionerNameDifferentToMarriageCertificate()));
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceUseHomeAddress(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        result.setD8PetitionerCorrespondenceUseHomeAddress(
            toYesNoUpperCase(divorceSession.getPetitionerCorrespondenceUseHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentNameAsOnMarriageCertificate(DivorceSession divorceSession,
                                                            @MappingTarget CoreCaseData result) {
        result.setD8RespondentNameAsOnMarriageCertificate(
            toYesNoUpperCase(divorceSession.getRespondentNameAsOnMarriageCertificate()));
    }

    @AfterMapping
    protected void mapRespondentSolicitorRepresented(DivorceSession divorceSession,
                                                     @MappingTarget CoreCaseData result) {
        result.setRespondentSolicitorRepresented(
            toYesNoUpperCase(divorceSession.getRespondentSolicitorRepresented()));
    }

    @AfterMapping
    protected void mapRespondentKnowsHomeAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8RespondentKnowsHomeAddress(toYesNoUpperCase(divorceSession.getRespondentKnowsHomeAddress()));
    }

    @AfterMapping
    protected void mapRespondentLivesAtLastAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8RespondentLivesAtLastAddress(
            toYesNoUpperCase(divorceSession.getRespondentLivesAtLastAddress()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLastLivedTogether(DivorceSession divorceSession,
                                                          @MappingTarget CoreCaseData result) {
        result.setD8LivingArrangementsLastLivedTogether(
            toYesNoNeverUpperCase(divorceSession.getLivingArrangementsLastLivedTogether()));
    }

    @AfterMapping
    protected void mapLivingArrangementsLiveTogether(DivorceSession divorceSession,
                                                     @MappingTarget CoreCaseData result) {
        result.setD8LivingArrangementsLiveTogether(
            toYesNoUpperCase(divorceSession.getLivingArrangementsLiveTogether()));
    }

    @AfterMapping
    protected void mapLegalProceedings(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8LegalProceedings(toYesNoUpperCase(divorceSession.getLegalProceedings()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAgreed(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceDesertionAgreed(
            toYesNoUpperCase(divorceSession.getReasonForDivorceDesertionAgreed()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhen(DivorceSession divorceSession,
                                                       @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceAdulteryKnowWhen(
            toYesNoUpperCase(divorceSession.getReasonForDivorceAdulteryKnowWhen()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryWishToName(DivorceSession divorceSession,
                                                         @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceAdulteryWishToName(
            toYesNoUpperCase(divorceSession.getReasonForDivorceAdulteryWishToName()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryKnowWhere(DivorceSession divorceSession,
                                                        @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceAdulteryKnowWhere(
            toYesNoUpperCase(divorceSession.getReasonForDivorceAdulteryKnowWhere()));
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulteryIsNamed(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceAdulteryIsNamed(
            toYesNoUpperCase(divorceSession.getReasonForDivorceAdulteryIsNamed()));
    }

    @AfterMapping
    protected void mapFinancialOrder(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8FinancialOrder(toYesNoUpperCase(divorceSession.getFinancialOrder()));
    }

    @AfterMapping
    protected void mapHelpWithFeesNeedHelp(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8HelpWithFeesNeedHelp(
            toYesNoUpperCase(divorceSession.getHelpWithFeesNeedHelp()));
    }

    @AfterMapping
    protected void mapHelpWithFeesAppliedForFees(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8HelpWithFeesAppliedForFees(toYesNoUpperCase(divorceSession.getHelpWithFeesAppliedForFees()));
    }

    @AfterMapping
    protected void mapApplyForDecreeNisi(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setApplyForDecreeNisi(toYesNoUpperCase(divorceSession.getApplyForDecreeNisi()));
    }

    @AfterMapping
    protected void mapDivorceCostsClaim(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8DivorceCostsClaim(toYesNoUpperCase(divorceSession.getClaimsCosts()));
    }

    @AfterMapping
    protected void mapJurisdictionConfidentLegal(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionConfidentLegal(toYesNoUpperCase(divorceSession.getJurisdictionConfidentLegal()));
    }

    @AfterMapping
    protected void mapJurisdictionLastTwelveMonths(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionLastTwelveMonths(
            toYesNoUpperCase(divorceSession.getJurisdictionLastTwelveMonths()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerDomicile(DivorceSession divorceSession,
                                                     @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionPetitionerDomicile(
            toYesNoUpperCase(divorceSession.getJurisdictionPetitionerDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionPetitionerResidence(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionPetitionerResidence(
            toYesNoUpperCase(divorceSession.getJurisdictionPetitionerResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentDomicile(DivorceSession divorceSession,
                                                     @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionRespondentDomicile(
            toYesNoUpperCase(divorceSession.getJurisdictionRespondentDomicile()));
    }

    @AfterMapping
    protected void mapJurisdictionRespondentResidence(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionRespondentResidence(
            toYesNoUpperCase(divorceSession.getJurisdictionRespondentResidence()));
    }

    @AfterMapping
    protected void mapJurisdictionHabituallyResLast6Months(DivorceSession divorceSession,
                                                           @MappingTarget CoreCaseData result) {
        result.setD8JurisdictionHabituallyResLast6Months(
            toYesNoUpperCase(divorceSession.getJurisdictionLastHabitualResident()));
    }

    @AfterMapping
    protected void mapResidualJurisdictionEligible(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ResidualJurisdictionEligible(
            toYesNoUpperCase(divorceSession.getResidualJurisdictionEligible()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowAdultery(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceShowAdultery(
            toYesNoUpperCase(divorceSession.getReasonForDivorceShowAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowUnreasonableBehavior(DivorceSession divorceSession,
                                                               @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceShowUnreasonableBehaviour(
            toYesNoUpperCase(divorceSession.getReasonForDivorceShowUnreasonableBehaviour()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowTwoYearsSeparation(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceShowTwoYearsSeparation(
            toYesNoUpperCase(divorceSession.getReasonForDivorceShowTwoYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowDesertion(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceShowDesertion(
            toYesNoUpperCase(divorceSession.getReasonForDivorceShowDesertion()));
    }

    @AfterMapping
    protected void mapReasonForDivorceLimitReasons(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceLimitReasons(
            toYesNoUpperCase(divorceSession.getReasonForDivorceLimitReasons()));
    }

    @AfterMapping
    protected void mapReasonForDivorceEnableAdultery(DivorceSession divorceSession,
                                                     @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceEnableAdultery(
            toYesNoUpperCase(divorceSession.getReasonForDivorceEnableAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionAlright(DivorceSession divorceSession,
                                                       @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceDesertionAlright(
            toYesNoUpperCase(divorceSession.getReasonForDivorceDesertionAlright()));
    }

    @AfterMapping
    protected void mapClaimsCostsAppliedForFees(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ClaimsCostsAppliedForFees(toYesNoUpperCase(divorceSession.getClaimsCostsAppliedForFees()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaimingAdultery(DivorceSession divorceSession,
                                                       @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceClaimingAdultery(
            toYesNoUpperCase(divorceSession.getReasonForDivorceClaimingAdultery()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationIsSameOrAftr(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceSeperationIsSameOrAftr(
            toYesNoUpperCase(divorceSession.getReasonForDivorceSeperationDateIsSameOrAfterLimitDate()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationInFuture(DivorceSession divorceSession,
                                                         @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceSeperationInFuture(
            toYesNoUpperCase(divorceSession.getReasonForDivorceSeperationInFuture()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionBeforeMarriage(DivorceSession divorceSession,
                                                              @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceDesertionBeforeMarriage(
            toYesNoUpperCase(divorceSession.getReasonForDivorceDesertionBeforeMarriage()));
    }

    @AfterMapping
    protected void mapReasonForDivorceDesertionInFuture(DivorceSession divorceSession,
                                                        @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceDesertionInFuture(
            toYesNoUpperCase(divorceSession.getReasonForDivorceDesertionInFuture()));
    }

    @AfterMapping
    protected void mapMarriageCanDivorce(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8MarriageCanDivorce(toYesNoUpperCase(divorceSession.getMarriageCanDivorce()));
    }

    @AfterMapping
    protected void mapMarriageIsFuture(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8MarriageIsFuture(toYesNoUpperCase(divorceSession.getMarriageIsFuture()));
    }

    @AfterMapping
    protected void mapMarriageMoreThan100(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8MarriageMoreThan100(toYesNoUpperCase(divorceSession.getMarriageMoreThan100()));
    }

    @AfterMapping
    protected void mapPetitionerHomeAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getPetitionerHomeAddress())
            && Objects.nonNull(divorceSession.getPetitionerHomeAddress().getAddressField())) {
            result.setD8DerivedPetitionerHomeAddress(
                join(LINE_SEPARATOR, divorceSession.getPetitionerHomeAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapPetitionerCorrespondenceAddress(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getPetitionerCorrespondenceAddress())
            && Objects.nonNull(divorceSession.getPetitionerCorrespondenceAddress().getAddressField())) {
            result.setD8DerivedPetitionerCorrespondenceAddress(
                join(LINE_SEPARATOR, divorceSession.getPetitionerCorrespondenceAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapRespondentHomeAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getRespondentHomeAddress())
            && Objects.nonNull(divorceSession.getRespondentHomeAddress().getAddressField())) {
            result.setD8DerivedRespondentHomeAddress(
                join(LINE_SEPARATOR, divorceSession.getRespondentHomeAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapRespondentCorrespondenceAddress(DivorceSession divorceSession,
                                                      @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getRespondentCorrespondenceAddress())
            && Objects.nonNull(divorceSession.getRespondentCorrespondenceAddress().getAddressField())) {
            result.setD8DerivedRespondentCorrespondenceAddr(
                join(LINE_SEPARATOR, divorceSession.getRespondentCorrespondenceAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapDerivedReasonForDivorceAdulteryThirdPartyName(DivorceSession divorceSession,
                                                                    @MappingTarget CoreCaseData result) {
        String adulteryThirdPartyName = StringUtils.join(divorceSession.getReasonForDivorceAdultery3rdPartyFirstName(),
            BLANK_SPACE, divorceSession.getReasonForDivorceAdultery3rdPartyLastName());
        result.setD8DerivedReasonForDivorceAdultery3dPtyNm(
            isNotBlank(adulteryThirdPartyName) ? adulteryThirdPartyName : null);
    }

    @AfterMapping
    protected void mapReasonForDivorceHasMarriage(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceHasMarriage(
            toYesNoUpperCase(divorceSession.getReasonForDivorceHasMarriageDate()));
    }

    @AfterMapping
    protected void mapReasonForDivorceShowFiveYearsSeparation(DivorceSession divorceSession,
                                                              @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceShowFiveYearsSeparation(
            toYesNoUpperCase(divorceSession.getReasonForDivorceShowFiveYearsSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceClaiming5YearSeparation(DivorceSession divorceSession,
                                                              @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceClaiming5YearSeparation(
            toYesNoUpperCase(divorceSession.getReasonForDivorceClaiming5YearSeparation()));
    }

    @AfterMapping
    protected void mapReasonForDivorceSeperationBeforeMarriage(DivorceSession divorceSession,
                                                               @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceSeperationBeforeMarriage(
            toYesNoUpperCase(divorceSession.getReasonForDivorceSeperationBeforeMarriage()));
    }

    @AfterMapping
    protected void mapDerivedPetitionerCurrentFullName(DivorceSession divorceSession,
                                                       @MappingTarget CoreCaseData result) {
        String petitionerFullName = StringUtils.join(divorceSession.getPetitionerFirstName(), BLANK_SPACE,
            divorceSession.getPetitionerLastName());
        result.setD8DerivedPetitionerCurrentFullName(isNotBlank(petitionerFullName) ? petitionerFullName : null);
    }

    @AfterMapping
    protected void mapRespondentSolicitorAddress(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getRespondentSolicitorAddress())
            && Objects.nonNull(divorceSession.getRespondentSolicitorAddress().getAddressField())) {
            result.setD8DerivedRespondentSolicitorAddr(
                join(LINE_SEPARATOR, divorceSession.getRespondentSolicitorAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapDerivedRespondentSolicitorDetails(DivorceSession divorceSession,
                                                        @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getRespondentSolicitorName())
            && Objects.nonNull(divorceSession.getRespondentSolicitorAddress())
            && Objects.nonNull(divorceSession.getRespondentSolicitorAddress().getAddressField())) {

            String solicitorAddress = join(LINE_SEPARATOR,
                divorceSession.getRespondentSolicitorAddress().getAddressField());

            String solictorDetails = join(LINE_SEPARATOR, Arrays.asList(divorceSession.getRespondentSolicitorName(),
                divorceSession.getRespondentSolicitorCompany()));

            result.setD8DerivedRespondentSolicitorDetails(join(LINE_SEPARATOR, solictorDetails, solicitorAddress));
        }
    }

    @AfterMapping
    protected void mapDerivedRespondentCurrentName(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        String respondentFullName = StringUtils.join(divorceSession.getRespondentFirstName(), BLANK_SPACE,
            divorceSession.getRespondentLastName());
        result.setD8DerivedRespondentCurrentName(isNotBlank(respondentFullName) ? respondentFullName : null);
    }

    @AfterMapping
    protected void mapDerivedLivingArrangementsLastLivedAddr(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getLivingArrangementsLastLivedTogetherAddress())
            && Objects.nonNull(divorceSession.getLivingArrangementsLastLivedTogetherAddress().getAddressField())) {
            result.setD8DerivedLivingArrangementsLastLivedAddr(join(LINE_SEPARATOR,
                divorceSession.getLivingArrangementsLastLivedTogetherAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapStatementOfTruth(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8StatementOfTruth(toYesNoUpperCase(divorceSession.getConfirmPrayer()));
    }

    @AfterMapping
    protected void mapDerivedStatementOfCase(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getReasonForDivorce())) {
            result.setD8DerivedStatementOfCase(reasonForDivorceContext.deriveStatementOfWork(divorceSession));
        }
    }

    @AfterMapping
    protected void mapDerivedReasonForDivorceAdultery3rdAddr(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getReasonForDivorceAdultery3rdAddress())
            && Objects.nonNull(divorceSession.getReasonForDivorceAdultery3rdAddress().getAddressField())) {
            result.setD8DerivedReasonForDivorceAdultery3rdAddr(
                join(LINE_SEPARATOR, divorceSession.getReasonForDivorceAdultery3rdAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapPayments(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getPayment())) {

            if (Objects.nonNull(divorceSession.getPayment().getPaymentDate())) {
                divorceSession.getPayment().setPaymentDate(LocalDate.parse(
                    divorceSession.getPayment().getPaymentDate(), DateTimeFormatter.ofPattern("ddMMyyyy"))
                    .format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT)));
            }

            result.setPayments(paymentContext.getListOfPayments(divorceSession));
        }
    }

    @AfterMapping
    protected void mapCohort(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8Cohort(dataFormatterConfiguration.getCohort());
    }

    @AfterMapping
    protected void mapInferredPetitionerGender(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getMarriageIsSameSexCouple())
            && Objects.nonNull(divorceSession.getDivorceWho())) {
            result.setD8InferredPetitionerGender(
                inferredGenderService.getPetitionerGender(divorceSession.getMarriageIsSameSexCouple(),
                    divorceSession.getDivorceWho()));
        }
    }

    @AfterMapping
    protected void mapInferredRespondentGender(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        if (Objects.nonNull(divorceSession.getDivorceWho())) {
            result.setD8InferredRespondentGender(
                inferredGenderService.getRespondentGender(divorceSession.getDivorceWho()));
        }
    }

    @AfterMapping
    protected void mapPetitionerConsent(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {
        result.setD8PetitionerConsent(toYesNoUpperCase(divorceSession.getPetitionerConsent()));
    }

    @AfterMapping
    protected void mapRespondentContactDetailsConfidential(DivorceSession divorceSession,
                                                           @MappingTarget CoreCaseData result) {
        if (Objects.isNull(divorceSession.getRespondentContactDetailsConfidential())) {
            result.setRespondentContactDetailsConfidential(SHARE_DETAILS);
        }
    }

    @AfterMapping
    protected void mapReasonForDivorceAdulterySecondHandInfo(DivorceSession divorceSession,
                                                             @MappingTarget CoreCaseData result) {
        result.setD8ReasonForDivorceAdulteryAnyInfo2ndHand(
            toYesNoUpperCase(divorceSession.getReasonForDivorceAdulterySecondHandInfo())
        );
    }

    @AfterMapping
    protected void mapTimeLivedTogetherFields(DivorceSession divorceSession,
                                              @MappingTarget CoreCaseData result) {
        reasonForDivorceContext.setLivedApartFieldsFromDivorceSession(divorceSession, result);
    }

    @AfterMapping
    protected void mapSeparationReferenceDate(DivorceSession divorceSession,
                                              @MappingTarget CoreCaseData result) {
        if (divorceSession.getLivingTogetherMonths() != null) {
            if (divorceSession.getLivingTogetherMonths() >= 6) {
                result.setReferenceDate(divorceSession.getReferenceDate());
            } else {
                result.setReferenceDate(divorceSession.getMostRecentSeparationDate());
            }
        }
    }

    @AfterMapping
    protected void mapPreviousCaseIdFields(DivorceSession divorceSession, @MappingTarget CoreCaseData result) {

        CaseLink caseLink = translateStringToCaseLink(divorceSession.getPreviousCaseId());
        result.setPreviousCaseId(caseLink);
    }


    @AfterMapping
    protected void mapLanguagePreferenceWelsh(DivorceSession divorceSession,
                                              @MappingTarget CoreCaseData result) {
        result.setLanguagePreferenceWelsh(
            toYesNoUpperCase(divorceSession.getLanguagePreferenceWelsh()));
    }

    private CaseLink translateStringToCaseLink(final String value) {
        // translate from string to CaseLink type
        if (Objects.isNull(value)) {
            return null;
        }

        return CaseLink.builder().caseReference(value).build();
    }
}
