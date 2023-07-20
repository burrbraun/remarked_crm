package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.`$`

class ReportsSummaryPage {
    fun clickToOpenDateRange() {
        val dateRange = `$`("[id='reportrange']")
        Thread.sleep(10000)
        dateRange.click()
    }
    fun changeStartDate(newValue: String) {
        val dateRangeStart = `$`("[name='daterangepicker_start']")
        dateRangeStart.value = newValue
    }
    fun changeEndDate(newValue: String) {
        val dateRangeEnd = `$`("[name='daterangepicker_end']")
        dateRangeEnd.value = newValue
    }
    fun clickApplyDateChangeButton() {
        val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
        applyButtonDateChange.click()
    }
    fun recountButtonVisible(): Boolean {
        val recountButton = `$`("[id = 'recount_cache_button']").scrollIntoView(true)
        recountButton.should(Condition.exist)
        return true
    }
}