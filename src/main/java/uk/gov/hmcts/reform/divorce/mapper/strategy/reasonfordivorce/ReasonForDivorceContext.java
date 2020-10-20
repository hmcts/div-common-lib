package uk.gov.hmcts.reform.divorce.mapper.strategy.reasonfordivorce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReasonForDivorceContext {

    private final List<ReasonForDivorceStrategy> reasonForDivorceStrategies;

    @Autowired
    public ReasonForDivorceContext(SeparationStrategy separationStrategy) {
        reasonForDivorceStrategies = Arrays.asList(
            new AdulteryStrategy(),
            new DesertionStrategy(),
            new SeparationFiveYearsStrategy(separationStrategy),
            new SeparationTwoYearsStrategy(separationStrategy),
            new UnreasonableBehaviourStrategy()
        );
    }

    public String deriveStatementOfWork(DivorceSession divorceSession) {
        return reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .map(s -> s.deriveStatementOfCase(divorceSession))
            .collect(Collectors.joining());
    }

    public void setLivedApartFieldsFromDivorceSession(DivorceSession divorceSession, CoreCaseData coreCaseData) {
        reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .forEach(s -> s.setLivedApartFieldsFromDivorceSession(divorceSession, coreCaseData));
    }

    public void setLivedApartFieldsFromCoreCaseData(CoreCaseData coreCaseData, DivorceSession divorceSession) {
        reasonForDivorceStrategies.stream()
            .filter(strategy -> strategy.accepts(divorceSession.getReasonForDivorce()))
            .forEach(s -> s.setLivedApartFieldsFromCoreCaseData(coreCaseData, divorceSession));
    }
}
