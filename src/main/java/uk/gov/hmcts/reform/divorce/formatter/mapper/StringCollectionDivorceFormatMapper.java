package uk.gov.hmcts.reform.divorce.formatter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.model.ccd.CollectionMember;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class StringCollectionDivorceFormatMapper {

    public List<String> map(List<CollectionMember> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
            .map(value -> (String) value.getValue())
            .collect(Collectors.toList());
    }
}
