package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class AosCaseToCCDMapperUTest {

    @Autowired
    private DivorceCaseToAosCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        AosCaseData expectedAosCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/aos.json", AosCaseData.class);

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/aos.json", DivorceSession.class);

        AosCaseData actualAosCaseData = mapper.divorceCaseDataToAosCaseData(divorceSession);

        assertThat(actualAosCaseData, samePropertyValuesAs(expectedAosCaseData));
    }
}
