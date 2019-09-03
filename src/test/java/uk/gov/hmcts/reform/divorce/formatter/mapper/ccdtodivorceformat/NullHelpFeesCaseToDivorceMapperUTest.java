package uk.gov.hmcts.reform.divorce.formatter.mapper.ccdtodivorceformat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class NullHelpFeesCaseToDivorceMapperUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    @Ignore
    public void shouldMapAllAndTransformAllFieldsForNullHelpFeesFieldMappingScenario()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject(
                "fixtures/ccdtodivorcemapping/ccd/helpfeesnullcase.json",
                CoreCaseData.class
            );

        DivorceSession expectedDivorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject(
                "fixtures/ccdtodivorcemapping/divorce/help-fees-null.json",
                DivorceSession.class
            );

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, samePropertyValuesAs(expectedDivorceSession));
    }
}
