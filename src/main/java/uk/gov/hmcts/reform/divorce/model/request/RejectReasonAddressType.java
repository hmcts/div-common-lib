package uk.gov.hmcts.reform.divorce.model.request;

public enum RejectReasonAddressType {

    REJECT_REASON_ADDRESS_COMMERCIAL("Commercial address"),
    REJECT_REASON_ADDRESS_NO_INFO("No information"),
    REJECT_REASON_ADDRESS_INCORRECT_INFO("Incorrect information"),
    REJECT_REASON_ADDRESS_OTHER("Other");

    private String name;

    RejectReasonAddressType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
