import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {
    WebDriver driver;

    public LandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy (id="userEmail")
    WebElement userEmail;

    @FindBy (id="login")
    WebElement submit;

    @FindBy (id="userPassword")
    WebElement passwordEle;

    @FindBy (css="[class*='flyInOut']")
    WebElement errorMessage;

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }


    public ProductCatalog logInApplication(String email, String password){
        userEmail.sendKeys(email);
        passwordEle.sendKeys(password);
        submit.click();
        ProductCatalog productCatalog = new ProductCatalog(driver);
        return productCatalog;

    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client");
    }
}