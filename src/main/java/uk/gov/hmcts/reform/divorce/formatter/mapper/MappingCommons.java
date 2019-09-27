package uk.gov.hmcts.reform.divorce.formatter.mapper;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.apache.commons.lang3.BooleanUtils;
import uk.gov.hmcts.reform.divorce.model.usersession.YesNoNeverAnswer;

import java.util.Locale;
import java.util.Objects;

public final class MappingCommons {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String NEVER = "NEVER";

    private MappingCommons() {
    }

    public static String toYesNoUpperCase(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return toYesNo(value).toUpperCase(Locale.ENGLISH);
    }

    public static String toYesNoPascalCase(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, toYesNo(value));
    }

    public static String toYesNoNeverUpperCase(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value.equalsIgnoreCase(NEVER)) {
            return NEVER;
        }
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value)).toUpperCase(Locale.ENGLISH);
    }

    public static String toYesNoNeverPascalCase(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return YesNoNeverAnswer.fromInput(value).getAnswer();
    }

    private static String toYesNo(String value) {
        return BooleanUtils.toStringYesNo(BooleanUtils.toBoolean(value));
    }
}
