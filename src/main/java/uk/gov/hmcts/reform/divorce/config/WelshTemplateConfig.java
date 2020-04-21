package uk.gov.hmcts.reform.divorce.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class WelshTemplateConfig {
    public static final String RELATION = "relation";
    public static final String MONTHS = "months";
    public static final String ENGLISH = "english";
    public static final String WELSH = "welsh";

    @Value("#{${template}}")
    private Map<String, Map<String,  Map<String, String>>> template;
}
