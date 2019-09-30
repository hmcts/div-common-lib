package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import uk.gov.hmcts.reform.divorce.config.BeanConfig;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DivorceCaseToDnClarificationMapper;
import uk.gov.hmcts.reform.divorce.formatter.mapper.ObjectMapperTestUtil;
import uk.gov.hmcts.reform.divorce.model.DivorceCaseWrapper;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.DnCaseData;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ContextConfiguration(classes = BeanConfig.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class DivorceCaseToDnClarificationMapperUTest {

    @Autowired
    private DivorceCaseToDnClarificationMapper mapper;

    @Test
    public void shouldMapDivorceSessionFieldsToDnCaseData() throws Exception {
        CoreCaseData coreCaseData = new CoreCaseData();

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-clarification.json", DnCaseData.class);

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-clarification.json",
                DivorceSession.class);

        DivorceCaseWrapper divorceCaseWrapper = new DivorceCaseWrapper(coreCaseData, divorceSession);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceCaseWrapper);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }

    @Test
    public void shouldMapDivorceSessionFieldsToDnCaseDataWithExistingData() throws Exception {
        Document existingDocument = new Document();
        existingDocument.setDocumentType("other");
        existingDocument.setDocumentDateAdded("2011-11-11");
        existingDocument.setDocumentComment("");
        existingDocument.setDocumentFileName("favicon.ico");
        existingDocument.setDocumentEmailContent("");
        DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl("http://localhost:5006/documents/1234567-abcd-1234-wxyz-098765432101");
        existingDocument.setDocumentLink(documentLink);

        CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(existingDocument);

        List<CollectionMember<Document>> existingDocuments = new ArrayList<>();
        existingDocuments.add(collectionMember);

        CoreCaseData coreCaseData = new CoreCaseData();
        coreCaseData.setDnClarificationResponse(new ArrayList<>(Arrays.asList("This is the initial response")));
        coreCaseData.setDocumentsUploadedDnClarification(existingDocuments);

        DnCaseData expectedDnCaseData = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/ccd/dn-clarification-existing-data.json",
                DnCaseData.class);

        DivorceSession divorceSession = ObjectMapperTestUtil
            .retrieveFileContentsAsObject("fixtures/divorcetoccdmapping/divorce/dn-clarification-existing-data.json",
                DivorceSession.class);

        DivorceCaseWrapper divorceCaseWrapper = new DivorceCaseWrapper(coreCaseData, divorceSession);

        DnCaseData actualDnCaseData = mapper.divorceCaseDataToDnCaseData(divorceCaseWrapper);

        assertThat(actualDnCaseData, samePropertyValuesAs(expectedDnCaseData));
    }
}