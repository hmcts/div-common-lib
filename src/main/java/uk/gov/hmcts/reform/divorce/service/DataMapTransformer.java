package uk.gov.hmcts.reform.divorce.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.model.ccd.CoreCaseData;
import uk.gov.hmcts.reform.divorce.model.usersession.DivorceSession;

import java.util.Map;

@RequiredArgsConstructor
@Component
/**
 * If you have beans, consider using <code>DataTransformer</code>
 */
public class DataMapTransformer {

    private final ObjectMapper objectMapper;
    private final DataTransformer dataTransformer;

    public Map<String, Object> transformDivorceCaseDataToCourtCaseData(Map<String, Object> divorceSessionMap) {
        DivorceSession divorceSession = objectMapper.convertValue(divorceSessionMap, DivorceSession.class);

        CoreCaseData coreCaseData = dataTransformer.transformDivorceCaseDataToCourtCaseData(divorceSession);

        return objectMapper.convertValue(coreCaseData, new TypeReference<Map<String, Object>>() {
        });
    }

    public Map<String, Object> transformCoreCaseDataToDivorceCaseData(Map<String, Object> coreCaseDataMap) {
        CoreCaseData coreCaseData = objectMapper.convertValue(coreCaseDataMap, CoreCaseData.class);

        DivorceSession divorceSession = dataTransformer.transformCoreCaseDataToDivorceCaseData(coreCaseData);

        return objectMapper.convertValue(divorceSession, new TypeReference<Map<String, Object>>() {
        });
    }
}