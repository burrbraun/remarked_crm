package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.files.FileFilters

class ReportsOrdersInCafePage {

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
    fun downloadTableDateInPdfFile(): String {
        val downloadPdfFile = Selenide.`$`("[id='save_pdf'] ")
        Selenide.sleep(5000)
        System.err.println(downloadPdfFile.size)
        val pngFileName: String? = Selenide.screenshot("Orders_in_cafe_report")
        val reportFile = downloadPdfFile.scrollTo().download(FileFilters.withExtension("pdf"))
        return reportFile.name
    }

    fun getAbsoluteFilePath(): String {
        val downloadPdfFile = Selenide.`$$`("[id='save_pdf'] ")
        val reportFile = downloadPdfFile.last().download(FileFilters.withExtension("pdf"))
        return reportFile.absolutePath
    }

}