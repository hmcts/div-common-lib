package uk.gov.hmcts.reform.divorce.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class Payment {

    @JsonProperty("PaymentChannel")
    private String paymentChannel;

    @JsonProperty("PaymentTransactionId")
    private String paymentTransactionId;

    @JsonProperty("PaymentReference")
    private String paymentReference;

    @JsonProperty("PaymentDate")
    private String paymentDate;

    @JsonProperty("PaymentAmount")
    private String paymentAmount;

    @JsonProperty("PaymentStatus")
    private String paymentStatus;

    @JsonProperty("PaymentFeeId")
    private String paymentFeeId;

    @JsonProperty("PaymentSiteId")
    private String paymentSiteId;
}
