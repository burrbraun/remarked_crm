import com.codeborne.selenide.Browsers.CHROME
import com.codeborne.selenide.Configuration.*
import com.codeborne.selenide.FileDownloadMode.FOLDER
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.sleep
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import pages.LoginPage
import pages.ProfilePage
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

    open class BaseTest {
    companion object {
        lateinit var driver: WebDriver
    }
    val login = "pizzalino"
    val loginBonaCapona = "bona-capona20"
    val loginPhaliHinkali = "Phali"
    val loginJoliWooFinegans = "administrator"
    val loginFiesta = "admin"

    val password = "" //пароль пиццалино
    val passwordBonaCapona = "" //пароль бона капона
    val passwordPhaliHinkali = "" //пароль пхали хинкали
    val passwordJoliWooFinegans = "" //пароль джоливу финеганс
    val passwordFiesta = "" //пароль фиесты

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


    @BeforeAll
    open fun setUp() {
        browser = CHROME
        baseUrl = "https://cabinet.clientomer.ru"
        browserSize = "2560×1600"
        driverManagerEnabled = true

        fileDownload = FOLDER
        downloadsFolder = "/Users/Shared/test"
        open(baseUrl)
        timeout = 120000
    }


}

    class Auth : BaseTest() {
    @Test
    @Order(1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerName.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

    @Test
    @Order(2)
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

    @Test
    @Order(3)
    fun checkMainSiteLink() {
/*        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        sleep(5000)*/
        profilePage.clickMainSiteLink()
        val actualUrl = WebDriverRunner.getWebDriver().currentUrl
        val expectedUrl = "https://remarked.ru/"

        assertEquals(expectedUrl,actualUrl)

    }

}


