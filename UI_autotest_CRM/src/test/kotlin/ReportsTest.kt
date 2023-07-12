import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.screenshot
import org.openqa.selenium.support.ui.ExpectedConditions
import org.testng.Assert
import org.testng.annotations.Test
import pages.*
import java.lang.Thread.sleep
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReportsTest : BaseTest() {
    @Test
     // Тест авторизации
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(loginBonaCapona)
        loginPage.setValueToPasswordEditBox(passwordBonaCapona)
        loginPage.setValueToPointEditBox(pointBonaCapona)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameBonaCapona.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
        System.err.println("auth passed")
    }

    @Test(dependsOnMethods = ["checkAuthByUserName"]) //Тест-кейс №1: открытие и закрытие списка отчетов
    fun checkLeftMenuNavigationOpenClose(){
        val profilePage = ProfilePage()
        profilePage.leftMenuSingleSelector("Отчеты")
        profilePage.leftSubMenuVisibleCheck()
        profilePage.leftMenuSingleSelector("Отчеты")
        profilePage.leftSubMenuInvisibleCheck()
        System.err.println("left menu passed")
    }

    @Test(dependsOnMethods = ["checkLeftMenuNavigationOpenClose"]) //тест кейс №2 “Звонки” и смена дат
    fun checkCallsReport(){
        val profilePage = ProfilePage()
        val reportsCallsPage = ReportsCallsPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Отчеты", "Звонки")
        reportsCallsPage.clickToOpenDateRange()
        reportsCallsPage.changeStartDate("04/01/2023")
        reportsCallsPage.changeEndDate("04/30/2023")
        reportsCallsPage.clickApplyDateChangeButton()
        sleep(60000)

        // сделать скриншот программно
        // val programScreen: File? = `$`("[class='chart-container']").screenshot()
        // сохранить его через evaluate, поместить в папку в качестве эталона

        val incomeCallsByDay = reportsCallsPage.screenShotMaker()
        val result = incomeCallsByDay?.let {
            commonUtils.matchTwoPictures("/Users/Shared/test/EtalonPictures/IncomeCallsByDayEtalonPic.png",
                it
            )
        }

        //assertEquals(true, result)
    }

    @Test(dependsOnMethods = ["checkCallsReport"]) //тест кейс №3 "Отзывы" и смена дат
    fun checkFeedbackReport() {
        val profilePage = ProfilePage()
        val reviewReportPage = ReviewsReportPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        sleep(10000)
        profilePage.leftMenuItemsSelector("Отчеты", "Отзывы")

        reviewReportPage.clickToOpenDateRange()
        reviewReportPage.changeStartDate("04/01/2023")
        reviewReportPage.changeEndDate("04/02/2023")
        reviewReportPage.clickApplyDateChangeButton()
        sleep(10000)
        val resultName = reviewReportPage.downloadTableDateInPdfFile()
        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
        assertEquals("Отчет по отзывам BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-02.pdf",resultName)
    }

    @Test(dependsOnMethods = ["checkFeedbackReport"])
     //Тест-кейс №4:  “Заказы в заведении”, смена дат и скачивание отчета
    fun checkOrdersInCafeReport () {
        val profilePage = ProfilePage()
        val reportsOrdersInCafePage = ReportsOrdersInCafePage()
        val commonUtils = CommonUtils()

        sleep(10000)
        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        sleep(10000)
        profilePage.leftMenuItemsSelector("Отчеты", "Заказы в заведении")
        sleep(10000)

        reportsOrdersInCafePage.clickToOpenDateRange()
        reportsOrdersInCafePage.changeStartDate("04/01/2023")
        reportsOrdersInCafePage.changeEndDate("04/07/2023")
        reportsOrdersInCafePage.clickApplyDateChangeButton()
        sleep(10000)
        val resultName = reportsOrdersInCafePage.downloadTableDateInPdfFile()
        val result = commonUtils.smartDownload("/")
        assertEquals(true, result)
        assertEquals("Отчет по заказам в заведении BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-07.pdf",resultName)
        val pngFileName: String? = screenshot("my_file_name")
        Configuration.reportsFolder = "reports"
    }

    @Test(dependsOnMethods = ["checkOrdersInCafeReport"])
     //Тест-кейс №5:  “Гостевой WiFi”, смена дат и скачивание отчета
    fun checkReportWifiGuest() {
        val profilePage = ProfilePage()
        val reportsGuestWifiPage = ReportsGuestWifiPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        sleep(10000)
        profilePage.leftMenuItemsSelector("Отчеты", "Гостевой WiFi")
        sleep(10000)
        reportsGuestWifiPage.clickToOpenDateRange()
        reportsGuestWifiPage.changeStartDate("04/01/2023")
        reportsGuestWifiPage.changeEndDate("04/30/2023")
        reportsGuestWifiPage.clickApplyDateChangeButton()
        //Selenide.Wait().until(ExpectedConditions.urlContains("/analytics.wifi/?from=2023-04-01&to=2023-04-30"))
        val resultName = reportsGuestWifiPage.downloadTableDateInPdfFile()
        val result = commonUtils.smartDownload("/")
        //val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
        assertEquals(
            "Отчет по гостевому Wi-Fi BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-30.pdf",
            resultName
        )
    }

    @Test(dependsOnMethods = ["checkReportWifiGuest"])
     //Тест-кейс №6:  “Сводка” и смена дат
    fun checkSummaryReport() {
        val profilePage = ProfilePage()
        val reportsSummaryPage=ReportsSummaryPage()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Сводка")
        sleep(10000)

        reportsSummaryPage.clickToOpenDateRange()
        reportsSummaryPage.changeStartDate("04/01/2023")
        reportsSummaryPage.changeEndDate("04/30/2023")
        reportsSummaryPage.clickApplyDateChangeButton()
        sleep(15000)

        val result = reportsSummaryPage.recountButtonVisible()
        Assert.assertEquals(true, result) //Здесь происходит проверка по наличию кнопки "Пересчитать" внизу страницы

    }

    @Test(dependsOnMethods = ["checkSummaryReport"])
     //Тест-кейс №7:  ”Доставки”, смена дат и скачивание отчета
    fun checkDeliveryReport() {
        val profilePage = ProfilePage()
        val reportsDeliveryPage = ReportsDeliveryPage()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Доставки")
        sleep(10000)

        reportsDeliveryPage.clickToOpenDateRange()
        reportsDeliveryPage.changeStartDate("04/01/2023")
        reportsDeliveryPage.changeEndDate("04/30/2023")
        reportsDeliveryPage.clickApplyDateChangeButton()
       /* val result = reportsDeliveryPage.downloadTableDateInPdfFile()
        assertEquals() - здесь должно быть название доки, с которым сверяться, но по состоянию на 19 мая кнопка скачать неактивна и файл не скачивается - тикет заведен, ждем исправления
*/
    }

    @Test(dependsOnMethods = ["checkDeliveryReport"])
    //Тест-кейс №8:  ”ABC анализ по блюдам”, смена дат и скачивание отчета
    fun checkReportsABCDish() {
        val profilePage = ProfilePage()
        val abcDishPage = ABCDishPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "ABC анализ по блюдам")
        sleep(10000)

        abcDishPage.clickToOpenDateRange()
        abcDishPage.changeStartDate("04/01/2023")
        abcDishPage.changeEndDate("04/07/2023")
        abcDishPage.clickApplyDateChangeButton()
        sleep(10000)
        abcDishPage.buttonDropdownMenuClick()
        abcDishPage.downloadTableDateInXlsFile()
        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
        val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles().get(0).length()
        assertTrue { fileSize in 70040..70045  }
    }

    @Test(dependsOnMethods = ["checkReportsABCDish"])
    //Тест-кейс №10:  ”ABC анализ по гостям”, смена дат и скачивание отчета
    fun checkReportABCGuest() {
        val profilePage = ProfilePage()
        val abcGuestsPage = ABCGuestsPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "ABC анализ по гостям")
        sleep(10000)

        abcGuestsPage.clickToOpenDateRange()
        abcGuestsPage.changeStartDate("04/01/2023")
        abcGuestsPage.changeEndDate("04/30/2023")
        abcGuestsPage.clickApplyDateChangeButton()

        abcGuestsPage.buttonDropdownMenuClick()
        abcGuestsPage.downloadTableDateInXlsFile()
        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
       /* val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
        assertTrue { fileSize in 278004..278009 }*/
        //проблема с разным размером файла, ждём исправления
    }

    @Test(dependsOnMethods = ["checkReportABCGuest"])
    //Тест-кейс №12:  ”Поведение гостей”, смена дат и скачивание отчета
    fun checkReportBehavior() {
        val profilePage = ProfilePage()
        val behaviorReportPage = BehaviorReportPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Поведение гостей")
        sleep(10000)

        behaviorReportPage.clickToOpenDateRange()
        behaviorReportPage.changeStartDate("04/01/2023")
        behaviorReportPage.changeEndDate("04/30/2023")
        behaviorReportPage.clickApplyDateChangeButton()
        behaviorReportPage.buttonDropdownMenuClick()
        /* behaviorReportPage.downloadTableDateInXlsFile()
         val result = commonUtils.smartDownload("/Users/Shared/test/")
         assertEquals(true, result)

         val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
         */
    // по состоянию на 18 мая файл не скачивался. ждем фикса от разработки и добавим в тест
    }

    @Test(dependsOnMethods = ["checkReportBehavior"])
    //Тест-кейс №14:  ”Отзывы после визита”, смена дат и скачивание отчета
    fun checkReviewAfterVisit() {
        val profilePage = ProfilePage()
        val reportsReviewAfterVisitPage = ReportsReviewAfterVisitPage()
        val commonUtils = CommonUtils()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Отзывы после визита")
        sleep(10000)

        reportsReviewAfterVisitPage.clickToOpenDateRange()
        reportsReviewAfterVisitPage.changeStartDate("04/01/2023")
        reportsReviewAfterVisitPage.changeEndDate("04/01/2023")
        reportsReviewAfterVisitPage.clickApplyDateChangeButton()
        sleep(10000)
        reportsReviewAfterVisitPage.buttonDropdownMenuClick()
        val resultName = reportsReviewAfterVisitPage.downloadTableDateInXlsFile()

        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
       val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles().get(0).length()
       // assertEquals(14886, fileSize)
        assertTrue { fileSize in 14885..14887 }
       assertEquals("messages.report_2023-04-01_2023-04-01.xlsx", resultName)
    }

    @Test(dependsOnMethods = ["checkReviewAfterVisit"])
    // Тест-кейс №15:  ”Дни рождения”, логин под новым пользователем, смена дат и скачивание
    fun checkBirthdays() {
        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val commonUtils = CommonUtils()
        val reportsBirthdayPage = ReportsBirthdayPage()

        open("https://cabinet.clientomer.ru")
        loginPage.setValueToLoginEditBox(loginPhaliHinkali)
        loginPage.setValueToPasswordEditBox(passwordPhaliHinkali)
        loginPage.setValueToPointEditBox(pointPhaliHinkali)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNamePhaliHinkali.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Дни рождения")
        sleep(10000)

        reportsBirthdayPage.clickToOpenDateRange()
        reportsBirthdayPage.changeStartDate("04/01/2021")
        reportsBirthdayPage.changeEndDate("04/30/2021")
        reportsBirthdayPage.clickApplyDateChangeButton()
        reportsBirthdayPage.buttonDropdownMenuClick()
        val resultName = reportsBirthdayPage.downloadTableDateInXlsFile()

        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
        assertEquals("birthdays_report_2021-04-01_2021-04-30.xlsx", resultName)

        //val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
        //assertEquals(14896, fileSize)
    }

    @Test(dependsOnMethods = ["checkBirthdays"])
    //Тест-кейс №11:  ”Новый RFM-отчет ”, смена дат, проверка загрузки страницы через заголовок
    fun newRfmReportCheck() {
        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val commonUtils = CommonUtils()
        val reportsNewRFMPage = ReportsNewRFMPage()

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "Новый RFM-отчёт")
        sleep(10000)

        reportsNewRFMPage.buttonDropdownMenuClick()
        reportsNewRFMPage.downloadTableDateInXlsFile()

        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)

        val resultHeader = reportsNewRFMPage.reportNameVisible()
        assertEquals(true, resultHeader)

    }

    @Test(dependsOnMethods = ["newRfmReportCheck"])
    //Тест-кейс №11:  ”RFM-отчет ”, проверка загрузки заголовка
    fun oldRFMReportCheck() {

        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val reportsRFMPage = ReportsRFMPage()

        open("https://cabinet.clientomer.ru")
        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerName.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())


        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        sleep(10000)
        profilePage.leftMenuItemsSelector("Отчеты", "RFM-отчет")
        sleep(30000)

        val resultHeader = reportsRFMPage.reportNameVisible()
        assertEquals(true, resultHeader)

    }

    @Test(dependsOnMethods = ["oldRFMReportCheck"])
    //Тест-кейс №17:  ”Комплементы ”, смена дат и скачивание отчета
    fun complementsReportCheck() {

        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val commonUtils = CommonUtils()
        val complementPage = ComplementPage()

        open("https://cabinet.clientomer.ru")
        loginPage.setValueToLoginEditBox(loginPhaliHinkali)
        loginPage.setValueToPasswordEditBox(passwordPhaliHinkali)
        loginPage.setValueToPointEditBox(pointPhaliHinkali)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNamePhaliHinkali.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        sleep(10000)
        profilePage.leftMenuItemsSelector("Отчеты", "Комплементы")
        sleep(30000)

        complementPage.clickToOpenDateRange()
        complementPage.changeStartDate("04/01/2023")
        complementPage.changeEndDate("04/30/2023")
        complementPage.clickApplyDateChangeButton()
        complementPage.buttonDropdownMenuClick()

        val resultName = complementPage.downloadTableDateInXlsFile()
        sleep(10000)
        val result = commonUtils.smartDownload("/Users/Shared/test/")
        assertEquals(true, result)
        val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()

        assertEquals("complements__2023-04-01_2023-04-30.xlsx", resultName)
        assertTrue{fileSize in 49003 .. 49008}
    }
}
