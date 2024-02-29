import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class StepDefinationImp extends BaseTest{
    public LandingPage landingPage;
    public ProductCatalog productCatalog;
    public ConfirmationPage confirmationPage;
    @Given("I landed on Ecommerce page")
    public void I_landed_on_Ecommerce_page() throws IOException {
        landingPage= launchapplication();
    }
    @Given("^logged in with Username (.+) and Password (.+)$")
    public void logged_in_Username_and_Password(String username, String password){
        productCatalog=landingPage.logInApplication(username,password);
    }
    @When("^I add the Product Name (.+) from Cart$")
    public void i_add_the_product_name_from_cart(String productName) throws InterruptedException {
        List<WebElement> products =productCatalog.getProductList();
        productCatalog.addProductToCart(productName);
    }
    @And("^Checkout (.+) and submit the order$")
    public void Checkout_and_Submit_order(String productname){
        CartPage cartPage = productCatalog.goToCartPage();
        Boolean match=cartPage.verifyProductDisplay(productname);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage =checkoutPage.submitOrder();
    }
    @Then("{string} message is displayed on confirmationPage.")
    public void messageDisplay(String string){
        String confirm = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirm.equalsIgnoreCase(string));
        driver.close();
    }
}
