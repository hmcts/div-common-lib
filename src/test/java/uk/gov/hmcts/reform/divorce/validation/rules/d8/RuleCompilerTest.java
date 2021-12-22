package uk.gov.hmcts.reform.divorce.validation.rules.d8;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class RuleCompilerTest {



    @Before
    public void setup() {


    }

    @Test
    public void rulesShouldBeAddedInCorrectOrder() {
        RuleCompiler ruleCompiler = new RuleCompiler();

        assertThat(ruleCompiler.rulesList, contains(D8InferredPetitionerGender.class,
                D8InferredRespondentGender.class,
                D8MarriageDate.class,
                D8MarriagePetitionerName.class,
                D8MarriageRespondentName.class,
                D8PetitionerFirstName.class,
                D8PetitionerLastName.class,
                D8PetitionerContactDetailsConfidential.class,
                D8RespondentFirstName.class,
                D8RespondentLastName.class,
                D8LegalProceedings.class,
                D8ReasonForDivorce.class,
                D8ReasonForDivorceBehaviourDetails.class,
                D8ReasonForDivorceDesertionDate.class,
                D8ReasonForDivorceDesertionDetails.class,
                D8ReasonForDivorceSeperationDate.class,
                D8ReasonForDivorceAdulteryDetails.class,
                D8FinancialOrder.class,
                D8DivorceCostsClaim.class,
                D8JurisdictionConnection.class,
                D8StatementOfTruth.class
                ));
    }

}
