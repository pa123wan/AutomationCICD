import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AbstractComponent {
    WebDriver driver;
    public AbstractComponent(WebDriver driver){
    this.driver=driver;
    PageFactory.initElements(driver,this);
    }

    @FindBy (css="button[routerlink='/dashboard/cart']")
    WebElement cartHeader;

    @FindBy (css=".btn.btn-custom[routerlink='/dashboard/myorders']")
    WebElement orderHeader;

    public CartPage goToCartPage(){
        cartHeader.click();
        CartPage cartPage=new CartPage(driver);
        return cartPage;
    }

    public OrderPage goToOrderPage(){
        orderHeader.click();
        OrderPage orderPage=new OrderPage(driver);
        return orderPage;
    }
    public void waitForElementToAppear(By findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(findBy));
    }

    public void waitForElementToDissapear(WebElement ele) throws InterruptedException {
        Thread.sleep(2000);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
       // wait.until(ExpectedConditions.invisibilityOf(ele));
    }
}
