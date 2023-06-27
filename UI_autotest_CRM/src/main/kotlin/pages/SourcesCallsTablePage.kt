package pages

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.files.FileFilters

class SourcesCallsTablePage {
    private val dateRange = Selenide.`$`("[id='reportrange']")
    private val dateRangeStart = Selenide.`$`("[name='daterangepicker_start']")
    private val dateRangeEnd = Selenide.`$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = Selenide.`$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val callsTablePage = Selenide.`$`("[class='table customizable_table']")
    private val tableRows = callsTablePage.`$$`("tr")
    private val buttonDropdownMenu = Selenide.`$`("[class='btn btn-success dropdown-toggle']")
    private val downloadFilesTypeCollection = Selenide.`$$`("[class='dropdown-menu dropdown-menu-right'] li")

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

    fun callsTablePageCheck(checkingCellValue : String ): Boolean {

        for (n in 0 until tableRows.size) {
            var tableCells=tableRows[n].`$$`("td")
            for (m in 0 until tableCells.size) {
                if (tableCells[m].text.equals(checkingCellValue)) {

                    return true
                }

            }
        }
        return false
    }
    fun buttonDropdownMenuClick() {
        buttonDropdownMenu.click()
    }
    fun downloadTableDateInXlsFile(): String {
        // downloadFilesTypeCollection.last().click()
        val reportFile = downloadFilesTypeCollection.last().download(FileFilters.withExtension("xlsx"))
        return reportFile.name
    }

}
