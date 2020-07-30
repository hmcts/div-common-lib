package uk.gov.hmcts.reform.divorce.formatter.mapper.ccdtodivorceformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DocumentCollectionDivorceFormatMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class DocumentCollectionDivorceFormatMapperUTest {
    private static final String FILE_URL =
        "http://em-api-gateway-web:3404/documents/3627acc4-cb3b-4c95-9588-fea94e6c5855";
    private static final String FILE_NAME = "test-file";//TODO - we probably want to get rid of this

    @Autowired
    private DocumentCollectionDivorceFormatMapper mapper;

    @Test
    public void shouldMapUploadedFileToCollectionMember() {
        final String dateDocumentAdded = "2012-11-11";

        final Document document = new Document();
        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl(FILE_URL);
        documentLink.setDocumentFilename(FILE_NAME);
        document.setDocumentLink(documentLink);
        document.setDocumentFileName(FILE_NAME);
        document.setDocumentDateAdded(dateDocumentAdded);
        document.setDocumentDateAdded("");

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        final UploadedFile uploadedFile = mapper.map(collectionMember);

        assertNull(uploadedFile.getStatus());
        assertNull(uploadedFile.getMimeType());
        assertNull(uploadedFile.getModifiedOn());
        assertNull(uploadedFile.getFileType());
        assertEquals(0, uploadedFile.getCreatedBy());
        assertEquals(0, uploadedFile.getLastModifiedBy());
        assertEquals(FILE_NAME, uploadedFile.getFileName());
        assertEquals(FILE_URL, uploadedFile.getFileUrl());
        assertNull(uploadedFile.getCreatedOn());
    }

    @Test
    public void shouldMapDateCorrectly() throws ParseException {
        final String dateDocumentAdded = "2012-11-11";
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        final Document document = new Document();
        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl(FILE_URL);
        documentLink.setDocumentFilename(FILE_NAME);
        document.setDocumentLink(documentLink);
        document.setDocumentFileName(FILE_NAME);
        document.setDocumentDateAdded(dateDocumentAdded);

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        final UploadedFile uploadedFile = mapper.map(collectionMember);

        assertEquals(dateFormat.parse(dateDocumentAdded), uploadedFile.getCreatedOn());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnBadDateFormat() {
        final String dateDocumentAdded = "wrong-format-1234";

        final Document document = new Document();
        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl(FILE_URL);
        documentLink.setDocumentFilename(FILE_NAME);
        document.setDocumentLink(documentLink);
        document.setDocumentFileName(FILE_NAME);
        document.setDocumentDateAdded(dateDocumentAdded);

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        mapper.map(collectionMember);
    }

    @Test
    public void shouldReturnNullWithNullInput() {
        assertNull(mapper.map(null));
    }

    @Test
    public void shouldNotThrowExceptionForNullDocumentLink() {
        final Document document = new Document();

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        assertThatCode(() -> mapper.map(collectionMember))
            .doesNotThrowAnyException();
    }

    @Test
    public void shouldNotThrowExceptionForNullDocumentLinkUrl() {
        final Document document = new Document();
        document.setDocumentLink(new DocumentLink());

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        assertThatCode(() -> mapper.map(collectionMember))
            .doesNotThrowAnyException();
    }
}
