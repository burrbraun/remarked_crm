import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.screenshot
import io.qameta.allure.Allure.step
import io.qameta.allure.Attachment
import io.qameta.allure.Description
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.Assert
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test
import pages.*
import java.io.File
import java.io.IOException
import java.lang.Thread.sleep
import java.util.*
import kotlin.test.assertEquals


class ReportsTest : BaseTest() {
    @Test
    @Description("Проверка работы сценария авторизации пользователя на сайте")
     // Тест авторизации
    fun checkAuthByUserName() {
        step("Вводим логин")
        loginPage.setValueToLoginEditBox(loginBonaCapona)
        step("Вводим пароль")
        loginPage.setValueToPasswordEditBox(passwordBonaCapona)
        step("Вводим нужный номер поинта")
        loginPage.setValueToPointEditBox(pointBonaCapona)
        step("Нажимаем на кнопку 'войти' ")
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameBonaCapona.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
        System.err.println("auth passed")
        @Attachment(value = "Screenshooot", type = "image/png")
        fun saveAllureScreenshot(s: String): ByteArray {
            return (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        }
    }

    @Test(dependsOnMethods = ["checkAuthByUserName"]) //Тест-кейс №1: открытие и закрытие списка отчетов
    @Description("Открытие и закрытие списка отчетов в левом боковом меню")
    fun checkLeftMenuNavigationOpenClose(){
        val profilePage = ProfilePage()
        step("Нажать на отчеты в левом меню")
        profilePage.leftMenuSingleSelector("Отчеты")
        step("Убедиться, что видим появившееся подменю")
        profilePage.leftSubMenuVisibleCheck()
        step("Сделать скриншот для проверки")
        val pngFileName: String? = screenshot("checkLeftMenuNavigationOpenClose")
        step("Повторно нажать на отчеты чтобы закрыть раскрывшийся список")
        profilePage.leftMenuSingleSelector("Отчеты")
        step("Убедиться, что список закрылся и подменю больше не видно")
        profilePage.leftSubMenuInvisibleCheck()
        System.err.println("left menu passed")
    }

//    @Test(dependsOnMethods = ["checkLeftMenuNavigationOpenClose"]) //тест кейс №2 “Звонки” и смена дат
//    fun checkCallsReport(){
//        val profilePage = ProfilePage()
//        val reportsCallsPage = ReportsCallsPage()
//        val commonUtils = CommonUtils()
//
//        profilePage.leftMenuItemsSelector("Отчеты", "Звонки")
//        reportsCallsPage.clickToOpenDateRange()
//        reportsCallsPage.changeStartDate("04/01/2023")
//        reportsCallsPage.changeEndDate("04/30/2023")
//        reportsCallsPage.clickApplyDateChangeButton()
//        sleep(60000)
//
//        // сделать скриншот программно
//        // val programScreen: File? = `$`("[class='chart-container']").screenshot()
//        // сохранить его через evaluate, поместить в папку в качестве эталона
//
//        val incomeCallsByDay = reportsCallsPage.screenShotMaker()
//        val result = incomeCallsByDay?.let {
//            commonUtils.matchTwoPictures("/UI_autotest_CRM/src/test/resources/IncomeCallsByDayEtalonPic.png",
//                it
//            )
//        }
//
//        //assertEquals(true, result)
//    }

    @Test(dependsOnMethods = ["checkLeftMenuNavigationOpenClose"]) //тест кейс №3 "Отзывы" и смена дат
    @Description("Открытие отчета с отзывами, смена дат и проверка загрузки кнопки перехода на основной сайт внизу")
    fun checkFeedbackReport() {
        val profilePage = ProfilePage()
        val reviewReportPage = ReviewsReportPage()
        val commonUtils = CommonUtils()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        sleep(10000)
//        profilePage.leftMenuItemsSelector( "Сегменты")
//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        sleep(10000)
//        profilePage.leftMenuItemsSelector( "Отзывы")
        step("Перейти на страницу с отзывами")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/analytics.reviews/")
        sleep(10000)
        step("Открыть календарь для смены дат")
        reviewReportPage.clickToOpenDateRange()
        step("Установить дату начала периода 04/01/2023")
        reviewReportPage.changeStartDate("04/01/2023")
        step("Установить дату конца периода 04/02/2023")
        reviewReportPage.changeEndDate("04/02/2023")
        step("Подтвердить выбор дат нажатием на 'применить'")
        reviewReportPage.clickApplyDateChangeButton()
        sleep(10000)
        step("Убедиться, что кнопка перехода на главный сайт reMarked внизу видна")
        val result = reviewReportPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
//        val resultName = reviewReportPage.downloadTableDateInPdfFile()
//        val result = commonUtils.smartDownload("/src/test/resources/testResults/")
//        assertEquals(true, result)
//        assertEquals("Отчет по отзывам BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-02.pdf",resultName)
    }

//    @Test(dependsOnMethods = ["checkFeedbackReport"])
//     //Тест-кейс №4:  “Заказы в заведении”, смена дат и скачивание отчета
//    fun checkOrdersInCafeReport () {
//        val profilePage = ProfilePage()
//        val reportsOrdersInCafePage = ReportsOrdersInCafePage()
//        val commonUtils = CommonUtils()
//        val reportHeader = Selenide.`$`("[class='page-header']").scrollIntoView(true)
//        reportHeader.shouldBe(Condition.exist)

//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Реклама")
//        sleep(10000)
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Отчеты")
//        sleep(10000)
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Заказы в заведении")
//        sleep(10000)

//        open("https://cabinet.clientomer.ru/$pointBonaCapona/hall.sales.report/")
//        sleep(50000)
//        reportsOrdersInCafePage.clickToOpenDateRange()
//        reportsOrdersInCafePage.changeStartDate("04/01/2023")
//        reportsOrdersInCafePage.changeEndDate("04/07/2023")
//        reportsOrdersInCafePage.clickApplyDateChangeButton()
//        sleep(10000)
//        val result = reportsOrdersInCafePage.mainSiteButtonVisible()
//        Assert.assertEquals(true, result)
//        val resultName = reportsOrdersInCafePage.downloadTableDateInPdfFile()
//        val result = commonUtils.smartDownload("/src/test/resources/testResults/")
//        assertEquals(true, result)
//        assertEquals("Отчет по заказам в заведении BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-07.pdf",resultName)
//        val pngFileName: String? = screenshot("my_file_name")
//        Configuration.reportsFolder = "reports"
//    }

    @Test(dependsOnMethods = ["checkFeedbackReport"])
    @Description("Тест на проверку загрузки отчета Гостевой WiFi")
     //Тест-кейс №5:  “Гостевой WiFi”, смена дат и скачивание отчета
    fun checkReportWifiGuest() {
        val profilePage = ProfilePage()
        val reportsGuestWifiPage = ReportsGuestWifiPage()
        val commonUtils = CommonUtils()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Гостевой WiFi")
//        sleep(10000)
        step("Открыть страницу с отчетом 'Гостевой WiFi'")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/analytics.wifi/")
        sleep(10000)
        step("Открыть календарь для смены дат")
        reportsGuestWifiPage.clickToOpenDateRange()
        step("Установить дату начала периода 04/01/2023")
        reportsGuestWifiPage.changeStartDate("04/01/2023")
        step("Установить дату конца периода 04/30/2023")
        reportsGuestWifiPage.changeEndDate("04/30/2023")
        step("Нажать на кнопку 'Применить' для подтверждения дат")
        reportsGuestWifiPage.clickApplyDateChangeButton()
        step("Убедиться, что кнопка перехода на главный сайт reMarked загрузилась и видна внизу страницы")
        val result = reportsGuestWifiPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
        //Selenide.Wait().until(ExpectedConditions.urlContains("/analytics.wifi/?from=2023-04-01&to=2023-04-30"))
//        val resultName = reportsGuestWifiPage.downloadTableDateInPdfFile()
//        val result = commonUtils.smartDownload("/src/test/resources/testResults/")
//        //val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)
//        assertEquals(
//            "Отчет по гостевому Wi-Fi BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-30.pdf",
//            resultName
//        )
    }

    @Test(dependsOnMethods = ["checkReportWifiGuest"])
    @Description("Отчет Сводка и смена дат")
     //Тест-кейс №6:  “Сводка” и смена дат
    fun checkSummaryReport() {
        val profilePage = ProfilePage()
        val reportsSummaryPage=ReportsSummaryPage()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сводка")
        step("Открыть страницу с отчетом Сводка")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/summary.report/")
        sleep(10000)
        step("Открыть календарь для смены дат")
        reportsSummaryPage.clickToOpenDateRange()
        step("Установить дату начала периода 04/01/2023")
        reportsSummaryPage.changeStartDate("04/01/2023")
        step("Установить дату конца периода 04/30/2023")
        reportsSummaryPage.changeEndDate("04/30/2023")
        step("Нажать на кнопку 'Применить' для подтверждения дат")
        reportsSummaryPage.clickApplyDateChangeButton()
        sleep(15000)
        step("Убедиться, что кнопка перехода на главный сайт reMarked загрузилась и видна внизу страницы")
        val result = reportsSummaryPage.recountButtonVisible()
        Assert.assertEquals(true, result) //Здесь происходит проверка по наличию кнопки "Пересчитать" внизу страницы

    }

    @Test(dependsOnMethods = ["checkSummaryReport"])
    @Description("Проверка отчета Доставки")
     //Тест-кейс №7:  ”Доставки”, смена дат и скачивание отчета
    fun checkDeliveryReport() {
        val profilePage = ProfilePage()
        val reportsDeliveryPage = ReportsDeliveryPage()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Доставки")
        step("Открыть страницу с отчетом Доставки")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/delivery.report/")
        sleep(10000)
        step("Открыть календарь для смены дат")
        reportsDeliveryPage.clickToOpenDateRange()
        step("Установить дату начала периода 04/01/2023")
        reportsDeliveryPage.changeStartDate("04/01/2023")
        step("Установить дату начала периода 04/30/2023")
        reportsDeliveryPage.changeEndDate("04/30/2023")
        step("Нажать на кнопку 'Применить' для подтверждения дат")
        reportsDeliveryPage.clickApplyDateChangeButton()
        step("Убедиться, что кнопка перехода на главный сайт reMarked загрузилась и видна внизу страницы")
        val result = reportsDeliveryPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
       /* val result = reportsDeliveryPage.downloadTableDateInPdfFile()
        assertEquals() - здесь должно быть название доки, с которым сверяться, но по состоянию на 19 мая кнопка скачать неактивна и файл не скачивается - тикет заведен, ждем исправления
*/
    }

    @Test(dependsOnMethods = ["checkDeliveryReport"])
    //Тест-кейс №8:  ”ABC анализ по блюдам”, смена дат и скачивание отчета
    @Description("Проверка отчета АВС анализ по блюдам")
    fun checkReportsABCDish() {
        val profilePage = ProfilePage()
        val abcDishPage = ABCDishPage()
        val commonUtils = CommonUtils()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("ABC анализ по блюдам")
        step("Открыть отчет АВС анализ по блюдам")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/analytics.abc/")
        sleep(10000)
        step("Открыть календарь для смены дат")
        abcDishPage.clickToOpenDateRange()
        step("Установить дату начала периода 04/01/2023")
        abcDishPage.changeStartDate("04/01/2023")
        step("Установить дату конца периода 04/07/2023")
        abcDishPage.changeEndDate("04/07/2023")
        step("Нажать на кнопку 'Применить' для подтверждения дат")
        abcDishPage.clickApplyDateChangeButton()
        sleep(10000)
        step("Убедиться, что кнопка перехода на главный сайт reMarked загрузилась и видна внизу страницы")
        val result = abcDishPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
//        abcDishPage.buttonDropdownMenuClick()
//        abcDishPage.downloadTableDateInXlsFile()
//        val result = commonUtils.smartDownload("/src/test/resources/testResults/")
//        assertEquals(true, result)
//        val fileSize = commonUtils.findFileInDirectory("/src/test/resources/testResults/").listFiles().get(0).length()
//        assertTrue { fileSize in 70040..70045  }
    }

    @Test(dependsOnMethods = ["checkReportsABCDish"])
    //Тест-кейс №10:  ”ABC анализ по гостям”, смена дат и скачивание отчета
    fun checkReportABCGuest() {
        val profilePage = ProfilePage()
        val abcGuestsPage = ABCGuestsPage()
        val commonUtils = CommonUtils()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("ABC анализ по гостям")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/analytics.abc.guests/")
        sleep(10000)

        abcGuestsPage.clickToOpenDateRange()
        abcGuestsPage.changeStartDate("04/01/2023")
        abcGuestsPage.changeEndDate("04/30/2023")
        abcGuestsPage.clickApplyDateChangeButton()
        val result = abcGuestsPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)

//        abcGuestsPage.buttonDropdownMenuClick()
//        abcGuestsPage.downloadTableDateInXlsFile()
//        val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)
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

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Поведение гостей")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/behavior/")
        sleep(10000)

        behaviorReportPage.clickToOpenDateRange()
        behaviorReportPage.changeStartDate("04/01/2023")
        behaviorReportPage.changeEndDate("04/30/2023")
        behaviorReportPage.clickApplyDateChangeButton()
        behaviorReportPage.buttonDropdownMenuClick()
        val result = behaviorReportPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
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

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Отзывы после визита")
        open("https://cabinet.clientomer.ru/$pointBonaCapona/messages.report/")
        sleep(10000)

        reportsReviewAfterVisitPage.clickToOpenDateRange()
        reportsReviewAfterVisitPage.changeStartDate("04/01/2023")
        reportsReviewAfterVisitPage.changeEndDate("04/01/2023")
        reportsReviewAfterVisitPage.clickApplyDateChangeButton()
        sleep(10000)
        val result = reportsReviewAfterVisitPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
//        reportsReviewAfterVisitPage.buttonDropdownMenuClick()
//        val resultName = reportsReviewAfterVisitPage.downloadTableDateInXlsFile()
//
//        val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)
//       val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles().get(0).length()
//       // assertEquals(14886, fileSize)
//        assertTrue { fileSize in 14885..14887 }
//       assertEquals("messages.report_2023-04-01_2023-04-01.xlsx", resultName)
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

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Дни рождения")
        open("https://cabinet.clientomer.ru/400125/birthdays.report/")
        sleep(10000)

        reportsBirthdayPage.clickToOpenDateRange()
        reportsBirthdayPage.changeStartDate("04/01/2021")
        reportsBirthdayPage.changeEndDate("04/30/2021")
        reportsBirthdayPage.clickApplyDateChangeButton()
//        reportsBirthdayPage.buttonDropdownMenuClick()
//        val resultName = reportsBirthdayPage.downloadTableDateInXlsFile()
//
//        val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)
//        assertEquals("birthdays_report_2021-04-01_2021-04-30.xlsx", resultName)
        val result = reportsBirthdayPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)

        //val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
        //assertEquals(14896, fileSize)
    }

    @Test(dependsOnMethods = ["checkBirthdays"])
    //Тест-кейс №11:  ”RFM-отчет ”, проверка загрузки заголовка
    fun oldRFMReportCheck() {

        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val reportsRFMPage = ReportsRFMPage()

        open("https://cabinet.clientomer.ru")
        loginPage.setValueToLoginEditBox(loginFiesta)
        loginPage.setValueToPasswordEditBox(passwordFiesta)
        loginPage.setValueToPointEditBox(pointFiesta)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameFiesta.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())


//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("RFM-отчет")
        open("https://cabinet.clientomer.ru/120004/rfm.report/")
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

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        sleep(10000)
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Комплементы")
        open("https://cabinet.clientomer.ru/400125/complements/")
        sleep(30000)

        complementPage.clickToOpenDateRange()
        complementPage.changeStartDate("04/01/2023")
        complementPage.changeEndDate("04/30/2023")
        complementPage.clickApplyDateChangeButton()

//        complementPage.buttonDropdownMenuClick()
//
//        val resultName = complementPage.downloadTableDateInXlsFile()
//        sleep(10000)
//        val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)
//        val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
//
//        assertEquals("complements__2023-04-01_2023-04-30.xlsx", resultName)
//        assertTrue{fileSize in 49003 .. 49008}
        val resultHeader = complementPage.mainSiteButtonVisible()
        assertEquals(true, resultHeader)
    }
    @Test(dependsOnMethods = ["complementsReportCheck"])
    //Тест-кейс №11:  ”Новый RFM-отчет ”, смена дат, проверка загрузки страницы через заголовок
    fun newRfmReportCheck() {
        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val commonUtils = CommonUtils()
        val reportsNewRFMPage = ReportsNewRFMPage()

//        profilePage.leftMenuSingleSelector("Реклама")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Сегменты")
//        profilePage.leftMenuSingleSelector("Отчеты")
//        profilePage.leftSubMenuVisibleCheck()
//        profilePage.leftMenuItemsSelector("Новый RFM-отчёт")
        open("https://cabinet.clientomer.ru/400125/new.rfm.report/")
        sleep(60000)

//        reportsNewRFMPage.buttonDropdownMenuClick()
//        reportsNewRFMPage.downloadTableDateInXlsFile()
//
//        val result = commonUtils.smartDownload("/Users/Shared/test/")
//        assertEquals(true, result)

        val result = reportsNewRFMPage.mainSiteButtonVisible()
        Assert.assertEquals(true, result)
    }
}
