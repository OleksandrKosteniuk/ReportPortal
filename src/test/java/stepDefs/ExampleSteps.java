package stepDefs;

import abstractClasses.page.AbstractPage;
import desktop.fragments.BasketPopUp;
import desktop.fragments.GlobalHeader;
import desktop.pages.CheckoutForGuestPage;
import desktop.pages.HomePage;
import desktop.pages.SearchResultPage;
import desktop.pages.BasketPage;
import driver.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleSteps extends AbstractPage {

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

    @When("I click Add to basket button for product with name {string}")
    public void clickAddToBasketButtonBelowParticularBookTitle(String bookTitle) {

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
    public void basketOrderSummaryIsAsFollowing(Map<String,String> expectedValuesInTheOrderSummaryComponent) {
        assertThat(expectedValuesInTheOrderSummaryComponent.get(basketPage.getDeliveryCostKeyOfTheSummaryComponent()).compareToIgnoreCase(basketPage.getValueByDeliveryCostKeyOfTheSummaryComponent()))
                .overridingErrorMessage("Delivery cost does not equal to expected value")
                .isTrue();
        expectedValuesInTheOrderSummaryComponent.get(basketPage.getTotalKeyOfTheSummaryComponent()).compareToIgnoreCase(basketPage.getValueByTotalKeyOfTheSummaryComponent())
                .overridingErrorMessage("Total does not equal to expected value")
                .isTrue();
    }

    @When("I click Checkout button on Basket page")
    public void clickONTheCheckoutButtonOnTheBasketPage() {
        basketPage.clickOnTheSearchButton();
    }

    @When("I am redirected to the Checkout page")
    public void userIsRedirectedToTheCheckoutPage() {
        wait.until(ExpectedConditions.urlContains(checkoutPageForGuest.getPageUrl()));
        assertThat(DriverManager.getDriver().getCurrentUrl().contains(checkoutPageForGuest.getPageUrl()))
                .overridingErrorMessage("User is not redirected to the Checkout for Guest page")
                .isTrue();
    }

    @When("I click Buy now button")
    public void ClickBuyNowButton() {
        checkoutPageForGuest.clickOnThePayNowButton();
    }

    @When("the following validation error messages are displayed on Delivery Address form:")
    public boolean areTheFollowingErrorMessagesDisplayedOnDeliveryAddressForm(Map<String,String> expectedErrorMessagesOnTheDeliveryAddressForm) {
    }

    @When("the following validation error messages are displayed on {string} form:")
    public void areTheFollowingErrorMessagesDisplayedOnPaymentForm(List<String> errorMessagesOnThePaymentForm) {
    }
}
