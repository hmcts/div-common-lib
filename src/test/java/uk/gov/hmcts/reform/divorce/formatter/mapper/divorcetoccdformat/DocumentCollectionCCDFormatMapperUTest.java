package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DocumentCollectionCCDFormatMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DocumentUrlRewrite;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class DocumentCollectionCCDFormatMapperUTest {
    private static final String FILE_URL =
        "http://em-api-gateway-web:3404/documents/3627acc4-cb3b-4c95-9588-fea94e6c5855";
    private static final String FILE_NAME = "test-file";

    @Autowired
    private DocumentCollectionCCDFormatMapper mapper;

    @MockBean
    private DocumentUrlRewrite documentUrlRewrite;

    @Before
    public void setUp() {
        given(documentUrlRewrite.getDocumentUrl(FILE_URL)).willReturn(FILE_URL);
    }

    @Test
    public void shouldMapUploadedFileToCollectionMember() {
        final Date createdOn = java.sql.Date.valueOf(LocalDate.of(2017, 11, 28));
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        final UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(FILE_NAME);
        uploadedFile.setFileUrl(FILE_URL);
        uploadedFile.setCreatedOn(createdOn);

        final CollectionMember<Document> collectionMember = mapper.map(uploadedFile);

        final Document document = collectionMember.getValue();

        final DocumentLink expectedDocumentLink = new DocumentLink();
        expectedDocumentLink.setDocumentUrl(FILE_URL);

        assertNull(collectionMember.getId());
        assertEquals(FILE_NAME, document.getDocumentFileName());
        assertEquals(dateFormat.format(createdOn), document.getDocumentDateAdded());
        assertEquals("", document.getDocumentComment());
        assertEquals("", document.getDocumentEmailContent());
        assertEquals("other", document.getDocumentType());
        assertEquals(expectedDocumentLink, document.getDocumentLink());
    }

    @Test
    public void shouldReturnNullWithNullInput() {
        assertNull(mapper.map(null));
    }

    @Test
    public void shouldIgnoreMissingCreatedOnField() {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(FILE_NAME);
        uploadedFile.setFileUrl(FILE_URL);

        CollectionMember<Document> collectionMember = mapper.map(uploadedFile);

        Document document = collectionMember.getValue();

        final DocumentLink expectedDocumentLink = new DocumentLink();
        expectedDocumentLink.setDocumentUrl(FILE_URL);

        assertEquals(FILE_NAME, document.getDocumentFileName());
        assertEquals("", document.getDocumentComment());
        assertEquals("", document.getDocumentEmailContent());
        assertEquals("other", document.getDocumentType());
        assertEquals(expectedDocumentLink, document.getDocumentLink());
    }

    @Test
    public void shouldCallTheDocumentUrlRewriteToUpdateTheUrl() {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(FILE_NAME);
        uploadedFile.setFileUrl(FILE_URL);

        CollectionMember<Document> collectionMember = mapper.map(uploadedFile);

        collectionMember.getValue();

        verify(documentUrlRewrite).getDocumentUrl(FILE_URL);
    }
}
