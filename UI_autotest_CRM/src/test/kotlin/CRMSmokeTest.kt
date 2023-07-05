import com.codeborne.selenide.Selenide
import org.testng.annotations.Test
import pages.ProfilePage
import pages.WifiAuthPage
import java.lang.Thread.sleep
import kotlin.test.assertEquals


class CRMSmokeTest : BaseTest() {

    @Test(priority=1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerName.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

    @Test(priority=2)
    fun leftMenuNavigation(){
        val profilePage = ProfilePage()
        val result = profilePage.leftMenuItemsSelector("Источники","WiFi-авторизации")
        assertEquals("WiFi-авторизации",result)
    }

    @Test(priority=3)
    fun checkPhoneNumInTheWifiAuthGrid() {
        val wifiAuthPage = WifiAuthPage()

        wifiAuthPage.clickToOpenDateRange()
        wifiAuthPage.changeStartDate("05/17/2021")
        wifiAuthPage.changeEndDate("05/18/2021")
        wifiAuthPage.clickApplyDateChangeButton()

        val result = wifiAuthPage.wifiAuthCheckTable("+79219777482")
        assertEquals(true,result)
        sleep(5000)
    }

    @Test(priority=4)
    fun downloadXlsFileCheck() {
        val wifiAuthPage = WifiAuthPage()
        wifiAuthPage.buttonDropdownMenuClick()
        val result = wifiAuthPage.downloadTableDateInXlsFile()
        assertEquals("wifi_auth_2021-05-17_2021-05-18.xlsx",result)
    }
}