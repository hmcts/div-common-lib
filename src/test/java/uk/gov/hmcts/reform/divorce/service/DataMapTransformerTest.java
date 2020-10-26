package uk.gov.hmcts.reform.divorce.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataMapTransformerTest {

    private final ObjectMapper objectMapper = ObjectMapperTestUtil.getObjectMapperInstance();

    private DataMapTransformer classUnderTest;

    private DataTransformer dataTransformer;

    private DivorceSession testDivorceSession;
    private Map<String, Object> testDivorceSessionMap;

    private CoreCaseData testCoreCaseData;
    private Map<String, Object> testCoreCaseDataMap;

    @Before
    public void setUp() {
        testDivorceSession = new DivorceSession();
        testDivorceSession.setCaseReference("123");
        testDivorceSessionMap = objectMapper.convertValue(testDivorceSession, new TypeReference<Map<String, Object>>() {
        });

        testCoreCaseData = new CoreCaseData();
        testCoreCaseData.setD8caseReference("123");
        testCoreCaseDataMap = objectMapper.convertValue(testCoreCaseData, new TypeReference<Map<String, Object>>() {
        });

        dataTransformer = mock(DataTransformer.class);
        when(dataTransformer.transformDivorceCaseDataToCourtCaseData(eq(testDivorceSession))).thenReturn(testCoreCaseData);
        when(dataTransformer.transformCoreCaseDataToDivorceCaseData(eq(testCoreCaseData))).thenReturn(testDivorceSession);
        classUnderTest = new DataMapTransformer(objectMapper, dataTransformer);
    }

    @Test
    public void shouldCallAdequateMapperForTransformingDivorceCaseDataIntoCoreCaseData() {
        Map<String, Object> returnedCoreCaseData = classUnderTest.transformDivorceCaseDataToCourtCaseData(testDivorceSessionMap);

        assertThat(returnedCoreCaseData, equalTo(testCoreCaseDataMap));
        verify(dataTransformer).transformDivorceCaseDataToCourtCaseData(eq(testDivorceSession));
    }

    @Test
    public void shouldCallAdequateMapperForTransformingCoreCaseDataIntoDivorceCaseData() {
        Map<String, Object> returnedDivorceCaseData = classUnderTest.transformCoreCaseDataToDivorceCaseData(testCoreCaseDataMap);

        assertThat(returnedDivorceCaseData, equalTo(testDivorceSessionMap));
        verify(dataTransformer).transformCoreCaseDataToDivorceCaseData(eq(testCoreCaseData));
    }

}