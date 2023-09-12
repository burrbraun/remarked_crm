package pages.Sources

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide

class CallsSourcePage {
    fun clickToOpenDateRange() {
        val dateRange = Selenide.`$`("[id='reportrange']")
        dateRange.shouldBe(Condition.visible)
        dateRange.click()
    }

    fun changeStartDate(newValue: String) {
        val sourcesBaseObjects = SourcesBaseObjects()
        val dateRangeStart = sourcesBaseObjects.changeStartDate(String)
    }

    fun changeEndDate(newValue: String) {
        val sourcesBaseObjects = SourcesBaseObjects()
        val dateRangeEnd = sourcesBaseObjects.changeEndDate(String)
    }

    fun clickApplyDateChangeButton() {
        val sourcesBaseObjects = SourcesBaseObjects()
        val applyButtonDateChange = sourcesBaseObjects.clickApplyDateChangeButton()
    }

    fun mainSiteButtonVisible() {
        val sourcesBaseObjects = SourcesBaseObjects()
        val mainSiteLink = sourcesBaseObjects.mainSiteButtonVisible()
    }

    fun logOut() {
        val sourcesBaseObjects = SourcesBaseObjects()
        val logOutButton = sourcesBaseObjects.logOut()
    }
}