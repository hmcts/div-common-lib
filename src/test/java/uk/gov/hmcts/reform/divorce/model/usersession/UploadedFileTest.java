package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.utils.ObjectMapperTestUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class UploadedFileTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Date createdOn = java.sql.Date.valueOf(LocalDate.of(2017, 11, 28));
    private final Date modifiedOn = java.sql.Date.valueOf(LocalDate.of(2017, 11, 29));

    private final UploadedFile uploadedFile = UploadedFile.builder()
        .fileName("marriage-certificate.pdf")
        .id("DocumentId")
        .fileUrl("http://em-api-gateway-web:3404/documents/3627acc4-cb3b-4c95-9588-fea94e6c5855")
        .createdBy(8)
        .lastModifiedBy(8)
        .createdOn(createdOn)
        .modifiedOn(modifiedOn)
        .mimeType("application/pdf")
        .status("OK")
        .build();

    private String json;

    @Before
    public void setUp() throws Exception {
        json = ObjectMapperTestUtil.retrieveFileContents("fixtures/model/divorce/UploadedFile.json");
    }

    @Test
    public void shouldMarshalJsonStringToObject() throws Exception {
        final ObjectReader objectReader = objectMapper.readerFor(UploadedFile.class);

        assertEquals(uploadedFile, objectReader.readValue(json));
    }

    @Test
    public void shouldUnMarshalObjectToJsonString() throws Exception {
        final ObjectWriter objectWriter = objectMapper
            .writer(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH))
            .withDefaultPrettyPrinter();

        assertEquals(json.trim(), objectWriter.writeValueAsString(uploadedFile));
    }
}
