package uk.gov.hmcts.reform.divorce.formatter.mapper.divorcetoccdformat;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.reform.divorce.formatter.mapper.DocumentUrlRewrite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DocumentUrlRewriteUTest {

    private static final String DM_STORE_URL = "Http://Dmstore.url";

    private DocumentUrlRewrite urlRewrite;

    @Before
    public void setUp() {
        urlRewrite = new DocumentUrlRewrite(DM_STORE_URL);
    }


    @Test
    public void getDocumentUrlShouldReplaceOldDocumentManagementUrl() {
        String oldUrl = "https://api-gateway.preprod.dm.reform.hmcts.net/documents/95f20d8e-7186-4180-98e9-f7400f7c6527";

        String newUrl = urlRewrite.getDocumentUrl(oldUrl);

        assertEquals(String.format("%s/documents/95f20d8e-7186-4180-98e9-f7400f7c6527", DM_STORE_URL),
            newUrl);
    }

    @Test
    public void getDocumentUrlShouldNotRepalceNewDocumentManagementUrl() {
        String url = String.format("%s/documents/95f20d8e-7186-4180-98e9-f7400f7c6527", DM_STORE_URL);

        String updatedUrl = urlRewrite.getDocumentUrl(url);

        assertEquals(url, updatedUrl);
    }


    @Test
    public void givenValidUrl_ReturnDocumentId() {
        String patter = DM_STORE_URL + "/documents/091b343e-198c-4b95-b510-1fec68bd5a00";
        assertEquals("091b343e-198c-4b95-b510-1fec68bd5a00", urlRewrite.getDocumentId(patter).get());
    }

    @Test
    public void givenSlashWithoutDocument_ReturnEmpty() {
        String patter = DM_STORE_URL + "/documents/";
        assertFalse(urlRewrite.getDocumentId(patter).isPresent());
    }

    @Test
    public void givenWithoutDocument_ReturnEmpty() {
        String patter = DM_STORE_URL + "/documents";
        assertFalse(urlRewrite.getDocumentId(patter).isPresent());
    }
}
