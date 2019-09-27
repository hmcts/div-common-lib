package uk.gov.hmcts.reform.divorce.formatter.mapper.ccdtodivorceformat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;

import static uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil.convertObjectToJson;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil.retrieveFileContents;
import static uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil.retrieveFileContentsAsObject;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class CoRespondentAnswersCCDToDivorceMapperTest {

    private static final String JSON_EXAMPLES_ROOT_FOLDER = "fixtures/model/";

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldMapAllFieldsForCoRespondent()
        throws URISyntaxException, IOException, JSONException {

        testJsonResultsAreAsExpected(
            "ccd/co-respondent/co-respondent-answers.json",
            "divorce/co-respondent/co-respondent-answers.json"
        );
    }

    @Test
    public void shouldMapFieldsForCoRespondent_UsingIncompleteNestedStructure()
        throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected(
            "ccd/co-respondent/co-respondent-answers-incomplete.json",
            "divorce/co-respondent/co-respondent-answers-incomplete.json"
        );
    }

    @Test
    public void shouldMapFields_WhenCoRespondentSectionDoesNotExist()
        throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected(
            "ccd/co-respondent/co-respondent-answers-empty.json",
            "divorce/co-respondent/co-respondent-answers-empty.json"
        );
    }

    private void testJsonResultsAreAsExpected(String ccdJsonFilePath, String divorceJsonFilePath)
        throws IOException, URISyntaxException, JSONException {
        CoreCaseData inputCCDData = retrieveFileContentsAsObject(JSON_EXAMPLES_ROOT_FOLDER + ccdJsonFilePath,
            CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.courtCaseDataToDivorceCaseData(inputCCDData);

        String expectedDivorceSession = retrieveFileContents(JSON_EXAMPLES_ROOT_FOLDER + divorceJsonFilePath);

        JSONAssert.assertEquals(
            expectedDivorceSession, convertObjectToJson(actualDivorceSession), false
        );
    }

}