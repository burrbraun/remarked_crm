package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.files.FileFilters

class ReportsBirthdayPage {
    private val dateRange = Selenide.`$`("[id='reportrange']")
    private val dateRangeStart = Selenide.`$`("[name='daterangepicker_start']")
    private val dateRangeEnd = Selenide.`$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = Selenide.`$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val buttonDropdownMenu = Selenide.`$`("[class='btn btn-success dropdown-toggle']")

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
    fun buttonDropdownMenuClick() {
        buttonDropdownMenu.click()
    }
    fun mainSiteButtonVisible():Boolean {
        val mainSiteLink = Selenide.`$`("[class='logo']").scrollIntoView(true)
        mainSiteLink.shouldBe(Condition.exist)
        return true
    }
    fun downloadTableDateInXlsFile(): String {
        val downloadFilesTypeCollection = Selenide.`$$`("[class='dropdown-menu dropdown-menu-right'] li")
        val reportFile = downloadFilesTypeCollection.last().download(FileFilters.withExtension("xlsx"))
        return reportFile.name
    }
}