package uk.gov.hmcts.reform.divorce.formatter.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.formatter.util.DateUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.apache.commons.lang3.StringUtils.join;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.toYesNoPascalCase;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.toYesNoUpperCase;

@Component
public class DesertionStrategy implements ReasonForDivorceStrategy {

    private static final String DESERTION = "desertion";

    private static final String LINE_SEPARATOR = "\n";

    private static final String DESERTION_STRING = "I have been deserted by my %s on the %s.";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettyDesertionDate = DateUtil.format(
            divorceSession.getReasonForDivorceDesertionDate(), "dd MMMM yyyy"
        );

        String derivedStatementOfCase = String.format(DESERTION_STRING, divorceSession.getDivorceWho(),
            prettyDesertionDate);

        return join(derivedStatementOfCase, LINE_SEPARATOR, divorceSession.getReasonForDivorceDesertionDetails());
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return DESERTION.equalsIgnoreCase(reasonForDivorce);
    }

    @Override
    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {

        coreCaseData.setDesertionLivedApartEntireTime(
            toYesNoUpperCase(divorceSession.getLivedApartEntireTime())
        );

        coreCaseData.setDesertionLivedTogetherMoreTimeThanPermitted(
            toYesNoUpperCase(divorceSession.getLivedTogetherMoreTimeThanPermitted())
        );

        coreCaseData.setDesertionTimeTogetherPermitted(
            divorceSession.getTimeLivedTogetherPermitted()
        );
    }

    @Override
    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {

        divorceSession.setLivedTogetherMoreTimeThanPermitted(
            toYesNoPascalCase(coreCaseData.getDesertionLivedTogetherMoreTimeThanPermitted())
        );

        divorceSession.setLivedApartEntireTime(
            toYesNoPascalCase(coreCaseData.getDesertionLivedApartEntireTime())
        );

        divorceSession.setTimeLivedTogetherPermitted(
            coreCaseData.getDesertionTimeTogetherPermitted()
        );
    }
}
