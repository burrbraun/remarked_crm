package pages

import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.files.FileFilters
import com.codeborne.selenide.files.FileFilters.withExtension
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.util.*

class ReviewsReportPage {

    private val dateRange = `$`("[id='reportrange']")
    private val dateRangeStart = `$`("[name='daterangepicker_start']")
    private val dateRangeEnd = `$`("[name='daterangepicker_end']")
    private val applyButtonDateChange = `$`("[class='applyBtn btn btn-small btn-info btn-block']")
    private val buttonDropdownMenu = `$`("[class='btn btn-success dropdown-toggle']")
    private val downloadPdfFile = `$$`("[id='save_pdf'] ")

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
        val reportFile = downloadPdfFile.last().download(withExtension("pdf"))
        return reportFile.name
    }
    fun getAbsoluteFilePath(): String {
        val reportFile = downloadPdfFile.last().download(FileFilters.withExtension("pdf"))
        return reportFile.absolutePath
    }
    fun findFileInDirectory(rootDir: String) : File {
        val arrayOfFiles = File(rootDir).listFiles()
        if (arrayOfFiles != null) {
            Arrays.sort(arrayOfFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
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