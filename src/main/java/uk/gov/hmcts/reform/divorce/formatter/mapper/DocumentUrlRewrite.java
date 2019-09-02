package uk.gov.hmcts.reform.divorce.formatter.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class DocumentUrlRewrite {

    private static final String URL_REGEX = ".*?(/documents/.*)";


    private final String documentManagementStoreUrl;

    private final Pattern urlPatter; // Document pattern

    public DocumentUrlRewrite(@Value("${document.management.store.url}") String documentManagementStoreUrl) {
        log.info("DM store url {}", documentManagementStoreUrl);
        this.documentManagementStoreUrl = documentManagementStoreUrl;
        this.urlPatter = Pattern.compile(".*?/documents/(.+)");
    }

    public String getDocumentUrl(String url) {
        if (!url.startsWith(documentManagementStoreUrl)) {
            return url.replaceAll(URL_REGEX, documentManagementStoreUrl + "$1");
        }

        return url;
    }

    public Optional<String> getDocumentId(String docUrl) {
        Matcher urlMatcher = urlPatter.matcher(docUrl);
        if (urlMatcher.find()) {
            log.info("Document url matched, Returning url ");
            return Optional.ofNullable(urlMatcher.group(1));
        }
        log.info("Document url did not matched, Returning empty");
        return Optional.empty();
    }
}
