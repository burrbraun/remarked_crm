package pages

import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.files.FileFilters.withExtension
import java.awt.image.ByteLookupTable

class WifiAuthPage {
        private val dateRange = `$`("[id='reportrange']")
        private val dateRangeStart = `$`("[name='daterangepicker_start']")
        private val dateRangeEnd = `$`("[name='daterangepicker_end']")
        private val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
        private val wifiAuthTable = `$`("[class='table customizable_table']")
        private val tableRows = wifiAuthTable.`$$`("tr")
        private val buttonDropdownMenu = `$`("[class='btn btn-success dropdown-toggle']")
        private val downloadFilesTypeCollection = `$$`("[class='dropdown-menu dropdown-menu-right'] li" )



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

        fun wifiAuthCheckTable(checkingCellValue : String ): Boolean {

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
                val reportFile = downloadFilesTypeCollection.last().download(withExtension("xlsx"))
                return reportFile.name
        }


}