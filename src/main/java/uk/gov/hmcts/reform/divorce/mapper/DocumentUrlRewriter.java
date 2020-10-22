package uk.gov.hmcts.reform.divorce.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.divorce.mapper.config.DataFormatterConfiguration;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DocumentUrlRewriter {

    private static final String URL_REGEX = ".*?(/documents/.*)";

    @Autowired
    @Lazy
    private DataFormatterConfiguration dataFormatterConfiguration;

    private final Pattern urlPattern = Pattern.compile(".*?/documents/(.+)");

    public String getDocumentUrl(String url) {
        String documentManagementStoreUrl = dataFormatterConfiguration.getDocumentManagementStoreUrl();
        if (!url.startsWith(documentManagementStoreUrl)) {
            return url.replaceAll(URL_REGEX, documentManagementStoreUrl + "$1");
        }

        return url;
    }

    public Optional<String> getDocumentId(String docUrl) {
        Matcher urlMatcher = urlPattern.matcher(docUrl);
        if (urlMatcher.find()) {
            log.info("Document url matched, Returning url ");
            return Optional.ofNullable(urlMatcher.group(1));
        }
        log.info("Document url did not matched, Returning empty");
        return Optional.empty();
    }

}