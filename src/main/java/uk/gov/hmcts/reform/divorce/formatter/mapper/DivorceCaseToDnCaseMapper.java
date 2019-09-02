package uk.gov.hmcts.reform.divorce.formatter.mapper;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring", uses = DocumentCollectionCCDFormatMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDnCaseMapper {

    public static final String YES = "YES";

    @Mapping(source = "files", target = "documentsUploadedDN")
    @Mapping(source = "changesDetails", target = "petitionChangedDetailsDN")
    @Mapping(source = "claimCosts", target = "divorceCostsOptionDN")
    @Mapping(source = "costsDifferentDetails", target = "costsDifferentDetails")
    @Mapping(source = "statementOfTruth", target = "statementOfTruthDN")
    @Mapping(source = "intolerable", target = "adulteryLifeIntolerable")
    @Mapping(source = "adulteryFirstFoundDate", dateFormat = SIMPLE_DATE_FORMAT, target = "adulteryDateFoundOut")
    @Mapping(source = "livedApartSinceAdultery", target = "adulteryLivedApartSinceEventDN")
    @Mapping(source = "datesLivedTogether", target = "adulteryTimeLivedTogetherDetailsDN")
    @Mapping(source = "behaviourContinuedSinceApplication", target = "behaviourStillHappeningDN")
    @Mapping(source = "lastIncidentDate", dateFormat = SIMPLE_DATE_FORMAT, target = "behaviourMostRecentIncidentDateDN")
    @Mapping(source = "livedApartSinceLastIncidentDate", target = "behaviourLivedApartSinceEventDN")
    @Mapping(source = "livedApartSinceDesertion", target = "desertionLivedApartSinceEventDN")
    @Mapping(source = "livedApartSinceSeparation", target = "separationLivedApartSinceEventDN")
    public abstract DnCaseData divorceCaseDataToDnCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void mapDnApplicationSubmittedDate(DivorceSession divorceSession, @MappingTarget DnCaseData result) {
        result.setDnApplicationSubmittedDate(LocalDate.now().format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT)));
    }

    @AfterMapping
    protected void mapPreviousCaseData(DivorceSession divorceSession,
                                       @MappingTarget CoreCaseData caseData) {
        if (divorceSession.getPreviousCaseId() == null) {
            caseData.setPreviousCaseId(null);
        }
        if (divorceSession.getPreviousReasonsForDivorce() == null) {
            caseData.setPreviousReasonsForDivorce(null);
        }
    }

    @AfterMapping
    protected void mapConfirmPetitionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        if (YES.equalsIgnoreCase(divorceSession.getStatementOfTruthChanges())
            || YES.equalsIgnoreCase(divorceSession.getStatementOfTruthNoChanges())) {
            result.setConfirmPetitionDN(YES);
        }
    }

    @AfterMapping
    protected void mapAgreeToApplyDn(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setApplyForDecreeNisi(translateToStringYesNo(divorceSession.getApplyForDecreeNisi()));
    }

    @AfterMapping
    protected void mapDocumentsUploadedQuestionDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setDocumentsUploadedQuestionDN(translateToStringYesNo(divorceSession.getUploadAnyOtherDocuments()));
    }

    @AfterMapping
    protected void mapPetitionChangedYesNoDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setPetitionChangedYesNoDN(translateToStringYesNo(divorceSession.getHasBeenChanges()));
    }

    @AfterMapping
    protected void mapBehaviourTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {

        if (StringUtils.isNotBlank(divorceSession.getLivedApartSinceLastIncidentDate())) {
            result.setBehaviourTimeLivedTogetherDetailsDN(divorceSession.getApproximateDatesOfLivingTogetherField());
        }
    }

    @AfterMapping
    protected void mapDesertionTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {

        if (StringUtils.isNotBlank(divorceSession.getLivedApartSinceDesertion())) {
            result.setDesertionTimeLivedTogetherDetailsDN(divorceSession.getApproximateDatesOfLivingTogetherField());
        }
    }

    @AfterMapping
    protected void mapSeparationTimeLivedTogetherDetailsDN(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {

        if (StringUtils.isNotBlank(divorceSession.getLivedApartSinceSeparation())) {
            result.setSeparationTimeLivedTogetherDetailsDN(divorceSession.getApproximateDatesOfLivingTogetherField());
        }
    }

    @AfterMapping
    protected void mapDesertionAskedToResumeDN(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        result.setDesertionAskedToResumeDN(translateToStringYesNo(divorceSession.getDesertionAskedToResumeDN()));
    }

    @AfterMapping
    protected void mapDesertionAskedToResumeDNRefused(DivorceSession divorceSession, @MappingTarget DnCaseData result) {

        if (StringUtils.equalsIgnoreCase(divorceSession.getDesertionAskedToResumeDN(), YES)) {
            result.setDesertionAskedToResumeDNRefused(
                translateToStringYesNo(divorceSession.getDesertionAskedToResumeDNRefused())
            );
        }
    }

    @AfterMapping
    protected void mapDesertionAskedToResumeDNDetails(DivorceSession divorceSession,
                                                          @MappingTarget DnCaseData result) {

        if (StringUtils.equalsIgnoreCase(divorceSession.getDesertionAskedToResumeDNRefused(), YES)) {
            result.setDesertionAskedToResumeDNDetails(divorceSession.getDesertionAskedToResumeDNDetails());
        }
    }

    private String translateToStringYesNo(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }
}
