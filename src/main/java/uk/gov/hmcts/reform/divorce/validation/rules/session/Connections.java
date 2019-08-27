package uk.gov.hmcts.reform.divorce.validation.rules.session;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import lombok.Data;
import uk.gov.hmcts.reform.divorce.models.request.DivorceSession;

import java.util.List;
import java.util.Optional;

@Rule(order = 24)
@Data
public class Connections {

    static final String BLANK_SPACE = " ";
    static final String ACTUAL_DATA = "Actual data is: %s";
    static final String ERROR_MESSAGE = "connections can not be null or empty.";

    @Result
    public List<String> result;

    @Given("divorceSession")
    public DivorceSession divorceSession = new DivorceSession();

    @When
    public boolean when() {
        return !Optional.ofNullable(divorceSession.getConnections()).isPresent();
    }

    @Then
    public void then() {
        result.add(String.join(
            BLANK_SPACE, // delimiter
            ERROR_MESSAGE,
            String.format(ACTUAL_DATA, divorceSession.getConnections())
        ));
    }
}