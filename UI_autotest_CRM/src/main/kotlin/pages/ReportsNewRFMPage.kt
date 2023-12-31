package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Condition.exist
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.files.FileFilters

class ReportsNewRFMPage {
    private val buttonDropdownMenu = `$`("[class='btn btn-success dropdown-toggle']")
    private val headerText = `$`("[class='page-title'] [data-i18n='RFM-отчёт']")

    fun buttonDropdownMenuClick() {
        buttonDropdownMenu.click()
    }
    fun downloadTableDateInXlsFile(): String {
        val downloadFilesTypeCollection = `$`("[id='excelUploadItem']")
        val reportFile = downloadFilesTypeCollection.download(FileFilters.withExtension("xlsx"))
        return reportFile.name
    }
    fun reportNameVisible(): Boolean {
        headerText.should(exist)
        return true
    }
    fun mainSiteButtonVisible():Boolean {
        val mainSiteLink = Selenide.`$`("[class='logo']").scrollIntoView(true)
        mainSiteLink.shouldBe(Condition.exist)
        return true
    }
}