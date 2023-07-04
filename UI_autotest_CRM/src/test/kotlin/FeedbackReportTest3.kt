import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.sleep
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import pages.ProfilePage
import pages.ReviewsReportPage
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FeedbackReportTest3 : BaseTest() {
    @Test
    // @Order(1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(loginBonaCapona)
        loginPage.setValueToPasswordEditBox(passwordBonaCapona)
        loginPage.setValueToPointEditBox(pointBonaCapona)
        loginPage.loginButtonClick()
        sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameBonaCapona.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }
    @Test
    // @Order(2)
    fun leftMenuNavigation(){
        val profilePage = ProfilePage()
        val result = profilePage.leftMenuItemsSelector("Отчеты","Отзывы")

        assertEquals("Отзывы",result)
    }
    @Test
    // @Order(3)
    fun setNewDates() {
        val reviewReportPage = ReviewsReportPage()

        reviewReportPage.clickToOpenDateRange()
        reviewReportPage.changeStartDate("04/01/2023")
        reviewReportPage.changeEndDate("04/30/2023")
        reviewReportPage.clickApplyDateChangeButton()
        sleep(10000)
    }
    @Test
    // @Order(4)
    fun downloadPdfFile() {
        val reviewReportPage = ReviewsReportPage()
        val result = reviewReportPage.downloadTableDateInPdfFile()

        assertEquals("Отчет по отзывам BONA CAPONA - общий лк (только боны) с 2023-04-01 по 2023-04-30.pdf",result)
    }
    @Test
    // @Order(5)
    fun downloadedFileSizeCheck() {
        val reviewReportPage = ReviewsReportPage()
      //  val fileSize = reviewReportPage.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
       // assertEquals(4798640, fileSize)
    }
}