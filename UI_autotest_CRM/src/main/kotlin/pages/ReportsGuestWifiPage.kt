package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.files.FileFilters

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
    fun mainSiteButtonVisible():Boolean {
        val mainSiteLink = Selenide.`$`("[class='logo']").scrollIntoView(true)
        mainSiteLink.shouldBe(Condition.exist)
        return true
    }
    fun downloadTableDateInPdfFile(): String {
        val downloadPdfFile = `$`("[id='save_pdf'] ")
        sleep(5000)
        System.err.println(downloadPdfFile.size)
        val pngFileName: String? = screenshot("wifirep_test")
        val reportFile = downloadPdfFile.scrollTo().download(FileFilters.withExtension("pdf"))
        return reportFile.name

    }

}