package uk.gov.hmcts.reform.divorce.model.usersession;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressType {
    POST_CODE("postcode"),
    MANUAL("manual");

    private String type;
}
