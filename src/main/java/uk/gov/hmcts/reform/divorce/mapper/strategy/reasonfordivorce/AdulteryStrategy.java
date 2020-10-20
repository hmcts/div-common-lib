package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.apache.commons.lang3.StringUtils.join;

@Component
public class AdulteryStrategy implements ReasonForDivorceStrategy {

    private static final String ADULTERY = "adultery";
    private static final String LINE_SEPARATOR = "\n";
    private static final String YES = "Yes";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        String derivedStatementOfCase = "";

        if (YES.equals(divorceSession.getReasonForDivorceAdulteryKnowWhere())) {
            derivedStatementOfCase = join(divorceSession.getReasonForDivorceAdulteryWhereDetails(), LINE_SEPARATOR);
        }

        if (YES.equals(divorceSession.getReasonForDivorceAdulteryKnowWhen())) {
            derivedStatementOfCase = join(derivedStatementOfCase,
                divorceSession.getReasonForDivorceAdulteryWhenDetails(), LINE_SEPARATOR);
        }

        return join(derivedStatementOfCase, divorceSession.getReasonForDivorceAdulteryDetails());
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return ADULTERY.equalsIgnoreCase(reasonForDivorce);
    }

}
