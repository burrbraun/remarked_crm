package pages.Sources

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide

class WifiAuthSourcePage {
    fun clickToOpenDateRange() {
        val dateRange = Selenide.`$`("[id='reportrange']")
        dateRange.shouldBe(Condition.visible)
        dateRange.click()
    }

    fun changeStartDate(newValue: String) {
        val dateRangeStart = Selenide.`$`("[name='daterangepicker_start']")
        dateRangeStart.shouldBe(Condition.visible)
        dateRangeStart.value = newValue
    }

    fun changeEndDate(newValue: String) {
        val dateRangeEnd = Selenide.`$`("[name='daterangepicker_end']")
        dateRangeEnd.shouldBe(Condition.visible)
        dateRangeEnd.value = newValue
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
}