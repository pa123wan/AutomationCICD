import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest{
    //String productName = "ZARA COAT 3";

    @Test(dataProvider="getData",groups= {"Purchase"})
    public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
    {
        ProductCatalog productCatalog=landingPage.logInApplication(input.get("email"),input.get("password"));
        List<WebElement> products =productCatalog.getProductList();
        productCatalog.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalog.goToCartPage();
        Boolean match=cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        ConfirmationPage confirmationPage =checkoutPage.submitOrder();
        String confirm = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirm.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }
    @Test(dependsOnMethods= {"submitOrder"})
    public void orderHistoryValidation(HashMap<String,String> input){
        ProductCatalog productCatalog=landingPage.logInApplication(input.get("email"),input.get("password"));
        OrderPage orderPage=productCatalog.goToOrderPage();
        Boolean match=orderPage.verifyOrderDisplay(input.get("productName"));
        Assert.assertTrue(match);
    }
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String,String >> data= getJasonDatatoMap(System.getProperty("user.dir")+"//src//test//java//Json//Purchase.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }
}
