package uk.gov.hmcts.reform.divorce.utils;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.Mockito.when;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.MONTHS;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.WELSH;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateToWelshStringConverterTest {
    @Mock
    WelshTemplateConfig templateConfig;

    @InjectMocks
    private LocalDateToWelshStringConverter localDateToWelshStringConverter;

    @Before
    public void setUp() {
        Map<String, String> welshMonth = ImmutableMap.of("11", "Tachwedd",
                "12", "Rhagfyr",
                "3", "Mawrth", "6", "Mehefin");
        Map<String, Map<String, String>> months = ImmutableMap.of(WELSH, welshMonth);


        Map<String, Map<String, Map<String, String>>> template =
                ImmutableMap.of(MONTHS, months);

        when(templateConfig.getTemplate()).thenReturn(template);

    }

    @Test
    public void testLocalDateConvertedToWelsh() {
        String localDate = "2020-12-27";
        String welshDate = localDateToWelshStringConverter.convertLocalDate(localDate);
        Assert.assertEquals("27 Rhagfyr 2020", welshDate);
    }

    @Test
    public void testLocalDateConvertedToWelshString() {
        LocalDate localDate = LocalDate.of(2020, 12, 27);
        String welshDate = localDateToWelshStringConverter.convert(localDate);
        Assert.assertEquals("27 Rhagfyr 2020", welshDate);
    }

}
