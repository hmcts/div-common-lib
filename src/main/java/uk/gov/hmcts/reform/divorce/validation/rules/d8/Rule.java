package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;

import java.util.List;

public abstract class Rule {

    public abstract List<String> execute(CoreCaseData coreCaseData, List<String> result);
}
