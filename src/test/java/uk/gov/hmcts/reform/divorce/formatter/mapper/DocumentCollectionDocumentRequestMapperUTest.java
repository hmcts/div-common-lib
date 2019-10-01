package uk.gov.hmcts.reform.divorce.formatter.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.documentupdate.GeneratedDocumentInfo;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class DocumentCollectionDocumentRequestMapperUTest {
    private static final String HAL_BINARY_RESPONSE_CONTEXT_PATH =
        (String) ReflectionTestUtils.getField(
            DocumentCollectionDocumentRequestMapper.class, "HAL_BINARY_RESPONSE_CONTEXT_PATH");
    private static final String PDF_FILE_EXTENSION =
        (String) ReflectionTestUtils.getField(
            DocumentCollectionDocumentRequestMapper.class, "PDF_FILE_EXTENSION");

    @Autowired
    private DocumentCollectionDocumentRequestMapper mapper;

    @Test
    public void shouldMapAllRequiredFields() {
        final String url = "url";
        final String documentType = "petition";
        final String fileName = "fileName";

        final GeneratedDocumentInfo generatedDocumentInfo = GeneratedDocumentInfo.builder()
            .url(url)
            .documentType(documentType)
            .fileName(fileName)
            .build();

        CollectionMember<Document> actual = mapper.map(generatedDocumentInfo);

        assertEquals(url, actual.getValue().getDocumentLink().getDocumentUrl());
        assertEquals(url + HAL_BINARY_RESPONSE_CONTEXT_PATH,
            actual.getValue().getDocumentLink().getDocumentBinaryUrl());
        assertEquals(fileName + PDF_FILE_EXTENSION, actual.getValue().getDocumentLink().getDocumentFilename());
        assertEquals(fileName, actual.getValue().getDocumentFileName());
        assertEquals(documentType, actual.getValue().getDocumentType());
    }
}
