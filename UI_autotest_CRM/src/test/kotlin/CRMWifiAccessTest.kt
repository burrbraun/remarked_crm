import com.codeborne.selenide.Selenide
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import pages.ProfilePage
import pages.WifiAccessTable
import java.lang.Thread.sleep
import kotlin.test.assertEquals

 @TestMethodOrder(MethodOrderer.OrderAnnotation::class)
    class CRMWifiAccessTest : BaseTest() {
        @Test
        @Order(1)
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
        @Order(2)
        fun leftMenuNavigation(){
            val profilePage = ProfilePage()
            val result = profilePage.leftMenuItemsSelector("Источники","Посещения WiFi")
            assertEquals("Посещения WiFi",result)
        }

        @Test
        @Order(3)
        fun checkPhoneNumInTheWifiAccessGrid() {
            val wifiAccessTable = WifiAccessTable()

            wifiAccessTable.clickToOpenDateRange()
            wifiAccessTable.changeStartDate("07/08/2021")
            wifiAccessTable.changeEndDate("07/09/2021")
            wifiAccessTable.clickApplyDateChangeButton()

            val result = wifiAccessTable.wifiAccessCheckTable("+79219777482")
            assertEquals(true,result)
            sleep(5000)
        }

        @Test
        @Order(4)
        fun downloadXlsFileCheck() {
            val wifiAccessTable = WifiAccessTable()
            wifiAccessTable.buttonDropdownMenuClick()
            val result = wifiAccessTable.downloadTableDateInXlsFile()
            wifiAccessTable.findFileInDirectory("/Users/Shared/test/")
            assertEquals("wifi_visits_2021-07-08_2021-07-09.xlsx",result)
        }

        @Test
        @Order(5)
        fun downloadedFileSizeCheck() {
            val wifiAccessTable = WifiAccessTable()
            //val filePath = Paths.get("/Users/Shared/test/wifi_visits_2021-07-08_2021-07-09.xlsx")
            val fileSize = wifiAccessTable.findFileInDirectory("/Users/Shared/test/").listFiles()[0].length()
            assertEquals(6577, fileSize)
     }

}