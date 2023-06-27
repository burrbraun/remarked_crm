import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pages.*
import java.lang.Thread.sleep
import kotlin.test.assertTrue

class SandBox: BaseTest() {
    @Test


    fun oldRFMTest() {

        val loginPage = LoginPage()
        val profilePage = ProfilePage()
        val reportsSummaryPage = ReportsSummaryPage()
        val abcDishPage = ABCDishPage()
        val commonUtils = CommonUtils()
        val reportsBirthdayPage = ReportsBirthdayPage()
        val reportsRFMPage = ReportsRFMPage()

        open("https://cabinet.clientomer.ru")
        loginPage.setValueToLoginEditBox(loginFiesta)
        loginPage.setValueToPasswordEditBox(passwordFiesta)
        loginPage.setValueToPointEditBox(pointFiesta)
        loginPage.loginButtonClick()
        sleep(15000)
        val actualUserName = profilePage.checkCustomerName()
        assertEquals(customerNameFiesta.lowercase().trimEnd(), actualUserName.lowercase().trimEnd())

        profilePage.leftMenuItemsSelector("Реклама", "Сегменты")
        profilePage.leftMenuItemsSelector("Отчеты", "RFM-отчет")
        Thread.sleep(10000)

        /*abcDishPage.clickToOpenDateRange()
        abcDishPage.changeStartDate("04/01/2023")
        abcDishPage.changeEndDate("04/07/2023")
        abcDishPage.clickApplyDateChangeButton()
        Thread.sleep(10000)
        abcDishPage.buttonDropdownMenuClick()
        abcDishPage.downloadTableDateInXlsFile()
        Thread.sleep(10000)
        val result = commonUtils.smartDownload("/Users/Shared/test/")
        kotlin.test.assertEquals(true, result)
        val fileSize = commonUtils.findFileInDirectory("/Users/Shared/test/").listFiles().get(0).length()
        assertTrue { fileSize in 38318..38320  }*/

        val resultHeader = reportsRFMPage.reportNameVisible()
        assertEquals(true, resultHeader)


    }

}