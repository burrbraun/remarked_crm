package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.`$`

class ReportsSummaryPage {
    private val dateRange = `$`("[id='reportrange']")
    private val dateRangeStart = `$`("[name='daterangepicker_start']")
    private val dateRangeEnd = `$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val recountButton = `$`("[id = 'recount_cache_button']")

    fun clickToOpenDateRange() {
        dateRange.click()
    }
    fun changeStartDate(newValue: String) {
        dateRangeStart.value = newValue
    }
    fun changeEndDate(newValue: String) {
        dateRangeEnd.value = newValue
    }
    fun clickApplyDateChangeButton() {
        applyButtonDateChange.click()
    }
    fun recountButtonVisible(): Boolean {
        recountButton.should(Condition.exist)
        return true

    }
}