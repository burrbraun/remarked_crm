package pages

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.files.FileFilters.withExtension
import org.apache.commons.io.comparator.LastModifiedFileComparator.LASTMODIFIED_REVERSE
import java.io.File
import java.util.*

class WifiAccessTable {

    private val dateRange = `$`("[id='reportrange']")
    private val dateRangeStart = `$`("[name='daterangepicker_start']")
    private val dateRangeEnd = `$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val wifiAccessTable = `$`("[class='table customizable_table']")
    private val buttonDropdownMenu = `$`("[class='btn btn-success dropdown-toggle']")

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

    fun wifiAccessCheckTable(checkingCellValue : String ): Boolean {
        val tableRows = wifiAccessTable.`$$`("tr")
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
        val downloadFilesTypeCollection = `$$`("[class='dropdown-menu dropdown-menu-right'] li")
        // downloadFilesTypeCollection.last().click()
        val reportFile = downloadFilesTypeCollection.last().download(withExtension("xlsx"))
        return reportFile.name
    }

    fun getAbsoluteFilePath(): String {
        val downloadFilesTypeCollection = `$$`("[class='dropdown-menu dropdown-menu-right'] li")
        val reportFile = downloadFilesTypeCollection.last().download(withExtension("xlsx"))
        return reportFile.absolutePath
    }

    fun findFileInDirectory(rootDir: String) : File {
        val arrayOfFiles = File(rootDir).listFiles()
        if (arrayOfFiles != null) {
            Arrays.sort(arrayOfFiles, LASTMODIFIED_REVERSE);
        }
        if (arrayOfFiles != null) {
            for (i in arrayOfFiles.indices) {
                if (arrayOfFiles[i]!=null){
                    return arrayOfFiles[i]
                }
            }
        }
        return arrayOfFiles[0]
    }

}