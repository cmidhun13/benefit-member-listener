package com.szells.membership.adaptor;

import com.szells.membership.domain.BenefitPackageResponse;
import com.szells.util.domain.RequestHeader;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.ServiceParameters;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
public class StandingDataAdaptorTest {

    private static MockWebServer mockWebServer;
    private StandingDataAdaptor adaptor;

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setUp() {
        adaptor = new StandingDataAdaptor();
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());

        ReflectionTestUtils.setField(adaptor, "standingDataUrl", baseUrl + "solIds");
    }

    @Test
    public void testFindPackagesBySolicitationId() {
        mockWebServer.enqueue(new MockResponse()
                .setBody((Objects.requireNonNull(buildResponse())))
                .addHeader("Content-Type", "application/json")
        );
        List<BenefitPackageResponse> response = adaptor.findPackagesBySolicitationId(buildInput())
                .collectList().block();
        Assert.assertNotNull(response);
    }

    private ServiceParameters<String> buildInput() {
        return ServiceParameters.<String>builder()
                .headers(RequestHeader.builder()
                        .authorization("213-21434235345")
                        .build())
                .parameters(RequestParameters.builder()
                        .param("some", "ae")
                        .build())
                .build();
    }

    private String buildResponse() {
        return "{\"returnDetails\":{\"code\":200,\"messageDescription\":\"Data Retrieved\",\"messageSource\":\"benefit-standing-data-service\",\"errorDetails\":null},\"standingDataResponse\":{\"packageId\":1020254,\"packageName\":\"Older Cardholders (29+)\",\"packageTypeName\":\"Flexible Parent Package\",\"packageNameTranslatedValue\":null,\"packageTypeId\":null,\"externalPkgReferenceName\":\"MCNOEIKTRP002\",\"emailOverrideFlag\":\"N\",\"noOfDaysTCRenewal\":null,\"abbreviation\":null,\"benefitSelectionType\":{\"benefitSelectionTypeId\":2,\"name\":\"Tiered\"},\"numFlexibleChildPkgAllowed\":1,\"numFlexibleBenAllowed\":null,\"minFlexibleChildPkgAllowed\":1,\"minFlexibleBenAllowed\":null,\"introHtmlPath\":null,\"emailCopy\":null,\"configDataStatusDesc\":null,\"countryGroupDesc\":null,\"organizationId\":16,\"clientId\":1023624,\"restrictedCountryGroupDesc\":null,\"packageReferenceName\":\"MCNOEIKTRP002\",\"flexibleBenefitAllowedFlag\":\"N\",\"switchHardletterCorrespond\":\"N\",\"standingDataFileId\":null,\"benefits\":[{\"benefitId\":1010144,\"benefitName\":\"MC NOR - ID insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00073\",\"externalReferenceName\":\"MCEIKNOR011\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"ID-tyveriforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Skulle noen misbruke din identitet, er ID-tyveriforsikring inkludert i kortet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010104,\"benefitName\":\"MC NOR - Regional block\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00069\",\"externalReferenceName\":\"MCEIKNOR007\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Regionsperre\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Regionsperre er noe du selv kan velge å sette på kortet ditt, slik at kortet kun kan benyttes i utvalgte deler av verden\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":126,\"vendorName\":\"Mastercard\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010164,\"benefitName\":\"MC NOR - Pay with wearables\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00075\",\"externalReferenceName\":\"MCEIKNOR013\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Betal med dingser\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Betal med klokker, armbånd og andre dingser\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010064,\"benefitName\":\"MC NOR - Same Pin\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00065\",\"externalReferenceName\":\"MCEIKNOR003\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Samme PIN-kode\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Lik PIN-kode på bankkortet og kredittkortet fra lokalbanken\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010174,\"benefitName\":\"MC NOR - Debt Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00076\",\"externalReferenceName\":\"MCEIKNOR014\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Betalingsforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Sikre deg og familien ved uforutsette livshendelser\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[{\"benAttributeId\":12794,\"benefitId\":1010174,\"benefitAttributeType\":{\"benAttributeTypeId\":38,\"benAttributeTypeName\":\"Early Cancellation\"},\"defaultValue\":\"1\",\"benAttrValueType\":3}],\"vendor\":{\"vendorId\":1744,\"vendorName\":\"Amtrust\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010154,\"benefitName\":\"MC NOR - Alert service\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00074\",\"externalReferenceName\":\"MCEIKNOR012\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Varslingstjeneste\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Vi varsler deg ved unormal aktivitet på kortet ditt\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010044,\"benefitName\":\"MC NOR - Free ATM\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00063\",\"externalReferenceName\":\"MCEIKNOR001\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Gebyrfritt minibankuttak i utlandet\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Om du velger lokal valuta i utenlandske minibanker så betaler du ingen gebyrer for uttaket\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010054,\"benefitName\":\"MC NOR - Travel insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00064\",\"externalReferenceName\":\"MCEIKNOR002\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Reise- og avbestillingsforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Alltid trygg på reise med standard reise- og avbestillingsforsikring inkludert i kortet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010134,\"benefitName\":\"MC NOR - Eika rentalcars\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00072\",\"externalReferenceName\":\"MCEIKNOR010\",\"benefitType\":{\"benefitTypeId\":13,\"benefitTypeDescription\":\"General\"},\"shortDescription\":\"Eika leiebil\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Alltid lavest pris på leiebil og 10% ekstra rabatt når du bruker ditt Eika kredittkort på eikaleiebil.no\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010114,\"benefitName\":\"MC NOR - Cancel service\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00070\",\"externalReferenceName\":\"MCEIKNOR008\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Sperretjeneste\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Rask og effektiv sperring av kortet dersom uhellet er ute\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010094,\"benefitName\":\"MC NOR - Transfer to bankaccount\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00068\",\"externalReferenceName\":\"MCEIKNOR006\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Overføring kort til konto\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Når utgiftene svinger opp og ned, er det lurt å ha økonomisk fleksibilitet i hverdagen\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010124,\"benefitName\":\"MC NOR - Pay out other cards\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00071\",\"externalReferenceName\":\"MCEIKNOR009\",\"benefitType\":{\"benefitTypeId\":21,\"benefitTypeDescription\":\"Feature\"},\"shortDescription\":\"Innfrielse av andre kort\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Samle all kredittkortgjeld på ett sted\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010074,\"benefitName\":\"MC NOR - Priceless cities\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00066\",\"externalReferenceName\":\"MCEIKNOR004\",\"benefitType\":{\"benefitTypeId\":13,\"benefitTypeDescription\":\"General\"},\"shortDescription\":\"Priceless cities\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Mastercard gir deg tilgang til spennende opplevelser og billetter til en rekke arrangementer over hele verden\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"vendor\":{\"vendorId\":126,\"vendorName\":\"Mastercard\",\"vendorOrganizationAttribute\":null}}],\"packageAttributes\":[{\"packageAttributeId\":18944,\"packageId\":1020254,\"packageAttributeTypeDesc\":\"MRSBankProductCode\",\"packageAttributeValue\":\"EIKATRANS\",\"packageAttributeValuteType\":2},{\"packageAttributeId\":19134,\"packageId\":1020254,\"packageAttributeTypeDesc\":\"MRS integration required\",\"packageAttributeValue\":\"1\",\"packageAttributeValuteType\":3},{\"packageAttributeId\":18954,\"packageId\":1020254,\"packageAttributeTypeDesc\":\"MRSProgramIdentifier\",\"packageAttributeValue\":\"16926MRSEIKA\",\"packageAttributeValuteType\":2},{\"packageAttributeId\":19594,\"packageId\":1020254,\"packageAttributeTypeDesc\":\"MemberICA\",\"packageAttributeValue\":\"016926\",\"packageAttributeValuteType\":2},{\"packageAttributeId\":18734,\"packageId\":1020254,\"packageAttributeTypeDesc\":\"Reinstate Grace Period\",\"packageAttributeValue\":\"30\",\"packageAttributeValuteType\":1}],\"flexibleChildPackage\":[{\"flexibleChildPackageId\":5844,\"accountId\":1026834,\"parentPackageId\":1020254,\"childPackageId\":1020364,\"isDefault\":0,\"cost\":59,\"displayOrderNumber\":1,\"priceDescription\":\"59 kr per måned\",\"insuranceDescription\":null,\"childPackage\":{\"childPackageId\":1020364,\"benefitSelectionTypeId\":null,\"childPackageName\":\"Hverdag\",\"childPackageTypeName\":\"Flexible Child Package\",\"description\":null,\"externalPkgReferenceName\":\"till008\",\"packageNameTranslatedValue\":null,\"descriptionTranslatedValue\":null},\"benefits\":[{\"benefitId\":1010244,\"benefitName\":\"MC NOR - 1% on all domestic transactions\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00083\",\"externalReferenceName\":\"MCEIKNOR021\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"1% bonus på alle varekjøp i norske kroner\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 1 kr i bonus for hver 100 kroner du bruker i Norge\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010304,\"benefitName\":\"MC NOR - Purchase & Price match Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00089\",\"externalReferenceName\":\"MCEIKNOR027\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Kjøps- og prisgarantiforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Forsikring som dekker skader ved varer du har kjøpt med kortet, samt dekning av prisdifferanse dersom du finner en vare du har kjøpt med kortet billigere et annet sted\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1744,\"vendorName\":\"Amtrust\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010214,\"benefitName\":\"MC NOR - 20% on Streaming\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00080\",\"externalReferenceName\":\"MCEIKNOR018\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"20% bonus på strømmetjeneste\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 20% bonus på en av følgende strømmetjenester: Netflix, Spotify, HBO Nordic ...\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}}]},{\"flexibleChildPackageId\":5864,\"accountId\":1026834,\"parentPackageId\":1020254,\"childPackageId\":1020374,\"isDefault\":0,\"cost\":59,\"displayOrderNumber\":2,\"priceDescription\":\"59 kr per måned\",\"insuranceDescription\":null,\"childPackage\":{\"childPackageId\":1020374,\"benefitSelectionTypeId\":null,\"childPackageName\":\"Reise\",\"childPackageTypeName\":\"Flexible Child Package\",\"description\":null,\"externalPkgReferenceName\":\"till009\",\"packageNameTranslatedValue\":null,\"descriptionTranslatedValue\":null},\"benefits\":[{\"benefitId\":1010274,\"benefitName\":\"MC NOR - LoungeKey\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00086\",\"externalReferenceName\":\"MCEIKNOR024\",\"benefitType\":{\"benefitTypeId\":12,\"benefitTypeDescription\":\"Travel\"},\"shortDescription\":\"Loungetilgang inkludert 1 gratis besøk i året\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Loungetilgangen gir deg mulighet til å unngå overfylte avgangshaller, og få en mer behagelig reiseopplevelse på flyplasser over hele verden.\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":120,\"vendorName\":\"Collinson Group\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010254,\"benefitName\":\"MC NOR - 1.5% on all x-border transactions\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00084\",\"externalReferenceName\":\"MCEIKNOR022\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"1,5% bonus på alle varekjøp i utenlandsk valuta\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 1,50 kr i bonus for hver 100 kroner du bruker i utlandet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010294,\"benefitName\":\"MC NOR - Rental Car Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00088\",\"externalReferenceName\":\"MCEIKNOR026\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Egenandelsforsikring leiebil\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Forsikring som dekker egenandel ved skade med leiebil\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010264,\"benefitName\":\"MC NOR - 10% cashback on Eika Hotel\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00085\",\"externalReferenceName\":\"MCEIKNOR023\",\"benefitType\":{\"benefitTypeId\":13,\"benefitTypeDescription\":\"General\"},\"shortDescription\":\"10% bonus på hotellovernatting\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Bestiller du hotellovernatting gjennom Eika Hoteller, får du alltid 10% bonus på kjøpet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1734,\"vendorName\":\"Eika Hoteller\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010284,\"benefitName\":\"MC NOR - Extended Travel Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00087\",\"externalReferenceName\":\"MCEIKNOR025\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Utvidet reise- og avbestillingsforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får bedre dekning og vilkår enn gjennom standardforsikringen som er inkludert i kortet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}}]},{\"flexibleChildPackageId\":5854,\"accountId\":1026834,\"parentPackageId\":1020254,\"childPackageId\":1020384,\"isDefault\":0,\"cost\":89,\"displayOrderNumber\":3,\"priceDescription\":\"89 kr per måned\",\"insuranceDescription\":null,\"childPackage\":{\"childPackageId\":1020384,\"benefitSelectionTypeId\":null,\"childPackageName\":\"Full pakke\",\"childPackageTypeName\":\"Flexible Child Package\",\"description\":null,\"externalPkgReferenceName\":\"till010\",\"packageNameTranslatedValue\":null,\"descriptionTranslatedValue\":null},\"benefits\":[{\"benefitId\":1010274,\"benefitName\":\"MC NOR - LoungeKey\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00086\",\"externalReferenceName\":\"MCEIKNOR024\",\"benefitType\":{\"benefitTypeId\":12,\"benefitTypeDescription\":\"Travel\"},\"shortDescription\":\"Loungetilgang inkludert 1 gratis besøk i året\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Loungetilgangen gir deg mulighet til å unngå overfylte avgangshaller, og få en mer behagelig reiseopplevelse på flyplasser over hele verden.\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":120,\"vendorName\":\"Collinson Group\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010254,\"benefitName\":\"MC NOR - 1.5% on all x-border transactions\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00084\",\"externalReferenceName\":\"MCEIKNOR022\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"1,5% bonus på alle varekjøp i utenlandsk valuta\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 1,50 kr i bonus for hver 100 kroner du bruker i utlandet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010294,\"benefitName\":\"MC NOR - Rental Car Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00088\",\"externalReferenceName\":\"MCEIKNOR026\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Egenandelsforsikring leiebil\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Forsikring som dekker egenandel ved skade med leiebil\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010244,\"benefitName\":\"MC NOR - 1% on all domestic transactions\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00083\",\"externalReferenceName\":\"MCEIKNOR021\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"1% bonus på alle varekjøp i norske kroner\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 1 kr i bonus for hver 100 kroner du bruker i Norge\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010304,\"benefitName\":\"MC NOR - Purchase & Price match Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00089\",\"externalReferenceName\":\"MCEIKNOR027\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Kjøps- og prisgarantiforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Forsikring som dekker skader ved varer du har kjøpt med kortet, samt dekning av prisdifferanse dersom du finner en vare du har kjøpt med kortet billigere et annet sted\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1744,\"vendorName\":\"Amtrust\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010284,\"benefitName\":\"MC NOR - Extended Travel Insurance\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00087\",\"externalReferenceName\":\"MCEIKNOR025\",\"benefitType\":{\"benefitTypeId\":6,\"benefitTypeDescription\":\"Insurance\"},\"shortDescription\":\"Utvidet reise- og avbestillingsforsikring\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får bedre dekning og vilkår enn gjennom standardforsikringen som er inkludert i kortet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1724,\"vendorName\":\"Eika Forsikring\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010264,\"benefitName\":\"MC NOR - 10% cashback on Eika Hotel\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00085\",\"externalReferenceName\":\"MCEIKNOR023\",\"benefitType\":{\"benefitTypeId\":13,\"benefitTypeDescription\":\"General\"},\"shortDescription\":\"10% bonus på hotellovernatting\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Bestiller du hotellovernatting gjennom Eika Hoteller, får du alltid 10% bonus på kjøpet\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1734,\"vendorName\":\"Eika Hoteller\",\"vendorOrganizationAttribute\":null}},{\"benefitId\":1010214,\"benefitName\":\"MC NOR - 20% on Streaming\",\"benefitNameTranslatedValue\":null,\"linkUrl\":null,\"linkFaq\":null,\"linkTos\":null,\"linkDescription\":null,\"imagePath\":null,\"benefitContractDescription\":null,\"benefitContractDescriptionTranslatedValue\":null,\"benefitReferenceName\":\"MCBEN00080\",\"externalReferenceName\":\"MCEIKNOR018\",\"benefitType\":{\"benefitTypeId\":2,\"benefitTypeDescription\":\"Discount\"},\"shortDescription\":\"20% bonus på strømmetjeneste\",\"shortDescriptionTranslatedValue\":null,\"longDescription\":\"Du får 20% bonus på en av følgende strømmetjenester: Netflix, Spotify, HBO Nordic ...\",\"longDescriptionTranslatedValue\":null,\"benefitQuestions\":[],\"benefitAttributes\":[],\"bundleReferenceName\":[],\"vendor\":{\"vendorId\":1714,\"vendorName\":\"Eika Fordel\",\"vendorOrganizationAttribute\":null}}]}],\"accounts\":[{\"accountId\":1026834,\"accountName\":\"Eika Kredittbank AS - Wholesale\"}],\"organization\":{\"organizationId\":16,\"organizationDesc\":\"AI Nordics\",\"parentOrganizationId\":16,\"minimumUtcOffset\":0,\"maximumUtcOffset\":0,\"client\":[{\"clientId\":1023624,\"clientName\":\"Eika Kredittbank AS\",\"clientBillingDescription\":null,\"wholesaleCancelAllowedFlag\":0,\"clientReferenceNumber\":\"16926\",\"sourceSystem\":\"ALBMBOEUREIKA\",\"effectiveDate\":null,\"expirationDate\":null,\"retentionPeriod\":null,\"clientTimeStamp\":\"2019-08-22T00:00:00\",\"accounts\":[{\"accountId\":1026834,\"clientId\":1023624,\"ccDataSourceFlag\":0,\"clientDataOwnerFlag\":1,\"editProfileFlag\":1,\"accountName\":\"Eika Kredittbank AS - Wholesale\",\"accountUrl\":null,\"accountAdminName\":\"Eika Kredittbank AS - Wholesale\",\"accountPrefix\":null}],\"contacts\":[{\"clientContactId\":3124,\"clientId\":1023624,\"contactFirst\":\"Inger\",\"contactLast\":\"Brogen Hansen\",\"contactMiddle\":null,\"address1\":null,\"address2\":null,\"city\":null,\"state\":null,\"postalCode\":null,\"phone\":null,\"email\":\"IngerBorgen.Hansen@mastercard.com\",\"contactTypeDesc\":\"Client\"},{\"clientContactId\":3134,\"clientId\":1023624,\"contactFirst\":\"Neil\",\"contactLast\":\"Dodson\",\"contactMiddle\":null,\"address1\":null,\"address2\":null,\"city\":null,\"state\":null,\"postalCode\":null,\"phone\":\"00 44 7950 687971\",\"email\":\"Neil.Dodson@mastercard.com\",\"contactTypeDesc\":\"Client\"}],\"partnerClient\":[{\"partnerClientId\":9534,\"partnerId\":314,\"hubClientGroupId\":501,\"dateCreated\":\"2019-08-22T14:36:50\",\"userCreated\":\"TWheller\",\"dateModified\":\"2020-01-02T14:49:29\",\"userModified\":\"TWheller\",\"clientId\":1023624,\"partner\":{\"partnerId\":314,\"partnerName\":\"MasterCard\"},\"hubClientGroup\":{\"hubClientGroupId\":501,\"externalClientGroupId\":\"160900\",\"clientGroupDescription\":\"Eika Kredittbank AS\",\"agentportalBrandCode\":\"AP_Eika\",\"agentportalDomainUrl\":\"?source=1036734\"}}]}]},\"benefitPackages\":[{\"benefitPackageId\":118944,\"benefitId\":1010044,\"flexibleFlag\":\"N\",\"displaySeq\":1,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":118954,\"benefitId\":1010064,\"flexibleFlag\":\"N\",\"displaySeq\":2,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":118964,\"benefitId\":1010094,\"flexibleFlag\":\"N\",\"displaySeq\":3,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":118974,\"benefitId\":1010104,\"flexibleFlag\":\"N\",\"displaySeq\":4,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":118984,\"benefitId\":1010114,\"flexibleFlag\":\"N\",\"displaySeq\":5,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":118994,\"benefitId\":1010124,\"flexibleFlag\":\"N\",\"displaySeq\":6,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119004,\"benefitId\":1010154,\"flexibleFlag\":\"N\",\"displaySeq\":7,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119014,\"benefitId\":1010164,\"flexibleFlag\":\"N\",\"displaySeq\":8,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119024,\"benefitId\":1010054,\"flexibleFlag\":\"N\",\"displaySeq\":9,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119034,\"benefitId\":1010134,\"flexibleFlag\":\"N\",\"displaySeq\":10,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119044,\"benefitId\":1010144,\"flexibleFlag\":\"N\",\"displaySeq\":11,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119054,\"benefitId\":1010074,\"flexibleFlag\":\"N\",\"displaySeq\":12,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"N\",\"activeFlag\":\"Y\"},{\"benefitPackageId\":119064,\"benefitId\":1010174,\"flexibleFlag\":\"N\",\"displaySeq\":13,\"supplierReferenceName\":null,\"isDefaultFlexibleBenefit\":0,\"primaryOnly\":0,\"userpaysFlag\":\"Y\",\"activeFlag\":\"Y\"}]}}";
    }
}