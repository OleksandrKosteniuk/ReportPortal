package stepDefs;

import abstractClasses.page.AbstractPage;
import desktop.fragments.BasketPopUp;
import desktop.fragments.GlobalHeader;
import desktop.pages.BasketPage;
import desktop.pages.CheckoutForGuestPage;
import desktop.pages.HomePage;
import desktop.pages.SearchResultPage;
import driver.DriverManager;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleSteps extends AbstractPage {
    
    final static Logger logger = Logger.getLogger(ExampleSteps.class);
    
    //Fragments creation
    private GlobalHeader globalHeader = new GlobalHeader();
    private BasketPopUp basketPopUp = new BasketPopUp();
    
    //Pages creation
    private HomePage homePage = new HomePage();
    private SearchResultPage searchResultPage = new SearchResultPage();
    private BasketPage basketPage = new BasketPage();
    private CheckoutForGuestPage checkoutPageForGuest = new CheckoutForGuestPage();

    @Given("I am an anonymous customer with clear cookies")
    public void cleanCookiesInTheBrowser() {
        DriverManager.getDriver().manage().deleteAllCookies();
        logger.error("Guest opens Browser with clear cookies");
    }
    
    @When("I open the Home page")
    public void openHomePage() {
        homePage.openWebsiteUrl(homePage.getPageUrl());
        wait.until(ExpectedConditions.urlContains(homePage.getPageUrl()));
        assertThat(DriverManager.getDriver().getCurrentUrl().contains(homePage.getPageUrl()))
                .overridingErrorMessage("Home page is not opened")
                .isTrue();
    }

    @When("I search for {string}")
    public void searchByBookTitle(String keywordForSearching) {
        globalHeader.fillInKeywordForSearching(keywordForSearching);
        globalHeader.clickOnTheSearchButton();
    }

    @When("I am redirected to a Search Result page")
    public void userIsRedirectedToSearchResultPage() {
        wait.until(ExpectedConditions.urlContains(searchResultPage.getPageUrl()));
        assertThat(DriverManager.getDriver().getCurrentUrl().contains(searchResultPage.getPageUrl()))
                .overridingErrorMessage("User is not redirected to the Search Result page")
                .isTrue();
    }

    @When("Search results contain the following products")
    public void searchResultPageContainsTheFollowingProducts(List<String> expectedBookTitles) {
        assertThat(searchResultPage.isBooksArePresentOnTheSearchResultPage(expectedBookTitles, searchResultPage.findBookTitlesByXpath()))
        .overridingErrorMessage("Search Result page does not contain any expected Book title")
                .isTrue();
    }

    @When("I apply the following search filters")
    public void applyTheFollowingSearchFilters(Map<String, String> refinementFilters) {
        searchResultPage.selectRefinementFilters(refinementFilters);
    }

    @When("Search results contain only the following products")
    public void searchResultPageContainsOnlyTheExpectedBookTitles(List<String> expectedBookTitles) {
        assertThat(searchResultPage.areOnlyExpectedBookTitlesDisplayedOnTheSearchResultPage(expectedBookTitles,searchResultPage.findBookTitlesByXpath()))
        .overridingErrorMessage("Search Result page does not contain the expected Book Titles")
                .isTrue();
    }

    @When("I click Add to basket button for product with name Thinking in Java")
    public void clickAddToBasketButtonBelowTheThinkingInJava() {
        searchResultPage.clickOnTheAddToBasketButtonBelowTheThinkingInJava();
    }

    @When("I select Basket Checkout button in basket pop-up")
    public void clickOnTheBasketCheckoutButtonInTheBasketPopUp() {
        basketPopUp.clickOnTheBasketCheckoutButton();
    }

    @When("I am redirected to the Basket page")
    public void userIsRedirectedToBasketPage() {
        wait.until(ExpectedConditions.urlContains(basketPage.getPageUrl()));
        assertThat(DriverManager.getDriver().getCurrentUrl().contains(basketPage.getPageUrl()))
                .overridingErrorMessage("User is not redirected to the Basket page")
                .isTrue();
    }

    @When("Basket order summary is as following:")
    public void basketOrderSummaryIsAsFollowing(@Transpose Map<String,String> expectedValuesInTheOrderSummaryComponent) {
        assertThat(basketPage.getDeliveryCost())
                .overridingErrorMessage("Delivery cost does not equal to expected value")
                .isEqualTo(expectedValuesInTheOrderSummaryComponent.get(basketPage.getDeliveryCostTitle()));

        assertThat(basketPage.getOrderTotal())
                .overridingErrorMessage("Order total does not equal to expected value")
                .isEqualTo(expectedValuesInTheOrderSummaryComponent.get(basketPage.getTotalTitle()));
    }

    @When("I click Checkout button on Basket page")
    public void clickONTheCheckoutButtonOnTheBasketPage() {
        basketPage.clickOnTheCheckoutButton();
    }

    @When("I am redirected to the Checkout page")
    public void userIsRedirectedToTheCheckoutPage() {
        wait.until(ExpectedConditions.urlContains(checkoutPageForGuest.getPageUrl()));
        assertThat(DriverManager.getDriver().getCurrentUrl().contains(checkoutPageForGuest.getPageUrl()))
                .overridingErrorMessage("User is not redirected to the Checkout for Guest page")
                .isTrue();
    }

    @When("I click Continue to payment button")
    public void ClickBuyNowButton() {
        checkoutPageForGuest.clickOnTheBuyNowButton();
    }

    @When("the following validation error messages are displayed on Delivery Address form:")
    public void areTheFollowingErrorMessagesDisplayedOnDeliveryAddressForm(Map<String,String> expectedErrorMessagesOnTheDeliveryAddressForm) {
        assertThat(checkoutPageForGuest.getEmailAddressActualErrorMessage())
                .overridingErrorMessage("Email Address error message is incorrect")
                .isEqualTo(expectedErrorMessagesOnTheDeliveryAddressForm.get(checkoutPageForGuest.getEmailAddressExpectedErrorMessageKey()));

        assertThat(checkoutPageForGuest.getFullNameActualErrorMessage())
                .overridingErrorMessage("Full name error message is incorrect")
                .isEqualTo(expectedErrorMessagesOnTheDeliveryAddressForm.get(checkoutPageForGuest.getFullNameExpectedErrorMessageKey()));

        assertThat(checkoutPageForGuest.getAddressLine1ActualErrorMessage())
                .overridingErrorMessage("Address line 1 error message is incorrect")
                .isEqualTo(expectedErrorMessagesOnTheDeliveryAddressForm.get(checkoutPageForGuest.getAddressLine1ExpectedErrorMessageKey()));

        assertThat(checkoutPageForGuest.getTownCityActualErrorMessage())
                .overridingErrorMessage("Town/city error message is incorrect")
                .isEqualTo(expectedErrorMessagesOnTheDeliveryAddressForm.get(checkoutPageForGuest.getTownCityExpectedErrorMessageKey()));

        assertThat(checkoutPageForGuest.getPostcodeZipActualErrorMessage())
                .overridingErrorMessage("Postalcode/Zip error message is incorrect")
                .isEqualTo(expectedErrorMessagesOnTheDeliveryAddressForm.get(checkoutPageForGuest.getPostcodeZipExpectedErrorMessageKey()));
    }

    @When("the following validation error messages are displayed on Payment form:")
    public void areTheFollowingErrorMessagesDisplayedOnPaymentForm(List<String> expectedErrorMessagesOnThePaymentForm) {
        assertThat(checkoutPageForGuest.getCardNumberErrorMessage().contains(expectedErrorMessagesOnThePaymentForm.get(0)))
                .overridingErrorMessage("Card Number error message is incorrect")
                .isTrue();

        assertThat(checkoutPageForGuest.getExpiryDateErrorMessage().contains(expectedErrorMessagesOnThePaymentForm.get(1)))
                .overridingErrorMessage("Expiry Date error message is incorrect")
                .isTrue();

        assertThat(checkoutPageForGuest.getCVVErrorMessage().contains(expectedErrorMessagesOnThePaymentForm.get(2)))
                .overridingErrorMessage("CVV error message is incorrect")
                .isTrue();
    }

    @When("Checkout order summary is as following:")
    public void checkoutOrderSummaryIsAsFollowing(@Transpose Map<String,String> expectedValuesInTheCheckoutSummaryComponent) {
        assertThat(checkoutPageForGuest.getSubTotalOfTheCheckoutSummaryComponent())
                .overridingErrorMessage("Sub-total does not equal to expected value in the Checkout Summary Component")
                .isEqualTo(expectedValuesInTheCheckoutSummaryComponent.get(checkoutPageForGuest.getSubTotalOfTheCheckoutSummaryComponentKey()));

        assertThat(checkoutPageForGuest.getDeliveryOfTheCheckoutSummaryComponent())
                .overridingErrorMessage("Delivery does not equal to expected value in the Checkout Summary Component")
                .isEqualTo(expectedValuesInTheCheckoutSummaryComponent.get(checkoutPageForGuest.getDeliveryOfTheCheckoutSummaryComponentKey()));
        
        assertThat(checkoutPageForGuest.getVATOfTheCheckoutSummaryComponent())
                .overridingErrorMessage("VAT does not equal to expected value in the Checkout Summary Component")
                .isEqualTo(expectedValuesInTheCheckoutSummaryComponent.get(checkoutPageForGuest.getVATOfTheCheckoutSummaryComponentKey()));

        assertThat(checkoutPageForGuest.getTotalOfTheCheckoutSummaryComponent())
                .overridingErrorMessage("Total does not equal to expected value in the Checkout Summary Component")
                .isEqualTo(expectedValuesInTheCheckoutSummaryComponent.get(checkoutPageForGuest.getTotalOfTheCheckoutSummaryComponentKey()));
    }

    @When("I checkout as a new customer with email {string}")
    public void fillInEmailAddressOnTheCheckout(String email) {
        checkoutPageForGuest.fillInEmailAddressOnTheCheckout(email);
    }

    @When("I fill delivery address information manually:")
    public void fillInDeliveryAddressInformation(@Transpose Map<String,String> deliveryAddressInformation) {
        checkoutPageForGuest.fillInDeliveryAddressInformation(deliveryAddressInformation);
    }

    @When("there is no validation error messages displayed on Delivery Address form")
    public void areValidationErrorMessagesDisplayedOnDeliveryAddressForm() {
        assertThat(checkoutPageForGuest.getEmailAddressActualErrorMessage().isEmpty())
                .overridingErrorMessage("Email Address error message is displayed")
                .isTrue();

        assertThat(checkoutPageForGuest.getFullNameActualErrorMessage().isEmpty())
                .overridingErrorMessage("Full name error message is displayed")
                .isTrue();

        assertThat(checkoutPageForGuest.getAddressLine1ActualErrorMessage().isEmpty())
                .overridingErrorMessage("Address line 1 error message is displayed")
                .isTrue();

        assertThat(checkoutPageForGuest.getTownCityActualErrorMessage().isEmpty())
                .overridingErrorMessage("Town/city error message is incorrect")
                .isTrue();

        assertThat(checkoutPageForGuest.getPostcodeZipActualErrorMessage().isEmpty())
                .overridingErrorMessage("Postalcode/Zip error message is incorrect")
                .isTrue();
    }

    @When("I enter my card details")
    public void fillInCardDetailsInThePaymentArea(Map<String,String> creditCardData) {
        checkoutPageForGuest.fillInCardDetailsInThePaymentArea(creditCardData);
    }
}
