import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalog extends AbstractComponent {
    WebDriver driver;
    public ProductCatalog(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy (css=".mb-3")
    List<WebElement> products;

    @FindBy (css=".ng-animating")
            WebElement spinner;

    By productsBy =By.cssSelector(".mb-3");
    By addToCart =By.cssSelector(".w-10");
    By toast=By.cssSelector("#toast-container");

    public List<WebElement> getProductList(){
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductName(String productName){
        WebElement prod = getProductList().stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return prod;
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toast);
        waitForElementToDissapear(spinner);
    }
}