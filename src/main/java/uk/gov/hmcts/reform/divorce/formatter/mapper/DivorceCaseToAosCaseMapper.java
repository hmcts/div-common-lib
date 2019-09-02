package uk.gov.hmcts.reform.divorce.formatter.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.model.usersession.YesNoNeverAnswer;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.CoRespondentAnswers;

import java.util.Objects;

import static java.lang.String.join;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.SIMPLE_DATE_FORMAT;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.toYesNoUpperCase;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToAosCaseMapper {

    private static final String LINE_SEPARATOR = "\n";

    @Mapping(source = "coRespondentAnswers.confirmReadPetition", target = "coRespConfirmReadPetition")
    @Mapping(source = "coRespondentAnswers.admitAdultery", target = "coRespAdmitAdultery")
    @Mapping(source = "coRespondentAnswers.contactInfo.consentToReceivingEmails", target = "coRespConsentToEmail")
    @Mapping(source = "coRespondentAnswers.contactInfo.contactMethodIsDigital", target = "coRespContactMethodIsDigital")
    @Mapping(source = "coRespondentAnswers.costs.agreeToCosts", target = "coRespAgreeToCosts")
    @Mapping(source = "coRespondentAnswers.costs.reason", target = "coRespCostsReason")
    @Mapping(source = "coRespondentAnswers.defendsDivorce", target = "coRespDefendsDivorce")
    @Mapping(source = "coRespondentAnswers.contactInfo.emailAddress", target = "coRespEmailAddress")
    @Mapping(source = "coRespondentAnswers.contactInfo.phoneNumber", target = "coRespPhoneNumber")
    @Mapping(source = "coRespondentAnswers.statementOfTruth", target = "coRespStatementOfTruth")
    @Mapping(source = "coRespondentAnswers.aos.linkedDate", dateFormat = SIMPLE_DATE_FORMAT,
        target = "coRespLinkedToCaseDate")
    @Mapping(source = "receivedAosFromRespDate", dateFormat = SIMPLE_DATE_FORMAT, target = "receivedAosFromRespDate")
    @Mapping(source = "respondentSolicitorName", target = "d8RespondentSolicitorName")
    @Mapping(source = "respondentSolicitorCompany", target = "d8RespondentSolicitorCompany")
    @Mapping(source = "respondentSolicitorEmail", target = "d8RespondentSolicitorEmail")
    @Mapping(source = "respondentSolicitorPhoneNumber", target = "d8RespondentSolicitorPhone")
    public abstract AosCaseData divorceCaseDataToAosCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void removeRespEmailAddress(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        result.setRespEmailAddress(null);
    }

    @AfterMapping
    protected void setDigital(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        result.setRespContactMethodIsDigital("YES");
    }

    @AfterMapping
    protected void setReceivedAosFromResp(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        if (StringUtils.isNotEmpty(divorceSession.getReceivedAosFromResp())) {
            result.setReceivedAosFromResp(toYesNoUpperCase(
                    YesNoNeverAnswer.fromInput(divorceSession.getReceivedAosFromResp()).getAnswer()
                )
            );
        }
    }

    @AfterMapping
    protected void mapRespondentSolicitorAddress(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        if (Objects.nonNull(divorceSession.getRespondentSolicitorAddress())
            && Objects.nonNull(divorceSession.getRespondentSolicitorAddress().getAddressField())) {
            result.setD8DerivedRespondentSolicitorAddr(
                join(LINE_SEPARATOR, divorceSession.getRespondentSolicitorAddress().getAddressField()));
        }
    }

    @AfterMapping
    protected void mapCoRespondentFields(DivorceSession divorceSession, @MappingTarget AosCaseData result) {
        CoRespondentAnswers coRespondentAnswers = divorceSession.getCoRespondentAnswers();

        if (coRespondentAnswers != null) {
            result.setCoRespConfirmReadPetition(toYesNoUpperCase(coRespondentAnswers.getConfirmReadPetition()));
            result.setCoRespStatementOfTruth(toYesNoUpperCase(coRespondentAnswers.getStatementOfTruth()));
            result.setCoRespAdmitAdultery(toYesNoUpperCase(coRespondentAnswers.getAdmitAdultery()));
            result.setCoRespDefendsDivorce(toYesNoUpperCase(coRespondentAnswers.getDefendsDivorce()));
            if (coRespondentAnswers.getCosts() != null) {
                result.setCoRespAgreeToCosts(toYesNoUpperCase(coRespondentAnswers.getCosts().getAgreeToCosts()));
            }
            if (coRespondentAnswers.getAos() != null) {
                result.setCoRespLinkedToCase(toYesNoUpperCase(coRespondentAnswers.getAos().getLinked()));
            }
            result.setCoRespConsentToEmail(
                toYesNoUpperCase(coRespondentAnswers.getContactInfo().getConsentToReceivingEmails()));
            result.setCoRespContactMethodIsDigital(
                toYesNoUpperCase(coRespondentAnswers.getContactInfo().getContactMethodIsDigital()));
        }
    }

}
