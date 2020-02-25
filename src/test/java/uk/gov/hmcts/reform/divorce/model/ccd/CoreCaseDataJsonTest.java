package uk.gov.hmcts.reform.divorce.model.ccd;

import org.junit.Test;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CoreCaseDataJsonTest {

    private static final String expectedJsonPath = "fixtures/model/ccd/CoreCaseData.json";
    private static final String inputJsonPath = "fixtures/model/ccd/InputCoreCaseDataMap.json";

    @Test
    public void givenJsonOfCoreCaseDataModel_whenMappedToCoreCaseData_thenReturnAllModelledProperties()
        throws Exception {
        Map expectedCoreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject(expectedJsonPath, Map.class);

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject(inputJsonPath, CoreCaseData.class);
        Map coreCaseDataMap = ObjectMapperTestUtil
            .convertJsonToObject(ObjectMapperTestUtil.convertObjectToJson(coreCaseData), Map.class);

        assertEquals(expectedCoreCaseData, coreCaseDataMap);
    }
}
