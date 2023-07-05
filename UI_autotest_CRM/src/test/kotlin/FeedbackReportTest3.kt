import com.codeborne.selenide.Selenide.sleep
import org.testng.annotations.Test
import pages.ProfilePage
import pages.ReviewsReportPage
import kotlin.test.assertEquals

class FeedbackReportTest3 : BaseTest() {

    @Test(priority=1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(loginBonaCapona)
        loginPage.setValueToPasswordEditBox(passwordBonaCapona)
        loginPage.setValueToPointEditBox(pointBonaCapona)
        loginPage.loginButtonClick()
        sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameBonaCapona.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

    @Test(priority=2)
    fun leftMenuNavigation() {
        val profilePage = ProfilePage()
        val result = profilePage.leftMenuItemsSelector("Отчеты","Отзывы")

        assertEquals("Отзывы",result)
    }

    @Test(priority=3)
    fun setNewDates() {
        val reviewReportPage = ReviewsReportPage()

        reviewReportPage.clickToOpenDateRange()
        reviewReportPage.changeStartDate("04/01/2023")
        reviewReportPage.changeEndDate("04/30/2023")
        reviewReportPage.clickApplyDateChangeButton()
        sleep(10000)
    }

    @Test(priority=4)
    fun downloadPdfFile() {
        val reviewReportPage = ReviewsReportPage()
        val result = reviewReportPage.downloadTableDateInPdfFile()

        assertEquals("Отчет по отзывам BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-30.pdf",result)
    }

//    @Test(priority=5) //здесь баг с разным размером пдф файла - требует переделки метод создания пдф
//    fun downloadedFileSizeCheck() {
//        val reviewReportPage = ReviewsReportPage()
//         val fileSize = reviewReportPage.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
//         assertEquals(4798640, fileSize)
//    }
}