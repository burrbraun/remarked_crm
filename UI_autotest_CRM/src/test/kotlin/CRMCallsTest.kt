import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.screenshot
import org.testng.annotations.Test
import pages.SourcesCallsTablePage
import pages.ProfilePage
import kotlin.test.assertEquals

class CRMCallsTest : BaseTest () {

    @Test(priority=1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(loginBonaCapona)
        loginPage.setValueToPasswordEditBox(passwordBonaCapona)
        loginPage.setValueToPointEditBox(pointBonaCapona)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerNameBonaCapona.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

//    @Test(priority=2)
//    fun leftMenuNavigation(){
//        val profilePage = ProfilePage()
//        val result = profilePage.leftMenuItemsSelector("Источники","Звонки")
//        assertEquals("Звонки",result)
//    }

    @Test(priority=3)
    fun checkPhoneNumInThePhoneCallsGrid() {
        val sourcesCallsTablePage = SourcesCallsTablePage()

        sourcesCallsTablePage.clickToOpenDateRange()
        sourcesCallsTablePage.changeStartDate("04/12/2023")
        sourcesCallsTablePage.changeEndDate("04/13/2023")
        sourcesCallsTablePage.clickApplyDateChangeButton()

        val result = sourcesCallsTablePage.callsTablePageCheck("+79006595675")
        assertEquals(true,result)
        Thread.sleep(5000)
    }

    @Test(priority=4)
    fun downloadXlsFileCheck() {
        val sourcesCallsTablePage = SourcesCallsTablePage()
        sourcesCallsTablePage.buttonDropdownMenuClick()
        val result = sourcesCallsTablePage.downloadTableDateInXlsFile()
        assertEquals("telephony_calls_2023-04-12_2023-04-13.xlsx",result)
        val pngFileName: String? = screenshot("my_file_name")
    }
}