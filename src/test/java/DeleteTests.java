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
import java.util.Random;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        final List<WebElement> delBtns = driver.findElements(By.className("delete-button"));
        if(delBtns.isEmpty()){
            assertThat("Empty table").isEqualTo("Table with content");
        }
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(delBtns.get(delBtns.size() - 1)));
        button.click();
        driver.navigate().refresh();
        final List<WebElement> tableLenNew = driver.findElements(By.className("delete-button"));
        assertThat(tableLenNew.size()).isLessThan(delBtns.size());
    }

    @Test
    @DisplayName("Should delete random entry from middle")
    void shouldDeleteRandomMiddle(){
        final List<WebElement> delBtns = driver.findElements(By.className("delete-button"));
        if(delBtns.size() < 3){
            assertThat("Table has less than 3 entries").isEqualTo("This test needs 3 or more entries in the table");
        }
        int tableLen = delBtns.size() + 1;
        Random random = new Random();
        int posRemove = random.nextInt(tableLen - 3) + 3;
        driver.findElement(By.xpath("//*[@id=\"carrosTable\"]/tbody/tr["+ posRemove +"]/td[7]/button[2]")).click();
        driver.navigate().refresh();
        final List<WebElement> tableLenNew = driver.findElements(By.className("delete-button"));
        assertThat(tableLenNew.size()).isLessThan(delBtns.size());
    }

    @Test
    @DisplayName("Should delete all entries in the table")
    void shouldDeleteAll(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        final List<WebElement> delBtns = driver.findElements(By.className("delete-button"));
        if(delBtns.isEmpty()){
            assertThat("Empty table").isEqualTo("Table with content");
        }
        WebElement button;
        for(int i = delBtns.size(); i > 0; i--){
            button = wait.until(ExpectedConditions.elementToBeClickable(By.className("delete-button")));
            button.click();
            driver.navigate().refresh();
        }
        final List<WebElement> tableLenNew = driver.findElements(By.className("delete-button"));
        assertThat(tableLenNew).isEmpty();
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
