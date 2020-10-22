package uk.gov.hmcts.reform.divorce.mapper.config;

public class DataFormatterTestConfiguration implements DataFormatterConfiguration {

    @Override
    public String getDocumentManagementStoreUrl() {
        return "http://localhost:5006";
    }

    @Override
    public String getCohort() {
        return "onlineSubmissionPrivateBeta";
    }

}