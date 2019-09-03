package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToDnCaseMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@Ignore
public class DivorceCaseToDnCaseMapperUTest {

    @Autowired
    private DivorceCaseToDnCaseMapper mapper;

    @Test
    public void shouldMapTheFieldsProperly() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn.json", DnCaseData.class);
        expectedDnCaseData.setDnApplicationSubmittedDate(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn.json",
                DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }

    @Test
    public void shouldMapTheFieldsProperlyForBehaviour() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-behaviour.json", DnCaseData.class);
        expectedDnCaseData.setDnApplicationSubmittedDate(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-behaviour.json",
                DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }

    @Test
    public void shouldMapTheFieldsProperlyForDesertion() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-desertion.json", DnCaseData.class);
        expectedDnCaseData.setDnApplicationSubmittedDate(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-desertion.json",
                DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }

    @Test
    public void shouldMapTheFieldsProperlyForSeparation() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-separation.json", DnCaseData.class);
        expectedDnCaseData.setDnApplicationSubmittedDate(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-separation.json",
                DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }

    @Test
    public void shouldMapTheFieldsProperlyForNull() throws URISyntaxException, IOException {

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-null.json", DnCaseData.class);
        expectedDnCaseData.setDnApplicationSubmittedDate(
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-null.json",
                DivorceSession.class);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceSession);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
        // Assert that actual data has null values
        assertThat(ObjectMapperTestUtil.convertObjectToJson(actualDnCaseData), containsString("null"));
    }
}
