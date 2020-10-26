package uk.gov.hmcts.reform.divorce.mapper.ccdtodivorceformat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;

import static uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil.convertObjectToJson;
import static uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil.retrieveFileContentsAsObject;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class AddressesCaseToDivorceMapperUTest {

    private static final String CCD_CASE_DATA_PATH = "fixtures/ccdtodivorcemapping/ccd/addresscase.json";
    private static final String EXPECTED_DIVORCE_SESSION_PATH = "fixtures/ccdtodivorcemapping/divorce/addresses.json";

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllAndTransformAllFieldsForAdulteryDifferentAddressMappingScenario()
        throws URISyntaxException, IOException, JSONException {

        CoreCaseData coreCaseData = retrieveFileContentsAsObject(CCD_CASE_DATA_PATH, CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        DivorceSession expectedDivorceSession = retrieveFileContentsAsObject(EXPECTED_DIVORCE_SESSION_PATH, DivorceSession.class);
        JSONAssert.assertEquals(convertObjectToJson(expectedDivorceSession), convertObjectToJson(actualDivorceSession), true);
    }

}
