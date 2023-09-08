import com.codeborne.selenide.Selenide
import io.qameta.allure.Allure
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.testng.Assert
import org.testng.annotations.Test
import pages.Sources.WifiAuthSourcePage
import kotlin.test.assertEquals

@Epic("Проверка доступности страниц раздела Источники в меню CRM")
class ResourcesCRMTest : BaseTest() {

    @Test
    @Description("Авторизация пользователя")
    fun authToTheSystem() {
        Allure.step("Вводим логин")
        loginPage.setValueToLoginEditBox(loginFiesta)
        Allure.step("Вводим пароль")
        loginPage.setValueToPasswordEditBox(passwordFiesta)
        Allure.step("Вводим нужный номер поинта")
        loginPage.setValueToPointEditBox(pointFiesta)
        Allure.step("Нажимаем на кнопку 'войти' ")
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName = profilePage.checkCustomerName()
        assertEquals(customerNameFiesta.lowercase().trimEnd(), actualUserName.lowercase().trimEnd())
        System.err.println("auth passed test message")
    }
    @Test(dependsOnMethods = ["authToTheSystem"])
    @Description("Проверка загрузки источника wi-fi авторизации и отображения внизу страницы ссылки для перехода на главный сайт")
    fun wifiAuthSourceTest() {
        val wifiAuthSourcePage = WifiAuthSourcePage()
        Allure.step("Перейти на страницу с Wifi-авторизациями")
        Selenide.open("https://cabinet.clientomer.ru/$pointFiesta/user.socnet.profile.phones/")
        Thread.sleep(10000)
        Allure.step("Открыть календарь для смены дат")
        wifiAuthSourcePage.clickToOpenDateRange()
        Allure.step("Установить дату начала периода 04/01/2023")
        wifiAuthSourcePage.changeStartDate("04/01/2023")
        Allure.step("Установить дату конца периода 04/02/2023")
        wifiAuthSourcePage.changeEndDate("04/02/2023")
        Allure.step("Подтвердить выбор дат нажатием на 'применить'")
        wifiAuthSourcePage.clickApplyDateChangeButton()
        Thread.sleep(10000)
        Allure.step("Убедиться, что кнопка перехода на главный сайт reMarked внизу видна")
        val result = wifiAuthSourcePage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)

    }

}