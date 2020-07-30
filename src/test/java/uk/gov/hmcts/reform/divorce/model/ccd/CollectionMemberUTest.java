package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CollectionMemberUTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private CollectionMember<Document> collectionMember;
    private String json;
    private String jsonNullDocumentFieldsRemoved;

    @Before
    public void setUp() throws Exception {
        json = ObjectMapperTestUtil.retrieveFileContents("fixtures/model/ccd/CollectionMember.json");

        jsonNullDocumentFieldsRemoved =
            ObjectMapperTestUtil.retrieveFileContents("fixtures/model/ccd/CollectionMemberNoNullFields.json");

        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl("http://localhost/document");

        Document document = new Document();
        document.setDocumentType("marriageCert");
        document.setDocumentFileName("test-file-name");
        document.setDocumentLink(documentLink);
        document.setDocumentEmailContent("test-email-content");
        document.setDocumentComment("test-comment");
        document.setDocumentDateAdded("2017-01-01");

        collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);
    }

    @Test
    public void shouldMarshalJsonStringToObject() throws Exception {
        ObjectReader objectReader = objectMapper.readerFor(new TypeReference<CollectionMember<Document>>() {
        });

        assertEquals(collectionMember, objectReader.readValue(json));
    }

    @Test
    public void shouldUnmarshalObjectToJsonString() throws Exception {
        ObjectWriter objectWriter = objectMapper
            .writer(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH))
            .withDefaultPrettyPrinter();

        assertEquals(jsonNullDocumentFieldsRemoved.trim(), objectWriter.writeValueAsString(collectionMember));
    }
}
