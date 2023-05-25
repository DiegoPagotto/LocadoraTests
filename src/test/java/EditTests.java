import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class EditTests {
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
    @DisplayName("Should open edit modal")
    void shouldOpenEditModal(){
        WebElement buttonEdit= driver.findElement(By.cssSelector("table#carrosTable .edit-button"));
        buttonEdit.click();
        assertTrue(driver.findElement(By.id("editModal")).isEnabled());
    }

}
