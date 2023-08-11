import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import io.qameta.allure.Attachment
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import java.nio.charset.StandardCharsets


class AllureHelpers {
    /**
     * Прикрепление текстовой строки.
     *
     * @param text the text
     * @return the string
     */
    @Attachment(value = "AllureTextReport", type = "text/plain", fileExtension = ".txt")
    fun attachText(text: String?): String? {
        return text
    }

    /**
     * Прикрепление строки в формате csv.
     *
     * @param csv the csv
     * @return the string
     */
    @Attachment(value = "AllureCSVReport", type = "text/csv", fileExtension = ".csv")
    fun attachCSV(csv: String?): String? {
        return csv
    }


    /**
     * Получение скриншота byte [ ].
     *
     * @param name the name
     * @return the byte [ ]
     */
    @Attachment(value = "{name}", type = "image/png", fileExtension = ".png")
    fun takeScreenshot(name: String?): ByteArray? {
        return getScreenshotBytes()
    }


    /**
     * Получение байтов скриншота byte[ ].
     *
     * @return the byte [ ]
     */
    fun getScreenshotBytes(): ByteArray? {
        return (WebDriverRunner.getWebDriver() as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
    }

    /**
     * Получение байтов скриншота byte[ ].
     *
     * @param elem the elem
     * @return the byte [ ]
     */
    fun getScreenshotBytes(elem: SelenideElement): ByteArray? {
        return elem.getScreenshotAs(OutputType.BYTES)
    }
}