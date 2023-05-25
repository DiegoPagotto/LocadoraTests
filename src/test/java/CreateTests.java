import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CreateTests {
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
