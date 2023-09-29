package ReservesTests

import BaseTest
import io.qameta.allure.Allure
import org.testng.annotations.Test
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.*
import pages.Reserves.reservesPage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals


class CreateReserveTest : BaseTest() {
    val reservesPage = reservesPage()
    val phones = reservesPage.generateRandomPhoneNumbers(100)
    val dtf = DateTimeFormatter.ofPattern("MM.dd.yyyy")
    val today = LocalDateTime.now().plusHours(2)
    val date = dtf.format(today)
//
//    fun roundTime(dt: LocalDateTime = LocalDateTime.now(), roundTo: Long = 60): LocalDateTime {
//        val seconds = dt.truncatedTo(ChronoUnit.SECONDS).toLocalTime().toSecondOfDay()
//        val rounding = (seconds + roundTo / 2) / roundTo * roundTo
//        return dt.truncatedTo(ChronoUnit.DAYS).plusSeconds(rounding.toLong())
//    }
@Test
    fun createReserveTest() {
        Allure.step("Вводим логин")
        loginPage.setValueToLoginEditBox(loginPavelCabinet)
        Allure.step("Вводим пароль")
        loginPage.setValueToPasswordEditBox(passwordPavelCabinet)
        Allure.step("Вводим нужный номер поинта")
        loginPage.setValueToPointEditBox(pointPavelCabinet)
        Allure.step("Нажимаем на кнопку 'войти' ")
        loginPage.loginButtonClick()
    Selenide.sleep(15000)
    val actualUserName = profilePage.checkCustomerNameInReserves()
    assertEquals(customerNameTestCabinet.lowercase().trimEnd(), actualUserName.lowercase().trimEnd())
    System.err.println("auth passed test message")

        // Переход на страницу резервов
        open("https://cabinet.clientomer.ru/555222/reserves/")
        Thread.sleep(3000)
        //println(driver.currentUrl)
        // assert driver.currentUrl=="https://cabinet.clientomer.ru/555222/reserves/"

        val phone = phones.random()

        //driver.findElement(By.cssSelector(".nav-item.open-main-list")).click()
    //profilePage.clickOnReservesListButton()
    val reservesPage = reservesPage()
    reservesPage.buttonToCreateReserve()

    reservesPage.fulfillSurnameInformation("ТестоваяФамилия")
    reservesPage.fulfillName("ТестовоеИмя")
    screenshot("1fio_added_screenshot")
    reservesPage.fulfillPhone("$phone")
    screenshot("2phone_added")
    reservesPage.fulfillDate("$date")
    screenshot("3date_added")
    sleep(3000)
    reservesPage.fulfillTime()
    sleep(3000)
    screenshot("4time_added_screenshot")
    reservesPage.chooseHall()
    sleep(3000)
    screenshot("5table_added_screenshot")
    reservesPage.chooseResponsible()
    sleep(6000)
    screenshot("6responsible_chosen")
    reservesPage.reserveStatusChange()
    screenshot("7reserve_status_changed")
    reservesPage.confirmReserve()
    sleep(3000)
    screenshot("8reserve_done")
//    reservesPage.closeSuccessReserveCreateWindow()
//    screenshot("9success_window_closed")


//        val reserves = driver.findElements(By.cssSelector(".emp_post.text-truncate.text-muted.reserve-phone"))
//        for (item in reserves) {
//            println(item.findElement(By.xpath("../../..")).getAttribute("data-reserve-id"))
//            println(item.text)
//        }

        // btn btn-success waves-effect waves-float waves-light header-site__add-open
        // # Поиск и проверка попадания на главную страницу
        // val titleText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"))
        // if (titleText.text == "PRODUCTS") {
        //     println("Мы попали на главную страницу")
        // } else {
        //     println("Ошибка поиска элемента")
        // }

        //Thread.sleep(20000)

    }
}


