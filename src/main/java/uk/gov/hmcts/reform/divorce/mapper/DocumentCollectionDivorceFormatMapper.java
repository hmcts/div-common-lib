package uk.gov.hmcts.reform.divorce.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.DivorceGeneralOrder;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings("squid:S1610")
public abstract class DocumentCollectionDivorceFormatMapper {

    @Autowired
    private DocumentUrlRewriter documentUrlRewriter;

    @Mapping(source = "value.documentLink.documentFilename", target = "fileName")
    @Mapping(source = "value.documentLink.documentUrl", target = "fileUrl")
    public abstract UploadedFile map(CollectionMember<Document> documentCollectionMember);

    @Mapping(source = "value.document.documentLink.documentFilename", target = "fileName")
    @Mapping(source = "value.document.documentLink.documentUrl", target = "fileUrl")
    public abstract UploadedFile mapGeneralOrder(CollectionMember<DivorceGeneralOrder> documentCollectionMember);

    @AfterMapping
    protected void mapCollectionMemberDocumentToUploadedFile(CollectionMember<Document> document,
                                                             @MappingTarget UploadedFile result) {
        mapDocumentToUploadedFile(result, document.getValue());
    }

    @AfterMapping
    protected void mapGeneralOrderToUploadedFile(CollectionMember<DivorceGeneralOrder> generalOrder,
                                                 @MappingTarget UploadedFile result) {
        Document document = generalOrder.getValue().getDocument();
        mapDocumentToUploadedFile(result, document);
    }

    private void mapDocumentToUploadedFile(@MappingTarget UploadedFile result, Document value) {
        mapDocumentIDToDocumentObject(result, value);
        mapCreatedDateCheckEmpty(result, value);
    }

    private void mapDocumentIDToDocumentObject(UploadedFile result, Document document) {
        Optional.ofNullable(document)
            .map(Document::getDocumentLink)
            .map(DocumentLink::getDocumentUrl)
            .flatMap(url -> documentUrlRewriter.getDocumentId(url))
            .ifPresent(result::setId);
    }

    private void mapCreatedDateCheckEmpty(@MappingTarget UploadedFile result, Document document) {
        try {
            if (document.getDocumentDateAdded() != null && !document.getDocumentDateAdded().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                Date date = new Date(dateFormat.parse(document.getDocumentDateAdded()).getTime());
                result.setCreatedOn(date);
            }
        } catch (ParseException ex) {
            log.warn("Unable to parse a date string on document");
            throw new RuntimeException("Parse date on documents error", ex);
        }
    }

}
