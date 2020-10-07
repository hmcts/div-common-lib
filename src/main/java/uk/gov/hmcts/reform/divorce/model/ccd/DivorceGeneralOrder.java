package uk.gov.hmcts.reform.divorce.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import uk.gov.hmcts.reform.divorce.model.parties.DivorceParty;

import java.util.List;

@Data
@Builder
public class DivorceGeneralOrder {

    @JsonProperty("Document")
    private Document document;

    @JsonProperty("GeneralOrderParties")
    private List<DivorceParty> generalOrderParties;

}