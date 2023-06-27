import org.junit.jupiter.api.Test
import pages.DataBase.SqlBaseUtils
import kotlin.test.assertEquals

class SQLDataBaseTests  {
    @Test
    fun checkSales() {
        val sqlBaseUtils = SqlBaseUtils()
        val result = sqlBaseUtils.checkPreviousDaySalesNotEmpty()
        assertEquals(true,result)
    }
    @Test
    fun checkActiveCustomersSales() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsers()
        //assertEquals(true, result)
    }
    @Test
    fun checkActiveCustomersSalesForPrevTwoDays(){
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsersAgain()
        //val file = SqlBaseUtils.OpenCsvWriterExample
        //assertEquals(true, result)
    }
    @Test
    fun checkActiveCustomersCallsForPrevTime() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getUsersWithActivePhoneCalls()
    }
    @Test
    fun checkActivePhoneCalls() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.fromAllCallBasesUpdates()
    }
    @Test
    fun checkGetActualReview () {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActualReviews()
    }
}