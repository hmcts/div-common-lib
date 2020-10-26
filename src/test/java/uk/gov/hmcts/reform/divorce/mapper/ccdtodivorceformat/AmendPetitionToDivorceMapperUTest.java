package uk.gov.hmcts.reform.divorce.mapper.ccdtodivorceformat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class AmendPetitionToDivorceMapperUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForAmendPetitionMappingScenario()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .convertJsonToObject(ObjectMapperTestUtil
                    .retrieveFileContents("fixtures/ccdtodivorcemapping/ccd/amend-petition-case.json"),
                CoreCaseData.class);

        DivorceSession expectedDivorceSession = ObjectMapperTestUtil
            .convertJsonToObject(ObjectMapperTestUtil
                    .retrieveFileContents("fixtures/ccdtodivorcemapping/divorce/amend-petition-divorce.json"),
                DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, Matchers.samePropertyValuesAs(expectedDivorceSession));
    }
}
