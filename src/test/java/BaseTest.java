import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("C:\\Users\\pawan\\OneDrive\\Desktop\\Selenium\\SeFramework\\src\\main\\resources\\Globaldata.properties");
        prop.load(file);
        String browserName = System.getProperty("browser")!=null  ? System.getProperty("browser") : prop.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options=new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));
        }
        else if (browserName.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
        driver.manage().window().maximize();
        return driver;
    }
    @BeforeMethod(alwaysRun = true)
    public LandingPage launchapplication() throws IOException {
        driver=initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(){
        driver.close();
    }

    public List<HashMap<String, String>> getJasonDatatoMap(String filePath) throws IOException {
        //Read Json to String
        String jsonContent= FileUtils.readFileToString(new File
                (filePath), StandardCharsets.UTF_8);
        //String to HashMap Jackson Databind
        ObjectMapper mapper=new ObjectMapper();
        List<HashMap<String,String>> data=
                mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
        return data;
    }



    public List<HashMap<String, String>> getJsondata(String url) throws IOException {

                //Jackson databind
        ObjectMapper mapper =new ObjectMapper();
        return mapper.readValue(FileUtils.readFileToString(new File(url)), new TypeReference<List<HashMap<String,String>>>() {
        });
    }

    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts=(TakesScreenshot)driver;
        File source=ts.getScreenshotAs(OutputType.FILE);
        File file=new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
        FileUtils.copyFile(source,file);
        return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
    }


}
