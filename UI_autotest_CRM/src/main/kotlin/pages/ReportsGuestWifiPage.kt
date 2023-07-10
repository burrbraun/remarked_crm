package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.files.FileFilters
import kotlin.time.Duration.Companion.seconds

class ReportsGuestWifiPage {

    fun clickToOpenDateRange() {
        val dateRange = `$`("[id='reportrange']")
        dateRange.shouldBe(Condition.visible)
            dateRange.click()
    }
    fun changeStartDate(newValue: String) {
        val dateRangeStart = `$`("[name='daterangepicker_start']")
            dateRangeStart.shouldBe(Condition.visible)
        dateRangeStart.value = newValue
    }
    fun changeEndDate(newValue: String) {
        val dateRangeEnd = `$`("[name='daterangepicker_end']")
            dateRangeEnd.shouldBe(Condition.visible)
        dateRangeEnd.value = newValue
    }
    fun clickApplyDateChangeButton() {
        val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
            applyButtonDateChange.shouldBe(Condition.visible)
        applyButtonDateChange.click()
    }
    fun downloadTableDateInPdfFile(): String {
        val downloadPdfFile = `$$`("[id='save_pdf'] ")
        sleep(1000)
        val reportFile = downloadPdfFile.last().download(FileFilters.withExtension("pdf"))
        return reportFile.name
    }

}