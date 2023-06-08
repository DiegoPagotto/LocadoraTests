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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
    
    @Test
    @DisplayName("Should not be able to register a car with space as attribute")
    void shouldNotBeAbleToRegisterACarWithSpaceAsAttribute(){
        int numberOfCars = getNumbersOfCars();
        goToRegisterPage();
        WebElement brandInput = driver.findElement(By.id("marca"));
        brandInput.sendKeys(" ");

        WebElement modelInput = driver.findElement(By.id("modelo"));
        modelInput.sendKeys(" ");

        WebElement yearInput = driver.findElement(By.id("ano"));
        yearInput.sendKeys("0");

        WebElement colorInput = driver.findElement(By.id("cor"));
        colorInput.sendKeys(" ");

        WebElement plateInput = driver.findElement(By.id("placa"));
        plateInput.sendKeys(" ");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("carrosTable")));


        // nenhum carro novo pode ter sido adicionado
        assertThat(getNumbersOfCars()).isEqualTo(numberOfCars);

    }

    private void goToHomePage() {
        WebElement buttonHome = driver.findElement(By.xpath("/html/body/div/nav/ul/li[1]"));
        buttonHome.click();
    }

    private void goToRegisterPage() {
        WebElement buttonCreate = driver.findElement(By.xpath("/html/body/div/nav/ul/li[2]"));
        buttonCreate.click();
    }

    private int getNumbersOfCars(){
        WebElement table = driver.findElement(By.id("carrosTable"));
        java.util.List<WebElement> rows = table.findElements(By.tagName("tr"));
        return rows.size();
    }



}
