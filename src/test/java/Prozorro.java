import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Prozorro extends Base
{
    private WebDriverWait wait;

    @DataProvider(name = "searchConditions")
    public Object[][] searchConditions()
    {
        return new Object[][]
                {
                {"ділянки", "positive"},
                {"закупівля", "positive"},
                {"", "negative"},
                {":?*%", "negative"},
                {"тумби", "positive"}
                };
    }

    @Test(dataProvider = "searchConditions", groups = {"positive", "negative"})
    public void searchTests(String searchConditions, String testType)
    {
        WebElement searchField = webDriver.findElement(By.className("search-text__input"));
        searchField.clear();
        searchField.sendKeys(searchConditions);
        searchField.sendKeys(Keys.ENTER);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        if (testType.equals("positive"))
        {
            List<WebElement> results = webDriver.findElements(By.xpath("//a[@class='item-title__title']"));

            for (WebElement result : results)
            {
                String resultText = result.getText().toLowerCase();
                Assert.assertTrue(resultText.contains(searchConditions), "Results contain the search condition");
            }
        }
        else if (testType.equals("negative"))
        {
            if (searchConditions.equals(""))
            {
                List<WebElement> results = webDriver.findElements(By.xpath("//a[@class='item-title__title']"));
                Assert.assertFalse(results.isEmpty(), "Any results are displayed.");

            }
            else
            {
                WebElement noResultsMessage = webDriver.findElement(By.className("search-result__empty"));
                Assert.assertTrue(noResultsMessage.isDisplayed(), "Expected 'no results message' it displayed");
            }
        }
    }
}