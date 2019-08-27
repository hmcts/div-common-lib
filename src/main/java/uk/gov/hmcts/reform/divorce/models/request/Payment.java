package uk.gov.hmcts.reform.divorce.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

//@ApiModel(value = "Payment details.")
@Data
public class Payment {

    //@ApiModelProperty("Payment channel.")
    @JsonProperty("PaymentChannel")
    private String paymentChannel;

    //@ApiModelProperty("Payment transaction ID.")
    @JsonProperty("PaymentTransactionId")
    private String paymentTransactionId;

    //@ApiModelProperty("Payment reference.")
    @JsonProperty("PaymentReference")
    private String paymentReference;

    //@ApiModelProperty("Payment date.")
    @JsonProperty("PaymentDate")
    private String paymentDate;

    //@ApiModelProperty("Payment amount.")
    @JsonProperty("PaymentAmount")
    private String paymentAmount;

    //@ApiModelProperty("Payment status.")
    @JsonProperty("PaymentStatus")
    private String paymentStatus;

    //@ApiModelProperty("ID of payment fee.")
    @JsonProperty("PaymentFeeId")
    private String paymentFeeId;

    //@ApiModelProperty("ID of site the payment was made.")
    @JsonProperty("PaymentSiteId")
    private String paymentSiteId;
}
