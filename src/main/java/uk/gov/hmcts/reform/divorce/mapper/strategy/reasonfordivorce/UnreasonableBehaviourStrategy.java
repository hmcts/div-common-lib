package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

@Component
public class UnreasonableBehaviourStrategy implements ReasonForDivorceStrategy {

    private static final String UNREASONABLE_BEHAVIOUR = "unreasonable-behaviour";
    private static final String LINE_SEPARATOR = "\n";

    @Override
    public String deriveStatementOfCase(DivorceSession divorceSession) {
        return StringUtils.join(divorceSession.getReasonForDivorceBehaviourDetails(), LINE_SEPARATOR);
    }

    @Override
    public boolean accepts(String reasonForDivorce) {
        return UNREASONABLE_BEHAVIOUR.equalsIgnoreCase(reasonForDivorce);
    }

}
