package pages.Sources

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide

class SourcesBaseObjects {
    fun changeStartDate(newValue: String.Companion) {
        val dateRangeStart = Selenide.`$`("[name='daterangepicker_start']")
        dateRangeStart.shouldBe(Condition.visible)
        dateRangeStart.value = newValue.toString()
    }

    fun changeEndDate(newValue: String.Companion) {
        val dateRangeEnd = Selenide.`$`("[name='daterangepicker_end']")
        dateRangeEnd.shouldBe(Condition.visible)
        dateRangeEnd.value = newValue.toString()
    }

    fun clickApplyDateChangeButton() {
        val applyButtonDateChange = Selenide.`$`("[class='applyBtn btn btn-small btn-info btn-block']")
        applyButtonDateChange.shouldBe(Condition.visible)
        applyButtonDateChange.click()
    }

    fun mainSiteButtonVisible(): Boolean {
        val mainSiteLink = Selenide.`$`("[class='logo']").scrollIntoView(true)
        mainSiteLink.shouldBe(Condition.exist)
        return true
    }

    fun logOut() {
        val profileNameMenu = Selenide.`$`("[class='dropdown dropdown-user']")
        profileNameMenu.click()
        val logOutButton = Selenide.`$`("[class='icon-switch2']")
        logOutButton.click()
    }
}