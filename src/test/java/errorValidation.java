import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import java.util.List;

public class errorValidation extends BaseTest{

    @Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
    public void LoginErrorValidation(){
        landingPage.logInApplication("test001@gmail.com", "Hinwali@123");
        //Assert.assertEquals("Login Successfully", landingPage.getErrorMessage());
    }

    @Test
        public void productErrorValidation() throws InterruptedException {
            String productName="ADIDAS ORIGINAL";
            ProductCatalog productCatalog=landingPage.logInApplication("test001@gmail.com", "Hinwali@123");
            List<WebElement> products =productCatalog.getProductList();
            productCatalog.addProductToCart(productName);
            CartPage cartPage = productCatalog.goToCartPage();
            Boolean match=cartPage.verifyProductDisplay(productName);
            Assert.assertTrue(match);
        }
}
