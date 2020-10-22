package uk.gov.hmcts.reform.divorce.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.reform.divorce.mapper.config.DataFormatterConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentUrlRewriterUTest {

    private static final String DM_STORE_URL = "Http://Dmstore.url";

    @Mock
    private DataFormatterConfiguration dataFormatterConfiguration;

    @InjectMocks
    private DocumentUrlRewriter urlRewriter;

    @Before
    public void setUp() {
        when(dataFormatterConfiguration.getDocumentManagementStoreUrl()).thenReturn(DM_STORE_URL);
    }

    @Test
    public void getDocumentUrlShouldReplaceOldDocumentManagementUrl() {
        String oldUrl = "https://api-gateway.preprod.dm.reform.hmcts.net/documents/95f20d8e-7186-4180-98e9-f7400f7c6527";

        String newUrl = urlRewriter.getDocumentUrl(oldUrl);

        assertEquals(String.format("%s/documents/95f20d8e-7186-4180-98e9-f7400f7c6527", DM_STORE_URL), newUrl);
    }

    @Test
    public void getDocumentUrlShouldNotReplaceNewDocumentManagementUrl() {
        String url = String.format("%s/documents/95f20d8e-7186-4180-98e9-f7400f7c6527", DM_STORE_URL);

        String updatedUrl = urlRewriter.getDocumentUrl(url);

        assertEquals(url, updatedUrl);
    }

    @Test
    public void givenValidUrl_ReturnDocumentId() {
        String pattern = DM_STORE_URL + "/documents/091b343e-198c-4b95-b510-1fec68bd5a00";
        assertEquals("091b343e-198c-4b95-b510-1fec68bd5a00", urlRewriter.getDocumentId(pattern).get());
    }

    @Test
    public void givenSlashWithoutDocument_ReturnEmpty() {
        String pattern = DM_STORE_URL + "/documents/";
        assertFalse(urlRewriter.getDocumentId(pattern).isPresent());
    }

    @Test
    public void givenWithoutDocument_ReturnEmpty() {
        String pattern = DM_STORE_URL + "/documents";
        assertFalse(urlRewriter.getDocumentId(pattern).isPresent());
    }
}
