package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "The response from retrieving a fee from fees and payments service")
public class FeeResponse {
    @ApiModelProperty(value = "The fee identifier")
    private String feeCode;
    @ApiModelProperty(value = "The fee amount in pounds")
    private Double amount;
    @ApiModelProperty(value = "The fee version")
    private Integer version;
    @ApiModelProperty(value = "The fee description")
    private String description;
}
