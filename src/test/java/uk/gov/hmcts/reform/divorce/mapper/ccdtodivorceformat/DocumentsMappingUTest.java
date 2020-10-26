package uk.gov.hmcts.reform.divorce.mapper.ccdtodivorceformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.CCDCaseToDivorceMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class DocumentsMappingUTest {

    @Autowired
    private CCDCaseToDivorceMapper mapper;

    @Test
    public void shouldHandleDocumentLinkBeingNull()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/document-link-null.json", CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        List<UploadedFile> marriageCertificateFiles = actualDivorceSession.getMarriageCertificateFiles();
        assertEquals(marriageCertificateFiles.size(), 1);
        assertEquals(marriageCertificateFiles.get(0).getFileName(), "some-file.pdf");
    }

    @Test
    public void shouldHandleDocumentDateBeingEmptyString()
        throws URISyntaxException, IOException {

        CoreCaseData coreCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/ccdtodivorcemapping/ccd/document-dates-empty.json",
                CoreCaseData.class);

        DivorceSession actualDivorceSession = mapper.coreCaseDataToDivorceCaseData(coreCaseData);

        List<UploadedFile> marriageCertificateFiles = actualDivorceSession.getMarriageCertificateFiles();

        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String date = dateFormat.format(marriageCertificateFiles.get(0).getCreatedOn());

        assertEquals(marriageCertificateFiles.size(), 3);
        assertEquals(marriageCertificateFiles.get(0).getFileName(), "some-file.pdf");
        assertEquals(date, "2019-05-10");
    }
}
