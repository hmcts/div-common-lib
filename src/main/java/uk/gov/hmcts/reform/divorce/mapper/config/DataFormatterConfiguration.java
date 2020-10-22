package uk.gov.hmcts.reform.divorce.mapper.config;

/**
 * The implementation for this is supposed to be supplied by whoever wants to use the mapper functionality.
 * Usually, these will come from the service's environment variables.
 */
public interface DataFormatterConfiguration {

    String getDocumentManagementStoreUrl();

    String getCohort();

}