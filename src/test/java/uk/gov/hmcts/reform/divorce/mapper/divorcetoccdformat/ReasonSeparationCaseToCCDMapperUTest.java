package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.service.DataTransformer;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class ReasonSeparationCaseToCCDMapperUTest {

    @Autowired
    private DataTransformer dataTransformer;

    @Test
    public void shouldMapAllAndTransformAllFieldsForReasonSeparationScenario() throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/reasonseparation.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(
            "fixtures/divorcetoccdmapping/divorce/reason-separation.json", DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

    @Test
    public void shouldMapAllAndTransformAllFieldsForReasonSeparationLivedLessThan6MonthTogetherScenario()
        throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/"
                + "reasonseparation-LessThan6MonthLivedTogether.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil.retrieveFileContentsAsObject(
            "fixtures/divorcetoccdmapping/divorce/reason-separation-LessThan6MonthLivedTogether.json",
            DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }
}
