package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.files.FileFilters
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.lang.Thread.sleep
import java.util.*

class ReviewsReportPage {

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
    fun downloadTableDateInPdfFile() : String {
        val downloadPdfFile = `$`("[id='save_pdf'] ").scrollIntoView(true)
        sleep(20000)
        System.err.println(downloadPdfFile.size)
        val pngFileName: String? = Selenide.screenshot("reviews_report")
        sleep(10000)
        val reportFile = downloadPdfFile.download(FileFilters.withExtension("pdf"))
        return reportFile.name
    }
    fun getAbsoluteFilePath(): String {
        val downloadPdfFile = `$$`("[id='save_pdf'] ")
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