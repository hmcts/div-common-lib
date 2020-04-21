package uk.gov.hmcts.reform.divorce.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig;

import java.time.LocalDate;
import java.util.Optional;

import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.MONTHS;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.WELSH;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalDateToWelshStringConverter {
    private final WelshTemplateConfig templateConfig;

    public String convert(LocalDate dateToConvert) {
        return Optional.ofNullable(dateToConvert).map(date -> {
            int day = dateToConvert.getDayOfMonth();
            int year = dateToConvert.getYear();
            int month = dateToConvert.getMonth().getValue();
            return String.join(" ", Integer.toString(day),
                    templateConfig.getTemplate().get(MONTHS).get(WELSH).get(String.valueOf(month)),
                    Integer.toString(year));
        }).orElse(null);
    }

    public String convertLocalDate(String localDateFormat) {
        return convert(LocalDate.parse(localDateFormat));
    }
}
