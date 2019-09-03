package uk.gov.hmcts.reform.divorce.formatter.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;
import uk.gov.hmcts.reform.divorce.model.ccd.Document;
import uk.gov.hmcts.reform.divorce.model.ccd.DocumentLink;
import uk.gov.hmcts.reform.divorce.model.usersession.UploadedFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static uk.gov.hmcts.reform.divorce.formatter.mapper.MappingCommons.SIMPLE_DATE_FORMAT;


@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@SuppressWarnings("squid:S1610")
public abstract class DocumentCollectionDivorceFormatMapper {

    @Autowired
    private DocumentUrlRewrite documentUrlRewrite;

    @Mapping(source = "value.documentLink.documentFilename", target = "fileName")
    @Mapping(source = "value.documentLink.documentUrl", target = "fileUrl")
    public abstract UploadedFile map(CollectionMember<Document> documentCollectionMember);


    @AfterMapping
    protected void mapDocumentIDToDocumentObject(CollectionMember<Document> document,
                                                 @MappingTarget UploadedFile result) {

        Optional.of(document)
            .map(documentEntry -> document.getValue())
            .map(Document::getDocumentLink)
            .map(DocumentLink::getDocumentUrl)
            .ifPresent(url -> documentUrlRewrite.getDocumentId(url).ifPresent(result::setId));
    }

    @AfterMapping
    protected void mapCreatedDateCheckEmpty(CollectionMember<Document> document,
                                            @MappingTarget UploadedFile result) {
        try {
            if (document.getValue().getDocumentDateAdded() != null
                && !document.getValue().getDocumentDateAdded().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = new Date(dateFormat.parse(document.getValue().getDocumentDateAdded()).getTime());
                result.setCreatedOn(date);
            }
        } catch (ParseException ex) {
            log.warn("Unable to parse a date string on document");
            throw new RuntimeException("Parse date on documents error", ex);
        }
    }
}
