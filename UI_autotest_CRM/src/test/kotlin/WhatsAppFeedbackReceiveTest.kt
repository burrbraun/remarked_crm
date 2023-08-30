import io.qameta.allure.Allure
import jdk.jfr.Description
import org.testng.annotations.Test
import pages.SqlBaseUtils
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class WhatsAppFeedbackReceiveTest {
    @Test
    @Description("Тест на проверку отвала рассылки просьб оценить последний визит или доставку в WhatsApp")
    fun checkWhatsappFeedbackCollections() {
        val consoleOutput = captureConsoleOutput {
            val sqlBaseUtils = SqlBaseUtils()
            System.out.format("--------------------------\nНет сообщений для сбора отзывов в whatsapp за последние 25 часов у поинтов с активной интеграцией feedbackaftervisit:\n")
            sqlBaseUtils.checkWhatsAppFeedbackCollectingForPrev25Hours()
            assert(true)
        }
        Allure.addAttachment("Console Output", consoleOutput)
    }
    private fun captureConsoleOutput(block: () -> Unit): String {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream, true)

        val systemOut = System.out
        val systemErr = System.err

        try {
            System.setOut(printStream)
            System.setErr(printStream)

            block.invoke()
        } finally {
            System.setOut(systemOut)
            System.setErr(systemErr)
        }

        return outputStream.toString()
    }
}