package uk.gov.hmcts.reform.divorce.model.parties;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum DivorceParty {

    PETITIONER("petitioner"),
    RESPONDENT("respondent"),
    CO_RESPONDENT("co-respondent");

    private final String description;

    DivorceParty(String description) {
        this.description = description;
    }

    public static DivorceParty getDivorcePartyByDescription(String description) throws DivorcePartyNotFoundException {
        return Arrays.stream(DivorceParty.values())
            .filter(d -> d.description.equals(description))
            .findFirst()
            .orElseThrow(() -> new DivorcePartyNotFoundException(description));
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

}