package uk.gov.hmcts.reform.divorce.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "PaymentCollectionBuilder", toBuilder = true)
@JsonDeserialize(builder = PaymentCollection.PaymentCollectionBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCollection {
    private String id;
    private Payment value;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PaymentBuilder {

    }
}
