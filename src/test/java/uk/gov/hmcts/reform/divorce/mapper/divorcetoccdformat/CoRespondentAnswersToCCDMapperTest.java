package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class CoRespondentAnswersToCCDMapperTest {

    private static final String JSON_EXAMPLES_ROOT_FOLDER = "fixtures/model/";

    @Autowired
    private DivorceCaseToAosCaseMapper mapper;

    @Test
    public void shouldMapAllFieldsForCoRespondent()
            throws URISyntaxException, IOException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers.json",
                "ccd/co-respondent/co-respondent-answers.json");
    }

    @Test
    public void shouldMapFieldsForCoRespondent_UsingIncompleteNestedStructure()
            throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers-incomplete.json",
                "ccd/co-respondent/co-respondent-answers-incomplete.json");
    }

    @Test
    public void shouldMapFields_WhenCoRespondentSectionDoesNotExist()
            throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers-empty.json",
                "ccd/co-respondent/co-respondent-answers-empty.json");
    }

    @Test
    public void givenNoCostAnswer_whenMapCoRespondent_ShouldMapOtherFields()
        throws IOException, URISyntaxException, JSONException {

        testJsonResultsAreAsExpected("divorce/co-respondent/co-respondent-answers-no-cost.json",
            "ccd/co-respondent/co-respondent-answers-no-cost.json");
    }

    private void testJsonResultsAreAsExpected(String divorceJsonFilePath, String ccdJsonFilePath)
            throws IOException, URISyntaxException, JSONException {

        DivorceSession divorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(JSON_EXAMPLES_ROOT_FOLDER + divorceJsonFilePath,
                DivorceSession.class);

        AosCaseData actualAosCaseData = mapper.divorceCaseDataToAosCaseData(divorceSession);

        String expectedAosCaseData = ObjectMapperTestUtil.retrieveFileContents(JSON_EXAMPLES_ROOT_FOLDER + ccdJsonFilePath);

        JSONAssert.assertEquals(expectedAosCaseData,
                ObjectMapperTestUtil.convertObjectToJson(actualAosCaseData),
                false);
    }

}
