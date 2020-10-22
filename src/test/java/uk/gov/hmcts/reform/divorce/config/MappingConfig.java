package uk.gov.hmcts.reform.divorce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.divorce.mapper.config.DataFormatterConfiguration;
import uk.gov.hmcts.reform.divorce.mapper.config.DataFormatterTestConfiguration;

@Configuration
@ComponentScan
public class MappingConfig {

    @Bean
    public DataFormatterConfiguration dataFormatterConfiguration() {
        return new DataFormatterTestConfiguration();
    }

}