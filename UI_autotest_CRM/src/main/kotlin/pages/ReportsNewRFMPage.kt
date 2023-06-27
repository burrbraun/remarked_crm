package pages

import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.files.FileFilters

class ReportsNewRFMPage {
    private val buttonDropdownMenu = `$`("[class='btn btn-success dropdown-toggle']")
    private val downloadFilesTypeCollection = `$`("[id='excelUploadItem']")
    private val headerText = `$`("[class='page-title'] [data-i18n='RFM-отчёт']")

    fun buttonDropdownMenuClick() {
        buttonDropdownMenu.click()
    }
    fun downloadTableDateInXlsFile(): String {
        val reportFile = downloadFilesTypeCollection.download(FileFilters.withExtension("xlsx"))
        return reportFile.name
    }
    fun reportNameVisible(): Boolean {
        headerText.should(exist)
        return true
    }

}