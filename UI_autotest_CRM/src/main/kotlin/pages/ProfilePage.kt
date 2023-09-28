package pages

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.*
import org.openqa.selenium.support.ui.ExpectedCondition
import java.time.Duration


class ProfilePage {
    fun checkCustomerName(): String {
        val customerNameCollection = `$$`("[class='media-body'] [class^='cabname-span']")
        val elementValue =  customerNameCollection[0].text()
        return elementValue
    }

    fun clickMainSiteLink() {
        val mainSiteLink = `$`("[class='logo']")
        mainSiteLink.click()
    }
    fun leftMenuItemsSelector(leftSubMenuItem: String): String {
//        val leftSideMenuCollection = `$$`("[class='has-ul']")
        var result = ""
//        for (n in 0 until leftSideMenuCollection.size){
//           if(leftSideMenuCollection[n].text.equals(leftMenuItem)) {
//               leftSideMenuCollection[n].click()
//               break
//               sleep(20000) // удалить слипы т.к. не нужны
//           }
//        }
        val leftSubMenuElementCollection = `$$`("[class='active'] [class='hidden-ul'] li")
        for (n in 0 until leftSubMenuElementCollection.size){
            if(leftSubMenuElementCollection[n].text.equals(leftSubMenuItem)) {
                result = leftSubMenuElementCollection[n].text
                leftSubMenuElementCollection[n].scrollIntoView(true).click()
                break
                sleep(20000)
            }
        }
    return result
    }

    fun leftMenuSingleSelector(leftMenuItem : String): String {
        val leftSideMenuCollection = `$$`("[class='has-ul']")
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
        val leftSubMenu = `$`("[class='active'] [class='hidden-ul']")
        leftSubMenu.shouldBe(visible)
    }
    fun leftSubMenuInvisibleCheck() {
        val leftSubMenu = `$`("[class='active'] [class='hidden-ul']")
        leftSubMenu.shouldBe(not(visible))
    }
    fun profileMenuOpen() {
        val profileNameMenu = `$`("[class='dropdown-toggle']")
        profileNameMenu.click()
    }
    fun logOutButtonClick() {
        val logOutButton = `$`("[class='icon-switch2']")
        logOutButton.click()
    }
    fun checkCustomerNameInReserves(): String {
        val customerNameInReserves = `$$`(" [id = 'dropdown-user'] ")
        val elementsValue =  customerNameInReserves[0].text()
        return elementsValue
    }
    fun clickOnReservesListButton() {
        val reservesList = `$`(".nav-item.open-main-list")
        reservesList.click()
    }

}