import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class SmpTest {
    static WebDriver driver;

    // Инициализация
    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    // Тестируем добавление карточки в избранное, делаем проверку на то, что карточка добавилась
    @Test
    public void addObject() throws InterruptedException {
        login();

        // Кликаем по кнопке добавления страницы в избранное
        click("//div[@id='gwt-debug-favorite']");
        Thread.sleep(500);

        // Подтверждаем действие
        click("//div[@id='gwt-debug-apply']");
        Thread.sleep(500);

        // Открываем меню избранного
        click("//div[@id='gwt-debug-c5af86c7-6e4d-a611-55f9-d3fc8dcc236c']/div");
        Thread.sleep(500);

        // Открываем редактирование избранного
        click("//span[@id='gwt-debug-editFavorites']");
        Thread.sleep(500);

        // Проверяем, что карточка добавлена в избранное
        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Студент \"Маскалев Глеб\"/Карточка сотрудника')]"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "Студент \"Маскалев Глеб\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("Студент \"Маскалев Глеб\"/Карточка сотрудника", textElement, msg);
        Thread.sleep(500);

        // Кликаем на удаление карточки
        click("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        Thread.sleep(500);

        // Подтверждаем действие
        click("//div[@id='gwt-debug-yes']");
        Thread.sleep(500);

        // Сохраняем
        click("//div[@id='gwt-debug-apply']");
        Thread.sleep(500);

        // Закрываем меню избранного
        click("//div[6]/div/div[2]/div");
        Thread.sleep(500);

        // Выходим из аккаунта
        click("//a[contains(text(),'Выйти')]");
    }

    // Тестируем удаление карточки из избранного, проверяем в конце метода, что объекта с карточкой нет в избранном
    @Test
    public void deleteObject() throws InterruptedException {
        login();

        // Кликаем по кнопке добавления страницы в избранное
        click("//div[@id='gwt-debug-favorite']");
        Thread.sleep(500);

        // Подтверждаем действие
        click("//div[@id='gwt-debug-apply']");
        Thread.sleep(500);

        // Переходим в раздел избранного
        click("//div[@id='gwt-debug-c5af86c7-6e4d-a611-55f9-d3fc8dcc236c']/div");
        Thread.sleep(500);

        // Кликаем по кнопке "Редактирование избранного"
        click("//span[@id='gwt-debug-editFavorites']");
        Thread.sleep(500);

        // Нажимаем на иконку удаления страницы
        click("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span");
        Thread.sleep(500);

        // Подтверждаем удаление страницы из избранного
        click("//div[@id='gwt-debug-yes']");
        Thread.sleep(500);

        // Проверяем, что элемент удален
        boolean isElementAbsent = driver.findElements(By.xpath("//a[contains(text(),'Студент \"Маскалев Глеб\"/Карточка сотрудника')]")).isEmpty();
        Assertions.assertTrue(isElementAbsent, "Элемент всё ещё присутствует в избранном после удаления!");
        Thread.sleep(500);

        // Сохраняем
        click("//div[@id='gwt-debug-apply']");
        Thread.sleep(500);

        // Закрываем меню избранного
        click("//div[6]/div/div[2]/div");
        Thread.sleep(500);

        // Выходим из аккаунта
        click("//a[contains(text(),'Выйти')]");
    }

    // Входим в аккаунт
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

    // Ожидание элемента
    private WebElement waitElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return element;
    }

    // Клик по элементу
    private void click(String xpath) {
        waitElement(xpath).click();
    }

    // Завершаем тест
    @AfterAll
    public static void close() {
        driver.close();
    }
}
