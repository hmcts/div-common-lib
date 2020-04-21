package uk.gov.hmcts.reform.divorce.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.ENGLISH;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.MONTHS;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.RELATION;
import static uk.gov.hmcts.reform.divorce.config.WelshTemplateConfig.WELSH;

/*@ContextConfiguration(
        classes = { WelshTemplateConfig.class},
        initializers = {ConfigFileApplicationContextInitializer.class} )
@TestPropertySource(properties = { "spring.config.location=classpath:application.yml" })
//@RunWith(SpringJUnit4ClassRunner.class)


 */
@ContextConfiguration(classes = WelshTemplateConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class WelshTemplateConfigTest {
    @Autowired
    private WelshTemplateConfig templateConfig;
    private Map<String, String> relations;

    @Test
    public void en_hustband() {
        relations = templateConfig.getTemplate().get(RELATION).get(ENGLISH);
        Assert.assertEquals("husband", relations.get("male"));
    }

    @Test
    public void en_wife() {
        relations = templateConfig.getTemplate().get(RELATION).get(ENGLISH);
        Assert.assertEquals("wife", relations.get("female"));
    }

    @Test
    public void welsh_hustband() {
        relations = templateConfig.getTemplate().get(RELATION).get(WELSH);
        Assert.assertEquals("gŵr", relations.get("male"));
    }

    @Test
    public void welsh__wife() {
        relations = templateConfig.getTemplate().get(RELATION).get(WELSH);
        Assert.assertEquals("gwraig", relations.get("female"));
    }

    @Test
    public void welsh_month() {
        String novMonth =  templateConfig.getTemplate().get(MONTHS)
                .get(WELSH).get("11");
        Assert.assertEquals("Tachwedd", novMonth);
    }
}