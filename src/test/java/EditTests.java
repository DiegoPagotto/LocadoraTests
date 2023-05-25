import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;

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
    @Test
    @DisplayName("Should open edit modal for car selected")
    void shouldOpenEditModalForCarSelected(){
        WebElement column = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[6]"));
        String plate = column.getText();
        WebElement buttonEdit= driver.findElement(By.cssSelector("table#carrosTable .edit-button"));
        buttonEdit.click();
        WebElement inputPlate = driver.findElement(By.id("placa"));
        assertThat(inputPlate.getAttribute("value")).isEqualTo(plate);
    }
    @Test
    @DisplayName("Should display error message and keep edit modal open when saving car without year")
    void shouldDisplayErrorMessageAndKeepEditModalOpenWhenSavingCarWithoutYear() {
        WebElement buttonEdit= driver.findElement(By.cssSelector("table#carrosTable .edit-button"));
        buttonEdit.click();
        WebElement inputYear = driver.findElement(By.id("ano"));
        inputYear.sendKeys("");
        WebElement buttonSave = driver.findElement(By.cssSelector("#editForm input[type='submit']"));
        buttonSave.click();
        assertTrue(driver.findElement(By.id("editModal")).isEnabled());
    }


}
