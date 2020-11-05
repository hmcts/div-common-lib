package uk.gov.hmcts.reform.divorce.mapper;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uk.gov.hmcts.reform.divorce.model.ccd.DaCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Locale;
import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DivorceCaseToDaCaseMapper {

    public abstract DaCaseData divorceCaseDataToDaCaseData(DivorceSession divorceSession);

    @AfterMapping
    protected void mapApplyForDecreeAbsolute(DivorceSession divorceSession, @MappingTarget DaCaseData result) {
        result.setApplyForDecreeAbsolute(translateToStringYesNo(divorceSession.getApplyForDecreeAbsolute()));
    }

    private String translateToStringYesNo(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }
}
