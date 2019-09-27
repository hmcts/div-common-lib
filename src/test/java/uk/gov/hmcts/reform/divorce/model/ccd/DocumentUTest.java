package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class DocumentUTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Document document;
    private String json;
    private String jsonWithNullFieldsIgnored;

    @Before
    public void setUp() throws Exception {
        json = ObjectMapperTestUtil.retrieveFileContents("fixtures/model/ccd/Document.json");

        jsonWithNullFieldsIgnored =
            ObjectMapperTestUtil.retrieveFileContents("fixtures/model/ccd/DocumentNullFieldsIgnored.json");

        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl("http://localhost/document");

        document = new Document();
        document.setDocumentType("marriageCert");
        document.setDocumentFileName("test-file-name");
        document.setDocumentLink(documentLink);
        document.setDocumentEmailContent("test-email-content");
        document.setDocumentComment("test-comment");
        document.setDocumentDateAdded("2017-01-01");
    }

    @Test
    public void shouldMarshalJsonStringToObject() throws Exception {
        ObjectReader objectReader = objectMapper.readerFor(Document.class);

        assertEquals(document, objectReader.readValue(json));
    }

    @Test
    public void shouldUnmarshalObjectToJsonString() throws Exception {
        ObjectWriter objectWriter = objectMapper
            .writer(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH))
            .withDefaultPrettyPrinter();

        assertEquals(jsonWithNullFieldsIgnored.trim(), objectWriter.writeValueAsString(document));
    }
}
