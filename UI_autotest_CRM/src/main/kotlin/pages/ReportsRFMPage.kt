package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide.`$`


class ReportsRFMPage {
    private val headerText = `$`("[class='page-title'] [data-i18n='RFM-отчет']")

    fun reportNameVisible(): Boolean {
        headerText.should(Condition.exist)
        return true
    }
    }


