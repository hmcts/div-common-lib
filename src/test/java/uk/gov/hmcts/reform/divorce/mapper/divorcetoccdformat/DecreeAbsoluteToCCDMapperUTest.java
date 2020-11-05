package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.DivorceCaseToDaCaseMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class DecreeAbsoluteToCCDMapperUTest {

    @Autowired
    private DivorceCaseToDaCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        DaCaseData expectedDaCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/da.json", DaCaseData.class);

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/da.json", DivorceSession.class);

        DaCaseData actualDaCaseData = mapper.divorceCaseDataToDaCaseData(divorceSession);

        assertThat(actualDaCaseData, Matchers.samePropertyValuesAs(expectedDaCaseData));
    }

}
