package uk.gov.hmcts.reform.divorce.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DivorceSession {

    private long expires;
    private String screenHasMarriageBroken;
    private String screenHasRespondentAddress;
    private String screenHasMarriageCert;
    private String screenHasPrinter;
    private String helpWithFeesNeedHelp;

    private String helpWithFeesAppliedForFees;

    private String helpWithFeesReferenceNumber;

    private String divorceWho;

    private String marriageIsSameSexCouple;

    private String marriageDateDay;

    private String marriageDateMonth;

    private String marriageDateYear;

    private Date marriageDate;

    private String marriageCanDivorce;

    private String marriageDateIsFuture;

    private String marriageDateMoreThan100;

    private String marriageWhereMarried;

    private String marriedInUk;

    private String certificateInEnglish;


    private String certifiedTranslation;

    private String countryName;

    private String placeOfMarriage;

    private List<String> jurisdictionPath;

    private List<String> jurisdictionConnection;

    private Connections connections;

    private String jurisdictionPetitionerResidence;

    private String jurisdictionRespondentResidence;

    private String jurisdictionConfidentLegal;

    private String jurisdictionConnectionFirst;

    private String jurisdictionLastTwelveMonths;

    private String jurisdictionPetitionerDomicile;

    private String jurisdictionRespondentDomicile;

    private String jurisdictionLastHabitualResident;

    private String residualJurisdictionEligible;

    private String connectionSummary;

    private String petitionerContactDetailsConfidential;

    private String petitionerFirstName;

    private String petitionerLastName;

    private String respondentFirstName;

    private String respondentLastName;

    private String marriagePetitionerName;

    private String marriageRespondentName;

    private String petitionerNameDifferentToMarriageCertificate;

    private List<String> petitionerNameChangedHow;

    private String petitionerNameChangedHowOtherDetails;

    private String petitionerEmail;

    private String petitionerPhoneNumber;

    private Address petitionerHomeAddress;

    private String petitionerCorrespondenceUseHomeAddress;

    private Address petitionerCorrespondenceAddress;

    private String livingArrangementsLiveTogether;

    private String livingArrangementsLastLivedTogether;

    private Address livingArrangementsLastLivedTogetherAddress;

    private String respondentLivesAtLastAddress;

    private Address respondentHomeAddress;

    private String respondentCorrespondenceUseHomeAddress;

    private Address respondentCorrespondenceAddress;

    private String reasonForDivorce;

    private String reasonForDivorceHasMarriageDate;

    private String reasonForDivorceShowAdultery;

    private String reasonForDivorceShowUnreasonableBehaviour;

    private String reasonForDivorceShowTwoYearsSeparation;

    private String reasonForDivorceShowFiveYearsSeparation;

    private String reasonForDivorceShowDesertion;

    private String reasonForDivorceLimitReasons;

    private String reasonForDivorceEnableAdultery;

    private List<String> reasonForDivorceBehaviourDetails;

    private String reasonForDivorceAdulteryWishToName;

    private String reasonForDivorceAdultery3rdPartyFirstName;

    private String reasonForDivorceAdultery3rdPartyLastName;

    private Address reasonForDivorceAdultery3rdAddress;

    private String reasonForDivorceAdulteryKnowWhere;

    private String reasonForDivorceAdulteryKnowWhen;

    private String reasonForDivorceAdulteryDetails;

    private String reasonForDivorceAdulteryWhenDetails;

    private String reasonForDivorceAdulteryWhereDetails;

    private String reasonForDivorceDesertionAlright;

    private String legalProceedings;

    private List<String> legalProceedingsRelated;

    private String legalProceedingsDetails;

    private String financialOrder;

    private List<String> financialOrderFor;

    private String claimsCosts;

    private String reasonForDivorceAdulteryIsNamed;

    private List<String> claimsCostsFrom;

    private String claimsCostsAppliedForFees;

    private String reasonForDivorceClaiming5YearSeparation;

    private String reasonForDivorceClaimingAdultery;

    private String reasonForDivorceSeperationDay;

    private String reasonForDivorceSeperationMonth;

    private String reasonForDivorceSeperationYear;

    private Date reasonForDivorceSeperationDate;

    private String reasonForDivorceSeperationDateIsSameOrAfterLimitDate;

    private String reasonForDivorceSeperationDateInFuture;

    private String reasonForDivorceSeperationDateBeforeMarriageDate;

    private String reasonForDivorceDesertionDay;

    private String reasonForDivorceDesertionMonth;

    private String reasonForDivorceDesertionYear;

    private String reasonForDivorceDesertionBeforeMarriage;

    private String reasonForDivorceDesertionDateInFuture;

    private Date reasonForDivorceDesertionDate;

    private String reasonForDivorceDesertionAgreed;

    private String reasonForDivorceDesertionDetails;

    private String marriageIsFuture;

    private String marriageMoreThan100;

    private String respondentHome;

    private String reasonForDivorceHasMarriage;

    private String reasonForDivorceAdultery3rd;

    private String reasonForDivorceSeperation;

    private String reasonForDivorceSeperationIsSameOrAfterLimit;

    private String reasonForDivorceSeperationInFuture;

    private String reasonForDivorceSeperationBeforeMarriage;

    private String reasonForDivorceDesertionInFuture;

    private String reasonForDivorceDesertion;


    private String respondentNameAsOnMarriageCertificate;

    private String respondentCorrespondenceSendToSolicitor;

    private String respondentKnowsHomeAddress;

    private String sessionKey;

    private String courts;

    private String respondentSolicitorName;

    private String respondentSolicitorCompany;

    private Address respondentSolicitorAddress;

    private String confirmPrayer;

    private Payment payment;

    private List<UploadedFile> marriageCertificateFiles;

    private List<PaymentCollection> existingPayments;

    @JsonProperty("d8")
    @Setter(AccessLevel.NONE)
    private List<UploadedFile> d8Documents;

    public void setD8Documents(List<UploadedFile> d8Documents) {
        d8Documents.forEach(doc -> doc.setFileType("petition"));
        this.d8Documents = d8Documents;
    }

}
