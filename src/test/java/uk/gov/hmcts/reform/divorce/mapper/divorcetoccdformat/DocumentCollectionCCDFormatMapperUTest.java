package uk.gov.hmcts.reform.divorce.mapper.divorcetoccdformat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.DocumentCollectionCCDFormatMapper;
import uk.gov.hmcts.reform.divorce.mapper.DocumentUrlRewriter;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    MappingConfig.class
})
public class DocumentCollectionCCDFormatMapperUTest {

    private static final String FILE_URL = "http://em-api-gateway-web:3404/documents/3627acc4-cb3b-4c95-9588-fea94e6c5855";
    private static final String FILE_NAME = "test-file";

    @MockBean
    private DocumentUrlRewriter documentUrlRewriter;

    @Autowired
    private DocumentCollectionCCDFormatMapper classUnderTest;

    @Before
    public void setUp() {
        when(documentUrlRewriter.getDocumentUrl(FILE_URL)).thenReturn(FILE_URL);
    }

    @Test
    public void shouldMapUploadedFileToCollectionMember() {
        final Date createdOn = java.sql.Date.valueOf(LocalDate.of(2017, 11, 28));
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        final UploadedFile uploadedFile = UploadedFile.builder()
            .fileName(FILE_NAME)
            .createdOn(createdOn)
            .fileUrl(FILE_URL)
            .build();

        final CollectionMember<Document> collectionMember = classUnderTest.map(uploadedFile);

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
        assertNull(classUnderTest.map(null));
    }

    @Test
    public void shouldIgnoreMissingCreatedOnField() {
        UploadedFile uploadedFile = UploadedFile.builder()
            .fileName(FILE_NAME)
            .fileUrl(FILE_URL)
            .build();

        CollectionMember<Document> collectionMember = classUnderTest.map(uploadedFile);

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
        UploadedFile uploadedFile = UploadedFile.builder()
            .fileName(FILE_NAME)
            .fileUrl(FILE_URL)
            .build();

        classUnderTest.map(uploadedFile);

        verify(documentUrlRewriter).getDocumentUrl(FILE_URL);
    }
}
