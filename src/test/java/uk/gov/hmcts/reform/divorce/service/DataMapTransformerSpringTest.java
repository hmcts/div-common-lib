package uk.gov.hmcts.reform.divorce.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MappingConfig.class)
public class DataMapTransformerSpringTest {

    @Autowired
    private DataMapTransformer dataMapTransformer;

    @Test
    public void shouldTransformAdequately() {
        Map<String, Object> divorceSessionMap = Map.of("caseReference", "123");

        Map<String, Object> returnedCoreCaseData = dataMapTransformer.transformDivorceCaseDataToCourtCaseData(divorceSessionMap);

        assertThat(returnedCoreCaseData.get("D8caseReference"), is("123"));
    }

}
