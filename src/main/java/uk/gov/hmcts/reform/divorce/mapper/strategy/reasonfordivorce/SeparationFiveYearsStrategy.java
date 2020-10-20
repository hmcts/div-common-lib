package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.utils.DateUtils;

@Component
public class SeparationFiveYearsStrategy implements ReasonForDivorceStrategy {

    private static final String SEPARATION_5_YEARS = "separation-5-years";

    private static final String SEPARATION_STRING = "I have been separated from my %s for 5 years or more from the %s.";

    private final SeparationStrategy separationStrategy;

    public SeparationFiveYearsStrategy(SeparationStrategy separationStrategy) {
        this.separationStrategy = separationStrategy;
    }

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String prettySeparationDate = DateUtils.format(
            divorceSession.getReasonForDivorceSeperationDate(), "dd MMMM yyyy"
        );

        return String.format(SEPARATION_STRING, divorceSession.getDivorceWho(), prettySeparationDate);
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return SEPARATION_5_YEARS.equalsIgnoreCase(reasonForDivorce);
    }

    @Override
    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        separationStrategy.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData);
    }

    @Override
    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {
        separationStrategy.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession);
    }
}
