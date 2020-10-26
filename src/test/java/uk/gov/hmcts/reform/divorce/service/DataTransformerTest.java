package uk.gov.hmcts.reform.divorce.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.mapper.DivorceCaseToCCDMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataTransformerTest {

    @Mock
    private DivorceCaseToCCDMapper divorceCaseToCCDMapper;

    @Mock
    private CCDCaseToDivorceMapper ccdCaseToDivorceMapper;

    @InjectMocks
    private DataTransformer dataTransformer;

    private DivorceSession testDivorceSession;
    private CoreCaseData testCoreCaseData;

    @Before
    public void setUp() {
        testDivorceSession = new DivorceSession();
        testCoreCaseData = new CoreCaseData();
    }

    @Test
    public void shouldCallAdequateMapperForTransformingDivorceCaseDataIntoCoreCaseData() {
        when(divorceCaseToCCDMapper.divorceCaseDataToCourtCaseData(testDivorceSession)).thenReturn(testCoreCaseData);

        CoreCaseData returnedCoreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(testDivorceSession);

        assertThat(returnedCoreCaseData, is(testCoreCaseData));
        verify(divorceCaseToCCDMapper).divorceCaseDataToCourtCaseData(testDivorceSession);
    }

    @Test
    public void shouldCallAdequateMapperForTransformingCoreCaseDataIntoDivorceCaseData() {
        when(ccdCaseToDivorceMapper.coreCaseDataToDivorceCaseData(testCoreCaseData)).thenReturn(testDivorceSession);

        DivorceSession returnedDivorceCaseData = dataTransformer.transformCoreCaseDataToDivorceCaseData(testCoreCaseData);

        assertThat(returnedDivorceCaseData, is(testDivorceSession));
        verify(ccdCaseToDivorceMapper).coreCaseDataToDivorceCaseData(testCoreCaseData);
    }

}