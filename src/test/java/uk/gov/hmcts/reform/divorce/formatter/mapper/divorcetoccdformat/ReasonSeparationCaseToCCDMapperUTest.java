package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReasonSeparationCaseToCCDMapperUTest {

    @Autowired
    private DivorceCaseToCCDMapper mapper;

    @Test
    @Ignore
    public void shouldMapAllAndTransformAllFieldsForReasonSeparationScenario() throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/reasonseparation.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(
            "fixtures/divorcetoccdmapping/divorce/reason-separation.json", DivorceSession.class);

        CoreCaseData actualCoreCaseData = mapper.divorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

    @Test
    @Ignore
    public void shouldMapAllAndTransformAllFieldsForReasonSeparationLivedLessThan6MonthTogetherScenario()
        throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/"
                + "reasonseparation-LessThan6MonthLivedTogether.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(
            "fixtures/divorcetoccdmapping/divorce/reason-separation-LessThan6MonthLivedTogether.json",
            DivorceSession.class);

        CoreCaseData actualCoreCaseData = mapper.divorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }
}
