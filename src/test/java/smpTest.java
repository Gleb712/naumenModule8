import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class smpTest {
    static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test
    @Order(1)
    public void addObject() {
        login();

        // Кликаем по кнопке добавления страницы в избранное
        click("//div[@id='gwt-debug-favorite']");

        // Подтверждаем действие
        click("//div[@id='gwt-debug-apply']");

        // Переходим в раздел Autotesting
        click("//a[contains(text(),'Autotesting')]");

        // Кликаем на объект TestObject и проверяем что поле название имеет тип данных text
        WebElement element = driver.findElement(By.xpath("//div[3]/div/div/div/div/div[3]/div/div[8]/div[6]/table/tbody/tr/td[2]/a/div"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "TestObject", textElement);
        Assertions.assertEquals("TestObject", textElement, msg);

        // Выходим из аккаунта
        click("//a[contains(text(),'Выйти')]");
    }

    @Test
    @Order(2)
    public void deleteObject() {
        login();

        // Переходим в раздел избранного
        click("//div[@id='gwt-debug-c5af86c7-6e4d-a611-55f9-d3fc8dcc236c']");

        // Кликаем по кнопке "Редактирование избранного"
        click("//span[@id='gwt-debug-editFavorites']");

        // Нажимаем на иконку удаления страницы
        click("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");

        // Подтверждаем удаление страницы из избранного
        click("//div[@id='gwt-debug-yes']");

        // Сохраняем
        click("//div[@id='gwt-debug-apply']");

        // Выходим из аккаунта
        click("//a[contains(text(),'Выйти')]");
    }

    private void login() {
        // Открываем сайт
        driver.get("http://5.181.254.246:8080/sd/");

        // Вводим логин
        driver.findElement(By.id("username")).sendKeys("user25");

        // Вводим пароль
        driver.findElement(By.id("password")).sendKeys("Gleb!17062004228");

        // Кликаем на подтверждение
        click("//input[@id='submit-button']");
    }

    public WebElement waitElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return element;
    }

    public void click(String xpath) {
        waitElement(xpath).click();
    }

    @AfterAll
    public static void close() {
        driver.close();
    }
}
