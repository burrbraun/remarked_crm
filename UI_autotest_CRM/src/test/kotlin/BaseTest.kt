import com.codeborne.selenide.Browsers.CHROME
import com.codeborne.selenide.Configuration.*
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.logevents.SelenideLogger
import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.allure.Allure.step
import io.qameta.allure.Step
import io.qameta.allure.selenide.AllureSelenide
import org.openqa.selenium.WebDriver
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test
import pages.CommonUtils
import pages.LoginPage
import pages.ProfilePage
import kotlin.test.assertEquals
import io.qameta.allure.AllureId
import io.qameta.allure.Feature


open class BaseTest {
    companion object {
        lateinit var driver: WebDriver
    }

    val utils = CommonUtils()

    val login = "pizzalino"
    val loginBonaCapona = "bona-capona20"
    val loginPhaliHinkali = "Phali"
    val loginJoliWooFinegans = "administrator"
    val loginFiesta = "admin"

    val password = utils.encryptionPasswords(System.getenv("PIZZALINO_PASSWORD")) //пароль пиццалино
    val passwordBonaCapona = utils.encryptionPasswords(System.getenv("BONACAPONA_PASSWORD")) //пароль бона капона
    val passwordPhaliHinkali = utils.encryptionPasswords(System.getenv("PHALIHINKALI_PASSWORD")) //пароль пхали хинкали
    val passwordJoliWooFinegans = utils.encryptionPasswords(System.getenv("JOLIWOOFINEGANS_PASSWORD")) //пароль джоливу финеганс
    val passwordFiesta = utils.encryptionPasswords(System.getenv("FIESTA_PASSWORD")) //пароль фиесты

    val point = "600400"
    val pointBonaCapona = "600320"
    val pointPhaliHinkali = "400125"
    val pointJoliWooFinegans = "400115"
    val pointFiesta = "120004"

    val customerName = "Pizzalino"
    val customerNameBonaCapona = "bona capona - общий лк (только боны)"
    val customerNamePhaliHinkali = "Пхали-Хинкали — общий кабинет"
    val customerNameJoliWooFinegans = "Joly Woo and Finnegans - общий кабинет"
    val customerNameFiesta = "ФИЕСТА ПИЦЦА ОБЩИЙ"

    val loginPage = LoginPage()
    val profilePage = ProfilePage()

    @BeforeSuite
    open fun setUp() {

        browser = CHROME
        baseUrl = "https://cabinet.clientomer.ru"
        browserSize = "1024×768"
        driverManagerEnabled = true
        headless = false //включает или выключает интерфейс браузера
        remote = "http://185.189.167.3:4444/wd/hub/" // закомментировать чтобы прогнать локально
        System.err.println("Start WebDriver Initialization")
        webdriverLogsEnabled= false
        //Configuration.fileDownload = FileDownloadMode.FOLDER
        //Configuration.proxyEnabled = false
        WebDriverManager.chromedriver().setup()

        //Create driver object for Chrome
        //driver = ChromeDriver()

        //Navigate to a URL
        System.err.println("trying to up the browser")
        //driver[baseUrl]

        //fileDownload = FOLDER
        //downloadsFolder = "/src/test/resources/testResults/"
        //System.setProperty("selenide.reportsFolder", "/src/test/resources/testResults/")
        open(baseUrl)
        timeout = 300000
        //open(baseUrl)

    }
    open fun setupAllureReports() {
        //SelenideLogger.addListener("AllureSelenide", AllureSelenide())

        // либо для тонкой настройки:
        SelenideLogger.addListener(
            "AllureSelenide", AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        )
    }
}

    class Auth : BaseTest() {
    @Test(priority=1)
    @AllureId("34")
    @Feature("Деление")
    fun checkAuthByUserName() {
        step("Вводим логин")
        loginPage.setValueToLoginEditBox(login)
        step("Вводим пароль")
        loginPage.setValueToPasswordEditBox(password)
        step("вводим нужный номер поинта")
        loginPage.setValueToPointEditBox(point)
        step("нажимаем на кнопку 'войти' ")
        loginPage.loginButtonClick()
        sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerName.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

    @Test(priority=2)
    fun checkAuthByUrl() {
        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        sleep(15000)
        //WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS)
        val actualUrl = WebDriverRunner.getWebDriver().currentUrl
        val expectedUrl = "https://cabinet.clientomer.ru/$point/"

        assertEquals(expectedUrl,actualUrl)
    }

    @Test(priority=3)
    fun checkMainSiteLink() {
        profilePage.clickMainSiteLink()
        val actualUrl = WebDriverRunner.getWebDriver().currentUrl
        val expectedUrl = "https://remarked.ru/"

        assertEquals(expectedUrl,actualUrl)
    }
    }


