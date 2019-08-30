package uk.gov.hmcts.reform.divorce.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Event {

    @JsonProperty("id")
    private String eventId;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

}
