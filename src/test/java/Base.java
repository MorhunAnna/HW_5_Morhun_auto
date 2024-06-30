
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public abstract class  Base {

    public WebDriver webDriver;

        @BeforeMethod
        public void initDriver()
        {
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.get("https://prozorro.gov.ua/");
        }

        @AfterMethod
        public void quitDriver()
        {
            webDriver.quit();
        }
}
