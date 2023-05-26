import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


public class EditTests {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("C:\\SeleniumTestNew\\frontend\\home\\index.html\n");
        WebElement buttonEdit= driver.findElement(By.cssSelector("table#carrosTable .edit-button"));
        buttonEdit.click();
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    @Test
    @DisplayName("Should open edit modal")
    void shouldOpenEditModal(){
        assertTrue(driver.findElement(By.id("editModal")).isEnabled());
    }
    @Test
    @DisplayName("Should open edit modal for car selected")
    void shouldOpenEditModalForCarSelected(){
        WebElement column = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[6]"));
        String plate = column.getText();
        WebElement inputPlate = driver.findElement(By.id("placa"));
        assertThat(inputPlate.getAttribute("value")).isEqualTo(plate);
    }
    @Test
    @DisplayName("Should display error message and keep edit modal open when saving car without year")
    void shouldDisplayErrorMessageAndKeepEditModalOpenWhenSavingCarWithoutYear() {
        WebElement inputYear = driver.findElement(By.id("ano"));
        inputYear.sendKeys("");
        WebElement buttonSave = driver.findElement(By.cssSelector("#editForm input[type='submit']"));
        buttonSave.click();
        assertTrue(driver.findElement(By.id("editModal")).isEnabled());
    }
    @Test
    @DisplayName("Should edit car and update table")
    void shouldEditCarAndUpdateTable(){
        WebElement inputColor = driver.findElement(By.id("cor"));
        inputColor.clear();
        inputColor.sendKeys("Amarelo");
        WebElement buttonSave = driver.findElement(By.cssSelector("#editForm input[type='submit']"));
        buttonSave.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(buttonSave));
        WebElement column = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[5]"));
        assertThat(column.getText()).isEqualTo("Amarelo");
    }
    @Test
    @DisplayName("Should closed edit modal when click on close button")
    void shouldClosedEditModalWhenClickOnCloseButton(){
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(700))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(By.className("close")))
                .click();
        assertThat(driver.findElement(By.id("editModal")).isDisplayed()).isFalse();
    }
    @Test
    @DisplayName("Should closed edit modal and not edit car after filling the form")
    void shouldClosedEditModalAndNotEditCarAfterFillingTheForm(){
        WebElement inputColor = driver.findElement(By.id("cor"));
        inputColor.clear();
        inputColor.sendKeys("Azul del rey");
        WebElement inputYear = driver.findElement(By.id("ano"));
        inputYear.clear();
        inputYear.sendKeys("2002");

        WebElement buttonClose = driver.findElement(By.className("close"));
        buttonClose.click();
        WebElement columnColor = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[5]"));
        WebElement columnYear = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[4]"));

        assertThat(columnColor.getText()).isNotEqualTo("Azul del rey");
        assertThat(columnYear.getText()).isNotEqualTo("2002");
    }

}
