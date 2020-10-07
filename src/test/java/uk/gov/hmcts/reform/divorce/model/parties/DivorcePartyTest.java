package uk.gov.hmcts.reform.divorce.model.parties;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivorcePartyTest {

    @Test
    public void shouldGetRightEnumByString() throws DivorcePartyNotFoundException {
        assertThat(DivorceParty.getDivorcePartyByDescription("respondent"), equalTo(DivorceParty.RESPONDENT));
        assertThat(DivorceParty.getDivorcePartyByDescription("co-respondent"), equalTo(DivorceParty.CO_RESPONDENT));
    }

    @Test
    public void shouldThrowExceptionWhenDescriptionIsInvalid() throws DivorcePartyNotFoundException {
        DivorcePartyNotFoundException divorcePartyNotFoundException = assertThrows(
            DivorcePartyNotFoundException.class,
            () -> DivorceParty.getDivorcePartyByDescription("invalid description")
        );

        assertThat(
            divorcePartyNotFoundException.getMessage(),
            is("Could not find divorce party with the given description: invalid description")
        );
    }
}