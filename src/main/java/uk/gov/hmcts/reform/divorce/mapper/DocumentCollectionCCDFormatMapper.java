package uk.gov.hmcts.reform.divorce.mapper;

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

import static uk.gov.hmcts.reform.divorce.mapper.MappingCommons.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DocumentCollectionCCDFormatMapper {

    @Autowired
    private DocumentUrlRewriter documentUrlRewriter;

    @Mapping(source = "fileName", target = "value.documentFileName")
    @Mapping(source = "createdOn", dateFormat = SIMPLE_DATE_FORMAT, target = "value.documentDateAdded")
    @Mapping(target = "value.documentEmailContent", constant = "")
    @Mapping(target = "value.documentComment", constant = "")
    @Mapping(source = "fileType", target = "value.documentType", defaultValue = "other")
    public abstract CollectionMember<Document> map(UploadedFile uploadedFile);

    @AfterMapping
    protected void mapDocumentUrlToDocumentLinkObject(UploadedFile uploadedFile,
                                                      @MappingTarget CollectionMember<Document> result) {

        String fileUrl = documentUrlRewriter.getDocumentUrl(uploadedFile.getFileUrl());

        DocumentLink documentLink = new DocumentLink();
        documentLink.setDocumentUrl(fileUrl);
        result.getValue().setDocumentLink(documentLink);
        result.setId(null);
    }

}