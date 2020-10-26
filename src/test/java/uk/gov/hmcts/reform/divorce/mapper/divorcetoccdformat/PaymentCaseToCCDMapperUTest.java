package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class PaymentCaseToCCDMapperUTest {

    @Autowired
    private DataTransformer dataTransformer;

    @Test
    public void shouldMapAllAndTransformAllFieldsForPaymentsMappingScenario() throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/paymentcase.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/payment.json", DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

    @Test
    public void shouldAddANewPaymentToExistingPaymentsMappingScenario() throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/additionalpayment.json",
                CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/additional-payment.json",
                DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

    @Test
    public void shouldReplaceExistingPaymentWithNewPaymentWhenTransactionIdIsSameMappingScenario()
        throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/overwritepayment.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/overwrite-payment.json",
                DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

    @Test
    public void shouldAddPaymentWhenOnlyTransactionIdIsPopulated() throws URISyntaxException, IOException {

        CoreCaseData expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/paymentidonly.json", CoreCaseData.class);

        expectedCoreCaseData.setCreatedDate(LocalDate.now().format(ofPattern("yyyy-MM-dd")));

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/payment-id-only.json",
                DivorceSession.class);

        CoreCaseData actualCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        assertThat(actualCoreCaseData, samePropertyValuesAs(expectedCoreCaseData));
    }

}
