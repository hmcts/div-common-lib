package uk.gov.hmcts.reform.divorce.utils;

import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.DivorceGeneralOrder;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;

public class DocumentsTestHelper {

    public static CollectionMember<Document> createDocumentCollectionMember(String fileUrl, String fileName, String documentDateAdded) {
        final Document document = createDocument(fileUrl, fileName, documentDateAdded);

        final CollectionMember<Document> collectionMember = new CollectionMember<>();
        collectionMember.setValue(document);
        return collectionMember;
    }

    public static CollectionMember<DivorceGeneralOrder> createGeneralOrderCollectionMember(String fileUrl,
                                                                                           String fileName,
                                                                                           String documentDateAdded) {
        final Document document = createDocument(fileUrl, fileName, documentDateAdded);

        final CollectionMember<DivorceGeneralOrder> collectionMember = new CollectionMember<>();
        collectionMember.setValue(DivorceGeneralOrder.builder().document(document).build());
        return collectionMember;
    }

    private static Document createDocument(String fileUrl, String fileName, String documentDateAdded) {
        final Document document = new Document();
        final DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl(fileUrl);
        documentLink.setDocumentFilename(fileName);
        document.setDocumentLink(documentLink);
        document.setDocumentFileName(fileName);
        document.setDocumentDateAdded(documentDateAdded);
        return document;
    }

}
