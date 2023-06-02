import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
public class DeleteTests {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("C:\\SeleniumTestNew\\frontend\\home\\index.html\n");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Should delete the first entry")
    void souldDeleteFirstEntry() {
        final List<WebElement> delBtns = driver.findElements(By.className("delete-button"));
        if(delBtns.size() == 0){
            assertThat("Empty table").isEqualTo("Table with content");
        }
        final String carPlate = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[6]")).getText();
        driver.findElement(By.className("delete-button")).click();
        driver.navigate().refresh();
        assertThat(driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr[2]/td[6]")).getText()).isNotEqualTo(carPlate);
    }

    @Test
    @DisplayName("Should delete the last entry")
    void shouldDeleteLastEntry(){
        final List<WebElement> delBtns = driver.findElements(By.className("delete-button"));
        if(delBtns.size() == 0){
            assertThat("Empty table").isEqualTo("Table with content");
        }
        int tableLen = delBtns.size() + 1;
        final String carPlate = driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr["+ tableLen +"]/td[6]")).getText();
        driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr["+ tableLen +"]/td[6]")).click();
        driver.navigate().refresh();
        tableLen--;
        assertThat(driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr["+ tableLen +"]/td[6]")).getText()).isNotEqualTo(carPlate);
    }

    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    //Esse caso de teste é só um exemplo
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://scl.ifsp.edu.br");
        Thread.sleep(5000);
        driver.quit();
    }
}
