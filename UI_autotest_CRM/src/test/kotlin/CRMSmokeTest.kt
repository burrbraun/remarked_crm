import com.codeborne.selenide.Selenide
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import pages.ProfilePage
import pages.WifiAuthPage
import java.lang.Thread.sleep
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CRMSmokeTest : BaseTest() {
    @Test
    // @Order(1)
    fun checkAuthByUserName() {
        loginPage.setValueToLoginEditBox(login)
        loginPage.setValueToPasswordEditBox(password)
        loginPage.setValueToPointEditBox(point)
        loginPage.loginButtonClick()
        Selenide.sleep(15000)
        val actualUserName= profilePage.checkCustomerName()
        assertEquals(customerName.lowercase().trimEnd(),actualUserName.lowercase().trimEnd())
    }

    @Test
    // @Order(2)
    fun leftMenuNavigation(){
        val profilePage = ProfilePage()
        val result = profilePage.leftMenuItemsSelector("Источники","WiFi-авторизации")
        assertEquals("WiFi-авторизации",result)
    }

    @Test
    // @Order(3)
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
@Test
// @Order(4)
fun downloadXlsFileCheck() {
    val wifiAuthPage = WifiAuthPage()
    wifiAuthPage.buttonDropdownMenuClick()
    val result = wifiAuthPage.downloadTableDateInXlsFile()
    assertEquals("wifi_auth_2021-05-17_2021-05-18.xlsx",result)
}
}