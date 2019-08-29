package uk.gov.hmcts.reform.divorce.validation.service;

public enum ValidationStatus {
    FAILED("failed"),
    SUCCESS("success");

    private final String value;

    ValidationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

