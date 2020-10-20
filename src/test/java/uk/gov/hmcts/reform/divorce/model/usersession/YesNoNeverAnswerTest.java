package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YesNoNeverAnswerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fromInput_converts_yes_successfully() {

        // given
        String yes = "yes";

        // when
        YesNoNeverAnswer answer = objectMapper.convertValue(yes, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.YES, answer);
    }

    @Test
    public void fromInput_converts_no_successfully() {

        // given
        String no = "no";

        // when
        YesNoNeverAnswer answer = objectMapper.convertValue(no, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.NO, answer);
    }

    @Test
    public void fromInput_converts_never_successfully() {

        // given
        String never = "never";

        // when
        YesNoNeverAnswer answer = objectMapper.convertValue(never, YesNoNeverAnswer.class);

        // then
        assertEquals(YesNoNeverAnswer.NEVER, answer);
    }

    @Test
    public void fromInput_throws_exception_for_unrecognized_input() {

        // given
        String maybe = "maybe";

        IllegalArgumentException exception = null;

        // when
        try {
            objectMapper.convertValue(maybe, YesNoNeverAnswer.class);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertNotNull(exception);
        String exceptionMessage = exception.getMessage();

        for (YesNoNeverAnswer answer : YesNoNeverAnswer.values()) {
            assertTrue(exceptionMessage.contains(answer.name()));
        }
    }

}