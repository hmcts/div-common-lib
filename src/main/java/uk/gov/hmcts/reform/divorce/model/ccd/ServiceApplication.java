package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

import static uk.gov.hmcts.reform.divorce.utils.DateUtils.parseToInstant;

@Data
public class ServiceApplication implements Comparable<ServiceApplication> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("value")
    private ServiceApplicationData value;

    public Instant parseDecisionDateToInstant() {
        return parseToInstant(getValue().getDecisionDate());
    }

    @Override
    public int compareTo(ServiceApplication otherServiceApplication) {
        return parseDecisionDateToInstant().compareTo(otherServiceApplication.parseDecisionDateToInstant());
    }
}
