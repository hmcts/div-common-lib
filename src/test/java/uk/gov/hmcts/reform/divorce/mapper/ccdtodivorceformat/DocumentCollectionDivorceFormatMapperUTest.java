package uk.gov.hmcts.reform.divorce.mapper.ccdtodivorceformat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.divorce.config.MappingConfig;
import uk.gov.hmcts.reform.divorce.mapper.DocumentCollectionDivorceFormatMapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.DivorceGeneralOrder;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static uk.gov.hmcts.reform.divorce.utils.DocumentsTestHelper.createDocumentCollectionMember;
import static uk.gov.hmcts.reform.divorce.utils.DocumentsTestHelper.createGeneralOrderCollectionMember;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MappingConfig.class})
public class DocumentCollectionDivorceFormatMapperUTest {

    private static final String FILE_URL = "http://em-api-gateway-web:3404/documents/3627acc4-cb3b-4c95-9588-fea94e6c5855";
    private static final String FILE_NAME = "test-file";

    @Autowired
    private DocumentCollectionDivorceFormatMapper mapper;

    private String dateDocumentAdded;
    private Date dateDocumentAddedParsed;

    @Before
    public void setUp() throws ParseException {
        dateDocumentAdded = "2012-11-11";
        dateDocumentAddedParsed = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateDocumentAdded);
    }

    @Test
    public void shouldMapUploadedFileToCollectionMember() {
        final CollectionMember<Document> collectionMember = createDocumentCollectionMember(FILE_URL, FILE_NAME, "");

        final UploadedFile uploadedFile = mapper.map(collectionMember);

        assertThat(uploadedFile, is(notNullValue()));
        assertThat(uploadedFile.getStatus(), is(nullValue()));
        assertThat(uploadedFile.getMimeType(), is(nullValue()));
        assertThat(uploadedFile.getModifiedOn(), is(nullValue()));
        assertThat(uploadedFile.getFileType(), is(nullValue()));
        assertThat(uploadedFile.getCreatedBy(), is(0));
        assertThat(uploadedFile.getLastModifiedBy(), is(0));
        assertThat(uploadedFile.getFileName(), is(FILE_NAME));
        assertThat(uploadedFile.getFileUrl(), is(FILE_URL));
        assertThat(uploadedFile.getCreatedOn(), is(nullValue()));
    }

    @Test
    public void shouldMapDateCorrectly() {
        final CollectionMember<Document> collectionMember = createDocumentCollectionMember(FILE_URL, FILE_NAME, dateDocumentAdded);

        final UploadedFile uploadedFile = mapper.map(collectionMember);

        assertThat(uploadedFile.getCreatedOn(), is(dateDocumentAddedParsed));
    }

    @Test
    public void shouldConvertDivorceGeneralOrderToUploadFile() {
        CollectionMember<DivorceGeneralOrder> generalOrder = createGeneralOrderCollectionMember(FILE_URL, FILE_NAME, dateDocumentAdded);

        UploadedFile uploadedFile = mapper.mapGeneralOrder(generalOrder);

        assertThat(uploadedFile, is(notNullValue()));
        assertThat(uploadedFile.getStatus(), is(nullValue()));
        assertThat(uploadedFile.getMimeType(), is(nullValue()));
        assertThat(uploadedFile.getModifiedOn(), is(nullValue()));
        assertThat(uploadedFile.getFileType(), is(nullValue()));
        assertThat(uploadedFile.getCreatedBy(), is(0));
        assertThat(uploadedFile.getLastModifiedBy(), is(0));
        assertThat(uploadedFile.getFileName(), is(FILE_NAME));
        assertThat(uploadedFile.getFileUrl(), is(FILE_URL));
        assertThat(uploadedFile.getCreatedOn(), is(dateDocumentAddedParsed));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnBadDateFormat() {
        final String dateDocumentAdded = "wrong-format-1234";

        final CollectionMember<Document> collectionMember = createDocumentCollectionMember(FILE_URL, FILE_NAME, dateDocumentAdded);

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

        assertThatCode(() -> mapper.map(collectionMember)).doesNotThrowAnyException();
    }

    @Test
    public void shouldNotThrowExceptionForNullDocumentLinkUrl() {
        final Document document = new Document();
        document.setDocumentLink(new DocumentLink());

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);

        assertThatCode(() -> mapper.map(collectionMember)).doesNotThrowAnyException();
    }

}
