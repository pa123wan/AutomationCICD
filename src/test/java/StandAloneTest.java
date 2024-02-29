import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) {
        WebDriver driver=new ChromeDriver();
//implicit time of 3 seconds.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
//going to the website.
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage landingPage =new LandingPage(driver);

//login
        driver.findElement(By.id("userEmail")).sendKeys("test001@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Hinwali@123");
        driver.findElement(By.id("login")).click();
//explicit time
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
// checking variable declaration
        String productName="ADIDAS ORIGINAL";
//Product name list
        List<WebElement> productlist= driver.findElements(By.cssSelector(".mb-3"));
//Add go to cart
        WebElement prod= productlist.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".w-10")).click();

//Wait until the msg display in footer
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
// auto reloding invisible
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
// add to cart button click
        driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']")).click();
//Add to cart items list
        List<WebElement> cartProducts =driver.findElements(By.cssSelector("div[class='cartSection'] h3"));
//check if product added to cart is present or not in cart list.
        Boolean match = cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
//checkout button
        driver.findElement(By.cssSelector(".totalRow button")).click();
//Place order page-Select Country India---> Auto suggestive drop down
        Actions a=new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"india").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
        driver.findElement(By.cssSelector(".btnn")).click();
        String msg= driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(msg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}