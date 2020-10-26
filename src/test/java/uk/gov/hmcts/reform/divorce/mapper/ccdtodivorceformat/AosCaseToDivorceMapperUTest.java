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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.Constants.NO_VALUE;
import static uk.gov.hmcts.reform.divorce.utils.Constants.YES_VALUE;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class AosCaseToDivorceMapperUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/aos.json", CoreCaseData.class);

        DivorceSession expectedDivorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/divorce/aos.json", DivorceSession.class);

        DivorceSession actualDivorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        assertThat(actualDivorceSession, Matchers.samePropertyValuesAs(expectedDivorceSession));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenValueIsYes() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setRespContactMethodIsDigital(YES_VALUE);

        DivorceSession divorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(true));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenValueIsNo() {
        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setRespContactMethodIsDigital(NO_VALUE);

        DivorceSession divorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(false));
    }

    @Test
    public void shouldTransformRespondentDigitalToDivorceSessionAsBoolean_whenNotSet() {
        CoreCaseData coreCaseData = new CoreCaseData();

        DivorceSession divorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        assertThat(divorceSession.getRespContactMethodIsDigital(), is(nullValue()));
    }
}
