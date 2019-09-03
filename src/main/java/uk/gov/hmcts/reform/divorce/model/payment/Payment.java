package uk.gov.hmcts.reform.divorce.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "Payment details.")
@Data
@Builder(builderClassName = "PaymentBuilder", toBuilder = true)
@JsonDeserialize(builder = Payment.PaymentBuilder.class)
@JsonIgnoreProperties
public class Payment {

    @ApiModelProperty("Payment channel.")
    @JsonProperty("PaymentChannel")
    private String paymentChannel;

    @ApiModelProperty("Payment transaction ID.")
    @JsonProperty("PaymentTransactionId")
    private String paymentTransactionId;

    @ApiModelProperty("Payment reference.")
    @JsonProperty("PaymentReference")
    private String paymentReference;

    @ApiModelProperty("Payment date.")
    @JsonProperty("PaymentDate")
    private String paymentDate;

    @ApiModelProperty("Payment amount.")
    @JsonProperty("PaymentAmount")
    private String paymentAmount;

    @ApiModelProperty("Payment status.")
    @JsonProperty("PaymentStatus")
    private String paymentStatus;

    @ApiModelProperty("ID of payment fee.")
    @JsonProperty("PaymentFeeId")
    private String paymentFeeId;

    @ApiModelProperty("ID of site the payment was made.")
    @JsonProperty("PaymentSiteId")
    private String paymentSiteId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PaymentBuilder {

    }
}
