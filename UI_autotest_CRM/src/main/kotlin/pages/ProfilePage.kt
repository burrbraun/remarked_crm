package pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.*
import org.openqa.selenium.support.ui.ExpectedCondition
import java.time.Duration


class ProfilePage {

    private val leftSideMenuCollection = `$$`("[class='has-ul']")

    private val mainSiteLink = `$`("[class='logo']")
    private val leftSubMenu = `$`("[class='active'] [class='hidden-ul']")
    private val profileNameMenu = `$`("[class='dropdown-toggle']")
    private val logOutButton = `$`("[class='icon-switch2']")

    fun checkCustomerName(): String {
        val customerNameCollection = `$$`("[class='media-body'] [class^='cabname-span']")
        val elementValue =  customerNameCollection[0].text()
        return elementValue
    }

    fun clickMainSiteLink() {
        mainSiteLink.click()
    }
    fun leftMenuItemsSelector(leftMenuItem : String, leftSubMenuItem: String): String {
        var result = ""
        for (n in 0 until leftSideMenuCollection.size){
           if(leftSideMenuCollection[n].text.equals(leftMenuItem)) {
               leftSideMenuCollection[n].click()
               break
               sleep(20000)
           }
        }
        val leftSubMenuElementCollection = `$$`("[class='active'] [class='hidden-ul'] li")
        for (n in 0 until leftSubMenuElementCollection.size){
            if(leftSubMenuElementCollection[n].text.equals(leftSubMenuItem)) {
                result = leftSubMenuElementCollection[n].text
                leftSubMenuElementCollection[n].scrollTo()
                leftSubMenuElementCollection[n].click()
                break
                sleep(20000)
            }
        }
    return result
    }

    fun leftMenuSingleSelector(leftMenuItem : String): String {
        var result = ""
        for (n in 0 until leftSideMenuCollection.size){
            if(leftSideMenuCollection[n].text.equals(leftMenuItem)) {
                leftSideMenuCollection[n].click()
                break
            }
        }
        return result
    }


    fun leftSubMenuVisibleCheck() {
        leftSubMenu.shouldBe(visible)
    }
    fun leftSubMenuInvisibleCheck() {
        leftSubMenu.shouldBe(not(visible))
    }
    fun profileMenuOpen() {
        profileNameMenu.click()
    }
    fun logOutButtonClick() {
        logOutButton.click()
    }

}