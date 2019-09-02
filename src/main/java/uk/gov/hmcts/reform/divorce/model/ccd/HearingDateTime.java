package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HearingDateTime {

    @JsonProperty("DateOfHearing")
    private String dateOfHearing;

    @JsonProperty("TimeOfHearing")
    private String timeOfHearing;
}
