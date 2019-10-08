package uk.gov.hmcts.reform.divorce.model.usersession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import uk.gov.hmcts.reform.divorce.model.payment.Payment;
import uk.gov.hmcts.reform.divorce.model.payment.PaymentCollection;
import uk.gov.hmcts.reform.divorce.model.usersession.corespondent.CoRespondentAnswers;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DivorceSession {
    @ApiModelProperty(value = "Session expiry timestamp.", hidden = true)
    private long expires;
    @ApiModelProperty(value = "Family Man case reference")
    private String caseReference;
    @ApiModelProperty(value = "Has petitioners marriage broken down irretrievably?", allowableValues = "Yes, No")
    private String screenHasMarriageBroken;
    @ApiModelProperty(value = "Has petitioner got an address for the respondent?", allowableValues = "Yes, No")
    private String screenHasRespondentAddress;
    @ApiModelProperty(value = "Has petitioner got their marriage certificate?", allowableValues = "Yes, No")
    private String screenHasMarriageCert;
    @ApiModelProperty(value = "Has petitioner got access to a printer?", allowableValues = "Yes, No")
    private String screenHasPrinter;
    @ApiModelProperty(value = "Does petitioner need help with fees?", allowableValues = "Yes, No")
    private String helpWithFeesNeedHelp;
    @ApiModelProperty(value = "Has petitioner applied for Help With Fees?", allowableValues = "Yes, No")
    private String helpWithFeesAppliedForFees;
    @ApiModelProperty(value = "Agree to apply for Dn?", allowableValues = "Yes, No")
    private String applyForDecreeNisi;
    @ApiModelProperty(
        value = "Help with fees reference. Must conform to regex ([Hh][Ww][Ff]-?)?[0-9a-zA-Z]{3}-?[0-9a-zA-Z]{3}$")
    private String helpWithFeesReferenceNumber;
    @ApiModelProperty(value = "Who is petitioner divorcing?", allowableValues = "husband,wife")
    private String divorceWho;
    @ApiModelProperty(value = "Is same sex couple?", allowableValues = "Yes, No")
    private String marriageIsSameSexCouple;
    @ApiModelProperty(
        value = "The day component of the marriage date in 'dd' transformToCCDFormat. "
            + "This field is not currently used.")
    private Integer marriageDateDay;
    @ApiModelProperty(
        value = "The month component of the marriage date in 'MM' transformToCCDFormat. "
            + "This field is not currently used")
    private Integer marriageDateMonth;
    @ApiModelProperty(
        value = "The year component of the marriage date in 'yyyy' transformToCCDFormat. "
            + "This field is not currently used")
    private Integer marriageDateYear;
    @ApiModelProperty(
        value = "The marriage date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\")")
    private Date marriageDate;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String marriageCanDivorce;
    @ApiModelProperty(value = "Has petitioner got married in the UK?", allowableValues = "Yes, No")
    private String marriedInUk;
    @ApiModelProperty(value = "Is the marriage certificate in English?", allowableValues = "Yes, No")
    private String certificateInEnglish;
    @ApiModelProperty(value = "Has petitioner got a translation of the marriage certificate?",
        allowableValues = "Yes, No")
    private String certifiedTranslation;
    @ApiModelProperty(value = "Country of marriage.")
    private String countryName;
    @ApiModelProperty(value = "Place of marriage (as on marriage certificate).")
    private String placeOfMarriage;
    @ApiModelProperty(value = "Jurisdiction Path")
    private List<String> jurisdictionPath;
    @ApiModelProperty(value = "Legal connections.")
    private List<String> jurisdictionConnection;
    @ApiModelProperty(value = "Legal connections content.")
    private Connections connections;
    @ApiModelProperty(value = "Is petitioner resident?", allowableValues = "Yes, No")
    private String jurisdictionPetitionerResidence;
    @ApiModelProperty(value = "Is respondent resident?", allowableValues = "Yes, No")
    private String jurisdictionRespondentResidence;
    @ApiModelProperty(value = "Is petitioner confident with their legal connections?", allowableValues = "Yes, No")
    private String jurisdictionConfidentLegal;
    @ApiModelProperty(hidden = true) //this field is not mapped to anything
    private String jurisdictionConnectionFirst;
    @ApiModelProperty(value = "Last 12 months.", allowableValues = "Yes, No")
    private String jurisdictionLastTwelveMonths;
    @ApiModelProperty(value = "Is petitioner domiciled?", allowableValues = "Yes, No")
    private String jurisdictionPetitionerDomicile;
    @ApiModelProperty(value = "Is respondent domiciled.", allowableValues = "Yes, No")
    private String jurisdictionRespondentDomicile;
    @ApiModelProperty(value = "Is habitually resident in the last six months?", allowableValues = "Yes, No")
    private String jurisdictionLastHabitualResident;
    @ApiModelProperty(value = "Is residual jurisdiction eligible?", allowableValues = "Yes, No")
    private String residualJurisdictionEligible;
    @ApiModelProperty(value = "Connection summary")
    private String connectionSummary;
    @ApiModelProperty(value = "Petitioners contact details to be kept private?", allowableValues = "share, keep")
    private String petitionerContactDetailsConfidential;
    @ApiModelProperty(value = "Petitioner's current first names.")
    private String petitionerFirstName;
    @ApiModelProperty(value = "Petitioner's current last names.")
    private String petitionerLastName;
    @ApiModelProperty(value = "Respondent's current first names.")
    private String respondentFirstName;
    @ApiModelProperty(value = "Respondent's current last names.")
    private String respondentLastName;
    @ApiModelProperty(value = "Petitioner's full name (as on marriage certificate).")
    private String marriagePetitionerName;
    @ApiModelProperty(value = "Respondent's full name (as on marriage certificate).")
    private String marriageRespondentName;
    @ApiModelProperty(value = "Is petitioner's current name the same as that on marriage certificate?",
        allowableValues = "Yes, No")
    private String petitionerNameDifferentToMarriageCertificate;
    @ApiModelProperty(
        value = "How did the petitioner change their name?", allowableValues = "marriageCertificate, deedPoll, other")
    private List<String> petitionerNameChangedHow;
    @ApiModelProperty(value = "What other details does the petitioner have of the name change?")
    private String petitionerNameChangedHowOtherDetails;
    @ApiModelProperty(value = "Petitioner's email address?")
    private String petitionerEmail;
    @ApiModelProperty(value = "Petitioner's phone number?")
    private String petitionerPhoneNumber;
    @ApiModelProperty(value = "Petitioner's home address.")
    private Address petitionerHomeAddress;
    @ApiModelProperty(value = "Use petitioners home address as address for service?", allowableValues = "Yes, No")
    private String petitionerCorrespondenceUseHomeAddress;
    @ApiModelProperty(value = "Petitioner's correspondence address.")
    private Address petitionerCorrespondenceAddress;
    @ApiModelProperty(value = "Are petitioner and respondent living together?", allowableValues = "Yes, No")
    private String livingArrangementsLiveTogether;
    @ApiModelProperty(hidden = true) //this field is not described in the CCD_Divorce_V60
    private String livingArrangementsLastLivedTogether;
    @ApiModelProperty(value = "Address petitioner and respondent last lived together.")
    private Address livingArrangementsLastLivedTogetherAddress;
    @ApiModelProperty(value = "Does respondent live at last address?", allowableValues = "Yes, No")
    private String respondentLivesAtLastAddress;
    @ApiModelProperty(value = "Respondent home address.")
    private Address respondentHomeAddress;
    @ApiModelProperty(
        value = "Use respondent's home address as their address for service?", allowableValues = "Yes, No, Solicitor")
    private String respondentCorrespondenceUseHomeAddress;
    @ApiModelProperty(value = "Respondent service address.")
    private Address respondentCorrespondenceAddress;
    @ApiModelProperty(
        value = "Fact (reason for divorce)",
        allowableValues = "unreasonable-behaviour, adultery, separation-2-years, separation-5-years, desertion")
    private String reasonForDivorce;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceHasMarriageDate;
    @ApiModelProperty(value = "Is reason for divorce adultery?", allowableValues = "Yes, No")
    private String reasonForDivorceShowAdultery;
    @ApiModelProperty(value = "Is reason for divorce unreasonable behaviour?", allowableValues = "Yes, No")
    private String reasonForDivorceShowUnreasonableBehaviour;
    @ApiModelProperty(value = "Is reason for divorce two year separation?", allowableValues = "Yes, No")
    private String reasonForDivorceShowTwoYearsSeparation;
    @ApiModelProperty(value = "Is reason for divorce five year separation?", allowableValues = "Yes, No")
    private String reasonForDivorceShowFiveYearsSeparation;
    @ApiModelProperty(value = "Is reason for divorce desertion?", allowableValues = "Yes, No")
    private String reasonForDivorceShowDesertion;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceLimitReasons;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceEnableAdultery;
    @ApiModelProperty(value = "Behaviour details")
    private List<String> reasonForDivorceBehaviourDetails;
    @ApiModelProperty(value = "Does petitioner want to name co-respondent?", allowableValues = "Yes, No")
    private String reasonForDivorceAdulteryWishToName;
    @ApiModelProperty(value = "First name of adultery co-respondent.")
    private String reasonForDivorceAdultery3rdPartyFirstName;
    @ApiModelProperty(value = "Last name of adultery co-respondent.")
    private String reasonForDivorceAdultery3rdPartyLastName;
    @ApiModelProperty(value = "Adultery co-respondent address.")
    private Address reasonForDivorceAdultery3rdAddress;
    @ApiModelProperty(value = "Does petitioner know where the adultery took place?", allowableValues = "Yes, No")
    private String reasonForDivorceAdulteryKnowWhere;
    @ApiModelProperty(value = "Does petitioner know when the adultery took place?", allowableValues = "Yes, No")
    private String reasonForDivorceAdulteryKnowWhen;
    @ApiModelProperty(value = "Adultery details.")
    private String reasonForDivorceAdulteryDetails;
    @ApiModelProperty(value = "When did adultery take place?")
    private String reasonForDivorceAdulteryWhenDetails;
    @ApiModelProperty(value = "Where did adultery take place?")
    private String reasonForDivorceAdulteryWhereDetails;
    @ApiModelProperty(value = "Did any of the information about adultery come from another person?",
        allowableValues = "Yes, No")
    private String reasonForDivorceAdulterySecondHandInfo;
    @ApiModelProperty(value = "Details of the information about adultery that has come from another person")
    private String reasonForDivorceAdulterySecondHandInfoDetails;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceDesertionAlright;
    @ApiModelProperty(
        value = "Are there legal proceedings relating to property, marriage or children?", allowableValues = "Yes, No")
    private String legalProceedings;
    @ApiModelProperty(
        value = "Legal proceedings relating to divorce.", allowableValues = "children, property, marriage")
    private List<String> legalProceedingsRelated;
    @ApiModelProperty(value = "Legal proceeding details")
    private String legalProceedingsDetails;
    @ApiModelProperty(value = "Petitioner want a financial order?", allowableValues = "Yes, No")
    private String financialOrder;
    @ApiModelProperty(value = "Who is financial order for?", allowableValues = "petitioner, children")
    private List<String> financialOrderFor;
    @ApiModelProperty(value = "Petitioner to claim costs?", allowableValues = "Yes, No")
    private String claimsCosts;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceAdulteryIsNamed;
    @ApiModelProperty(value = "Claim costs from.", allowableValues = "respondent, co-respondent")
    private List<String> claimsCostsFrom;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String claimsCostsAppliedForFees;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceClaiming5YearSeparation;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceClaimingAdultery;
    @ApiModelProperty(value = "Separation date day in dd transformToCCDFormat.")
    private Integer reasonForDivorceSeperationDay;
    @ApiModelProperty(value = "Separation date month in MM transformToCCDFormat.")
    private Integer reasonForDivorceSeperationMonth;
    @ApiModelProperty(value = "Separation date year in yyyy transformToCCDFormat.")
    private Integer reasonForDivorceSeperationYear;
    @ApiModelProperty(
        value = "The separation date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date reasonForDivorceSeperationDate;
    @ApiModelProperty(value = "Is separation date same as or after limit date?", allowableValues = "Yes, No")
    private String reasonForDivorceSeperationDateIsSameOrAfterLimitDate;
    @ApiModelProperty(value = "Is separation date in the future?", allowableValues = "Yes, No")
    private String reasonForDivorceSeperationDateInFuture;
    @ApiModelProperty(value = "Is separation date before marriage date?", allowableValues = "Yes, No")
    private String reasonForDivorceSeperationDateBeforeMarriageDate;
    @ApiModelProperty(value = "Desertion date day in dd transformToCCDFormat.")
    private Integer reasonForDivorceDesertionDay;
    @ApiModelProperty(value = "Desertion date month in MM transformToCCDFormat.")
    private Integer reasonForDivorceDesertionMonth;
    @ApiModelProperty(value = "Desertion date year in yyyy transformToCCDFormat.")
    private Integer reasonForDivorceDesertionYear;
    @ApiModelProperty(value = "Is desertion date before marriage?", allowableValues = "Yes, No")
    private String reasonForDivorceDesertionBeforeMarriage;
    @ApiModelProperty(value = "Is desertion date in the future?", allowableValues = "Yes, No")
    private String reasonForDivorceDesertionDateInFuture;
    @ApiModelProperty(
        value = "Desertion date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date reasonForDivorceDesertionDate;
    @ApiModelProperty(value = "Did petitioner agree to the desertion?", allowableValues = "Yes, No")
    private String reasonForDivorceDesertionAgreed;
    @ApiModelProperty(value = "Desertion details.")
    private String reasonForDivorceDesertionDetails;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String marriageIsFuture;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String marriageMoreThan100;
    @ApiModelProperty(hidden = true) // this field is not mapped
    private String respondentHome;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceHasMarriage;
    @ApiModelProperty(hidden = true) // this field is not mapped
    private String reasonForDivorceAdultery3rd;
    @ApiModelProperty(hidden = true) // this field is not mapped
    private String reasonForDivorceSeperation;
    @ApiModelProperty(hidden = true) // this field is not mapped
    private String reasonForDivorceSeperationIsSameOrAfterLimit;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceSeperationInFuture;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceSeperationBeforeMarriage;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */ allowableValues = "Yes, No")
    private String reasonForDivorceDesertionInFuture;
    @ApiModelProperty(/* The spreadsheet does not say what this field means */)
    private String reasonForDivorceDesertion;
    @ApiModelProperty(
        value = "Respondent current name is the same as that on marriage certificate?", allowableValues = "Yes, No")
    private String respondentNameAsOnMarriageCertificate;
    @ApiModelProperty(value = "Is respondent using a solicitor?", allowableValues = "Yes, No")
    private String respondentSolicitorRepresented;
    @ApiModelProperty(value = "Does petitioner know the respondents home address?", allowableValues = "Yes, No")
    private String respondentKnowsHomeAddress;
    @ApiModelProperty(hidden = true)
    private String sessionKey;
    @ApiModelProperty(value = "Regional divorce unit details")
    private Map<String, Map<String, Object>> court;
    @ApiModelProperty(value = "Regional divorce unit.")
    private String courts;
    @ApiModelProperty(value = "Name of solicitor used by respondent.")
    private String respondentSolicitorName;
    @ApiModelProperty(value = "Company of solicitor used by respondent.")
    private String respondentSolicitorCompany;
    @ApiModelProperty(value = "Email of solicitor used by respondent.")
    private String respondentSolicitorEmail;
    @ApiModelProperty(value = "Phone number of solicitor used by respondent.")
    private String respondentSolicitorPhoneNumber;
    @ApiModelProperty(value = "Address of solicitor used by respondent.")
    private Address respondentSolicitorAddress;
    @ApiModelProperty(value = "Respondent's Solicitor reference number.")
    private String respondentSolicitorReference;
    @ApiModelProperty(value = "Agree to statement of truth?", allowableValues = "Yes, No")
    private String confirmPrayer;
    @ApiModelProperty(value = "Payment details.")
    private Payment payment;
    @ApiModelProperty(value = "Supporting documentation uploaded with the application")
    private List<UploadedFile> marriageCertificateFiles;
    @ApiModelProperty(value = "Details about existing payments.")
    private List<PaymentCollection> existingPayments;
    @ApiModelProperty(value = "D8 PDF file URL details.")
    @JsonProperty("d8")
    @Setter(AccessLevel.NONE)
    private List<UploadedFile> d8Documents;
    @ApiModelProperty(value = "Agree receive communications?", allowableValues = "Yes, No")
    private String petitionerConsent;
    @ApiModelProperty(value = " Reference date used for 6-month rule calculation of "
        + "time petitioner and respondent can have lived together.")
    private String referenceDate;

    private Integer livingTogetherMonths;

    private String mostRecentSeparationDate;

    private Date createdDate;

    @ApiModelProperty(
        value = "Issue date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date issueDate;

    @ApiModelProperty(
        value = "Due date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date dueDate;

    @ApiModelProperty(value = "Petitioner has obtained a written consent from the respondent?",
        allowableValues = "Yes, No")
    private String reasonForDivorceRespondentConsent;

    @ApiModelProperty(
        value = "Date the petitioner decided the marriage was over, in one of the following formats "
            + "(\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", \"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", \"yyyy-MM-dd\").")
    private Date reasonForDivorceDecisionDate;

    @ApiModelProperty(
        value = "Date the petitioner and respondent started living apart, in one of the following formats "
            + "(\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", \"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", \"yyyy-MM-dd\").")
    private Date reasonForDivorceLivingApartDate;

    @ApiModelProperty(value = "Respondent contact details to be kept private?", allowableValues = "share, keep")
    private String respondentContactDetailsConfidential;

    @ApiModelProperty(value = "Maximum separation time together permitted?")
    @JsonProperty("separationTimeTogetherPermitted")
    private String timeLivedTogetherPermitted;

    @ApiModelProperty(value = "Has petitioner & respondent lived more than"
        + " the time together permitted?", allowableValues = "Yes, No")
    private String livedTogetherMoreTimeThanPermitted;
    @ApiModelProperty(value = "Has petitioner & respondent lived apart"
        + " for the entire time since separated?", allowableValues = "Yes, No")
    private String livedApartEntireTime;

    //Aos Fields Mappings Start
    @ApiModelProperty(value = "Respondent confirmed petition read.")
    private String respConfirmReadPetition;
    @ApiModelProperty(value = "Respondent agreed to claimed jurisdiction?")
    private String respJurisdictionAgree;
    @ApiModelProperty(value = "Respondent agreed or disagree with the reason for divorce?")
    private String respAdmitOrConsentToFact;
    @ApiModelProperty(value = "Will respondent defend the divorce?", allowableValues = "Yes, No, NoNoAdmission")
    private String respWillDefendDivorce;
    @ApiModelProperty(value = "Reason respondent disagreed to claimed jurisdiction")
    private String respJurisdictionDisagreeReason;
    @ApiModelProperty(value = "Respondent country of residence")
    private String respJurisdictionRespCountryOfResidence;
    @ApiModelProperty(value = "Do legal proceedings exist (respondent)?")
    private String respLegalProceedingsExist;
    @ApiModelProperty(value = "Legal proceedings details (respondent)")
    private String respLegalProceedingsDescription;
    @ApiModelProperty(value = "Does respondent agree to costs?")
    private String respAgreeToCosts;
    @ApiModelProperty(value = "Respondent's costs amount")
    private String respCostsAmount;
    @ApiModelProperty(value = "Respondent's costs reason")
    private String respCostsReason;
    @ApiModelProperty(value = "Respondent's email address")
    private String respEmailAddress;
    @ApiModelProperty(value = "Respondent's telephone number")
    private String respPhoneNumber;
    @ApiModelProperty(value = "Respondent agreed to statement of truth?")
    private String respStatementOfTruth;
    @ApiModelProperty(value = "Respondent consented to email communications?")
    private String respConsentToEmail;
    @ApiModelProperty(value = "Respondent requires consideration of final situation?")
    private String respConsiderFinancialSituation;
    @ApiModelProperty(value = "Respondent relying on hardship defense?")
    private String respHardshipDefenseResponse;
    @ApiModelProperty(value = "Hardship defence details")
    private String respHardshipDescription;
    @ApiModelProperty(value = "Reason for AwaitingDecreeNisi?")
    private String permittedDecreeNisiReason;
    @ApiModelProperty(value = "Respondent submitted AOS date")
    private Date receivedAosFromRespDate;
    @ApiModelProperty(value = "Respondent has submitted AOS")
    private String receivedAosFromResp;

    @ApiModelProperty(value = "Answers from co respondent")
    private CoRespondentAnswers coRespondentAnswers;
    //Aos Fields Mappings End

    //DnCase Fields Mapping Start
    @ApiModelProperty(value = "Dn Petition needs changes")
    private String hasBeenChanges;
    @ApiModelProperty(value = "Dn Petition Change details")
    private String changesDetails;
    @ApiModelProperty(value = "Dn Change Confirmation Petition Yes")
    private String statementOfTruthChanges;
    @ApiModelProperty(value = "Dn Change Confirmation Petition No")
    private String statementOfTruthNoChanges;
    @ApiModelProperty(value = "Dn Divorce Cost Option originalAmount/differentAmount/endClaim")
    private String claimCosts;
    @ApiModelProperty(value = "Details when Divorce Cost Option is differentAmount")
    private String costsDifferentDetails;
    @ApiModelProperty(value = "Dn Statement of Truth")
    private String statementOfTruth;
    @ApiModelProperty(value = "Dn Adultery life is Intolerable")
    private String intolerable;
    @ApiModelProperty(
        value = "AdulteryFirstFoundDate date in one of the following formats (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date adulteryFirstFoundDate;
    @ApiModelProperty(value = "Dn Live apart since event")
    private String livedApartSinceAdultery;
    @ApiModelProperty(value = "Dn Adultery time lived together")
    private String datesLivedTogether;
    @ApiModelProperty(value = "Dn Behaviour still happening")
    private String behaviourContinuedSinceApplication;
    @ApiModelProperty(
        value = "Dn Behaviour most recent incident date in one of the following formats "
            + "(\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", \"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", "
            + "\"EEE, dd MMM yyyy HH:mm:ss zzz\", \"yyyy-MM-dd\").")
    private Date lastIncidentDate;
    @ApiModelProperty(value = "Dn Behaviour lived apart since event")
    private String livedApartSinceLastIncidentDate;
    @ApiModelProperty(value = "Dn Behaviour time lived together details")
    private String approximateDatesOfLivingTogetherField;
    @ApiModelProperty(value = "Dn Desertion - Respondent asked petitioner to resume living together")
    private String desertionAskedToResumeDN;
    @ApiModelProperty(value = "Dn Desertion - Did the petitioner refuse to  resume living together")
    private String desertionAskedToResumeDNRefused;
    @ApiModelProperty(value = "Dn Desertion - Resuming living together refusal details")
    private String desertionAskedToResumeDNDetails;
    @ApiModelProperty(value = "Dn Desertion live apart since event")
    private String livedApartSinceDesertion;
    @ApiModelProperty(value = "Dn Separation time lived together")
    private String livedApartSinceSeparation;
    @ApiModelProperty(value = "Dn uploaded documents URL details.")
    @JsonProperty("files")
    private List<UploadedFile> files;
    @ApiModelProperty(value = "Agree to apply for Dn?", allowableValues = "Yes, No")
    private String uploadAnyOtherDocuments;
    //DnCase Fields Mapping End
    //Dn Approval Fields Mapping Start
    @ApiModelProperty(value = "Has the Costs Claim been granted")
    private String costsClaimGranted;
    @ApiModelProperty(value = "Who has been ordered to pay the costs")
    private String whoPaysCosts;
    @ApiModelProperty(value = "The type of cost decision made")
    private String typeCostsDecision;
    @ApiModelProperty(value = "Any additional information on the cost order")
    private String costsOrderAdditionalInfo;
    @ApiModelProperty(value = "Date Decree Nisi has been pronounced")
    private Date decreeNisiGrantedDate;
    @ApiModelProperty(value = "Date Decree Absolute can be aplied for")
    private Date decreeAbsoluteEligibleFromDate;
    //Dn Approval Fields Mapping End
    //Dn Bulk Listing Fields Mapping Start
    @ApiModelProperty(value = "Name of the Court where the Hearing is Scheduled")
    private String courtName;
    @ApiModelProperty(value = "Date of the Hearing in \"yyyy-MM-dd\" format")
    private String hearingDate;
    @ApiModelProperty(value = "Time of the Hearing in \"HH:mm\" format")
    private String hearingTime;
    //Dn Bulk Listing Fields Mapping End
    //DaCase Fields Mapping Start
    @ApiModelProperty(value = "Petitioner Applied for Decree Absolute", allowableValues = "Yes, No")
    private String applyForDecreeAbsolute;
    //DaCase Fields Mapping End

    @ApiModelProperty(value = "Case ID from previously amended case")
    private String previousCaseId;

    @ApiModelProperty(
        value = "Issue date from previously amended case in one of the following formats "
            + " (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", "
            + "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", "
            + "\"yyyy-MM-dd\").")
    private Date previousIssueDate;

    @ApiModelProperty(value = "List of previous reasons used for divorce, before amending petition")
    private List<String> previousReasonsForDivorce;

    @ApiModelProperty(
        value = "Date from which the respondent can apply for Decree Absolute.")
    private Date dateRespondentEligibleForDA;

    @ApiModelProperty(
        value = "Final date to apply for Decree Absolute.")
    private Date dateCaseNoLongerEligibleForDA;

    @ApiModelProperty("Reason for why clarification is needed.")
    private List<String> refusalClarificationReason;

    @ApiModelProperty("Any additional input by the legal advisor.")
    private String refusalClarificationAdditionalInfo;

    @ApiModelProperty(value = "Clarification response for the current clarification journey")
    private String clarificationResponse;

    @ApiModelProperty(value = "Flag for online Decree Nisi Outcome journey")
    private String dnOutcomeCase;

    public void setD8Documents(List<UploadedFile> d8Documents) {
        if (CollectionUtils.isNotEmpty(d8Documents)) {
            d8Documents.forEach(doc -> doc.setFileType("petition"));
            this.d8Documents = d8Documents;
        }
    }
}
