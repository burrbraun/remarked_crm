package pages

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.files.FileFilters

class ReportsOrdersInCafePage {
    private val dateRange = Selenide.`$`("[id='reportrange']")
    private val dateRangeStart = Selenide.`$`("[name='daterangepicker_start']")
    private val dateRangeEnd = Selenide.`$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = Selenide.`$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val downloadPdfFile = Selenide.`$$`("[id='save_pdf'] ")

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
    fun downloadTableDateInPdfFile(): String {
        val reportFile = downloadPdfFile.last().download(FileFilters.withExtension("pdf"))
        return reportFile.name
    }

    fun getAbsoluteFilePath(): String {
        val reportFile = downloadPdfFile.last().download(FileFilters.withExtension("pdf"))
        return reportFile.absolutePath
    }

}