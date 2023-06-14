import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.Random;

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
    @DisplayName("Should have the same amount of delete buttons and table rows")
    void shouldHaveSameButtonsAndRows(){
        int delBtnsAmount = driver.findElements(By.className("delete-button")).size();
        int tableRowsAmount = driver.findElements(By.tagName("tr")).size();
        assertThat(delBtnsAmount).isEqualTo(tableRowsAmount - 1);
    }
    
    @Test
    @DisplayName("Should delete the first entry")
    void souldDeleteFirstEntry() {
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
        if(delBtns.size() < 3)
            Assertions.fail("Not enough entries to test");

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
        WebElement button;
        for(int i = delBtns.size(); i > 0; i--){
            button = wait.until(ExpectedConditions.elementToBeClickable(By.className("delete-button")));
            button.click();
            driver.navigate().refresh();
        }
        final List<WebElement> tableLenNew = driver.findElements(By.className("delete-button"));
        assertThat(tableLenNew).isEmpty();
    }

}
