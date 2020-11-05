package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.DivorceCaseToAosCaseMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.AosCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
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

        assertThat(actualAosCaseData, Matchers.samePropertyValuesAs(expectedAosCaseData));
    }
}
