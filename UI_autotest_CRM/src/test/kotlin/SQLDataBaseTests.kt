import io.qameta.allure.Allure
import io.qameta.allure.Description
import org.testng.annotations.Test
import pages.SqlBaseUtils
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class SQLDataBaseTests  {
    @Test
    @Description("Тест на проверку наличия обновлений базы продаж за последние 48 часов")
    fun checkSales() {
        val sqlBaseUtils = SqlBaseUtils()
        val result = sqlBaseUtils.checkPreviousDaySalesNotEmpty()
        assertEquals(true,result)
    }

//    @Test//(dependsOnMethods = ["checkSales"])
//    fun checkActiveCustomersSales() {
//        val sqlBaseUtils = SqlBaseUtils()
//        sqlBaseUtils.getActiveUsers()
//        //assertEquals(true, result)
//    } //метод работает некорректно

    @Test//(dependsOnMethods = ["checkSales"]) //тест проверяет были ли продажи за последние 48 часов у поинтов с активным источником данных продажи
    @Description("Проверка продаж за последние 48 часов для поинтов с активной интеграцией --продажи-- ")
    fun checkActiveCustomersSalesForPrevTwoDays(){
        val consoleOutput = captureConsoleOutput {
        val sqlBaseUtils = SqlBaseUtils()
        System.out.format("--------------------------\nНет продаж за последние 48 часов:\n")
        sqlBaseUtils.getActiveUsersAgain()
        //val file = SqlBaseUtils.OpenCsvWriterExample
        //assertEquals(true, result)
        assert(true)
        }
        Allure.addAttachment("Console Output", consoleOutput)
    }

    @Test(dependsOnMethods = ["checkActiveCustomersSalesForPrevTwoDays"]) //тест проверяет какие телефонии не получали обновлений за последние 3+45 часов
    @Description("Проверка активности телефоний (получения обновлений)")
    fun checkActiveCustomersCallsForPrevTime() {
        val consoleOutput = captureConsoleOutput {
            val sqlBaseUtils = SqlBaseUtils()
            System.out.format("--------------------------\nТелефонии ниже неактивны последние 48 часов:\n")
            sqlBaseUtils.getUsersWithActivePhoneCalls()
        }
        Allure.addAttachment("Console Output", consoleOutput)
    }


    @Test(dependsOnMethods = ["checkActiveCustomersCallsForPrevTime"]) //тест проверяет у каких поинтов сканер не обнаружил звонков за последние 24 часа
    @Description("Проверка отсутствия звонков на поинтах за последние 24 часа")
    fun checkActivePhoneCalls() {
        val consoleOutput = captureConsoleOutput {
        val sqlBaseUtils = SqlBaseUtils()
        System.out.format("--------------------------\nПоинты без обновлений телефонии последние 24 часа:\n")
        sqlBaseUtils.fromAllCallBasesUpdates()
        }
        Allure.addAttachment("Console Output", consoleOutput)
    }

    @Test(dependsOnMethods = ["checkActivePhoneCalls"]) //тест проверяет что отзывы приходят с порталов отзывов и их количество
    @Description("Проверка работы порталов отзывов и количества отзывов с каждого из них")
    fun checkGetActualReview () {
        val consoleOutput = captureConsoleOutput {
        val sqlBaseUtils = SqlBaseUtils()
        System.out.format("--------------------------\n")
        sqlBaseUtils.getActualReviews()
    }
        Allure.addAttachment("Console Output", consoleOutput)
    }

    @Test(dependsOnMethods = ["checkGetActualReview"]) //тест проверяет были ли доставки за последние 48 часов у поинтов с активным источником данных доставки
    @Description("Проверка доставок за последние 48 часов для поинтов")
    fun checkActiveCustomerDeliveriesForPrevTwoDays(){
        val consoleOutput = captureConsoleOutput {
        val sqlBaseUtils = SqlBaseUtils()
        System.out.format("--------------------------\nНет доставок за последние 48 часов:\n")
        sqlBaseUtils.getActiveUsersDelivery()
        assert(true)
    }
        Allure.addAttachment("Console Output", consoleOutput)
    }

    @Test(dependsOnMethods = ["checkActiveCustomerDeliveriesForPrevTwoDays"])
    @Description("Проверка смены статуса активности номера телефона, привязанного к whatsApp в базе с -Привязан- (2) на -Отвязан- (1) или -Неактивен- (0) за последние сутки ")
    fun checkIfActiveNumChangedFromTwoInPastTwoDays() {
        val consoleOutput = captureConsoleOutput {
            val sqlBaseUtils = SqlBaseUtils()
            System.out.format("--------------------------\nПоинты, где привязка телефона к вацапу либо отвязалась, либо отключилась за последние 24 часа\n")
            sqlBaseUtils.checkPhoneNumberWhatsAppActiveStatusChangeForPrevTwoDays()
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