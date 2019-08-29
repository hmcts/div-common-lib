package uk.gov.hmcts.reform.divorce.validation.rules;

import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RulesConfig {

    public static final String D8_RULEBOOK = "uk.gov.hmcts.reform.divorce.validation.rules.d8";

    @SuppressWarnings("unchecked")
    @Bean("D8RuleBook")
    public RuleBook<List<String>> d8RuleBook() {
        return new RuleBookRunner(D8_RULEBOOK);
    }
}
