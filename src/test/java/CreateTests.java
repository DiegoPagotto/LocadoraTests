import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CreateTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("C:\\SeleniumTestNew\\frontend\\home\\index.html\n");
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }


    @Test
    @DisplayName("Should navigate to register page when create button is clicked")
    void shouldNavigateToRegisterPageWhenCreateButtonIsClicked(){
        goToRegisterPage();
        assertTrue(driver.getCurrentUrl().endsWith("register/index.html"));
    }

    @Test
    @DisplayName("Should navigate to home page when home button is clicked")
    void shouldNavigateToHomePageWhenHomeButtonIsClicked(){
        goToRegisterPage();
        goToHomePage();
        assertTrue(driver.getCurrentUrl().endsWith("home/index.html"));
    }

    @Test
    @DisplayName("All fields should be empty when opening register page")
    void allFieldsShouldBeEmptyWhenOpeningRegisterPage(){
        goToRegisterPage();
        WebElement carForm = driver.findElement(By.id("carForm"));
        java.util.List<WebElement> inputFields = carForm.findElements(By.tagName("input"));

        for (WebElement field : inputFields) {
            assertThat(field.getAttribute("value")).isEmpty();
        }

    }

    private void goToHomePage() {
        WebElement buttonHome = driver.findElement(By.xpath("/html/body/div/nav/ul/li[1]"));
        buttonHome.click();
    }

    private void goToRegisterPage() {
        WebElement buttonCreate = driver.findElement(By.xpath("/html/body/div/nav/ul/li[2]"));
        buttonCreate.click();
    }



}
