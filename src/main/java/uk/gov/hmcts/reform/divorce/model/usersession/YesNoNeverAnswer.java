package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum YesNoNeverAnswer {

    YES("Yes"),
    NO("No"),
    NEVER("Never");

    private final String answer;

    @JsonCreator
    public static YesNoNeverAnswer fromInput(String input) {
        if (input.equalsIgnoreCase(YES.getAnswer())) {
            return YES;
        } else if (input.equalsIgnoreCase(NO.getAnswer())) {
            return NO;
        } else if (input.equalsIgnoreCase(NEVER.getAnswer())) {
            return NEVER;
        }
        throw new IllegalArgumentException(
            String.format("Could not find match for input '%s' in %s",
                input,
                Arrays.asList(YesNoNeverAnswer.values())));
    }

}