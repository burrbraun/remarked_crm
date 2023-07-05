import org.testng.annotations.Test
import pages.DataBase.SqlBaseUtils
import kotlin.test.assertEquals

class SQLDataBaseTests  {
    @Test(priority=1)
    fun checkSales() {
        val sqlBaseUtils = SqlBaseUtils()
        val result = sqlBaseUtils.checkPreviousDaySalesNotEmpty()
        assertEquals(true,result)
    }

    @Test(priority=2)
    fun checkActiveCustomersSales() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsers()
        //assertEquals(true, result)
    }

    @Test(priority=3)
    fun checkActiveCustomersSalesForPrevTwoDays(){
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsersAgain()
        //val file = SqlBaseUtils.OpenCsvWriterExample
        //assertEquals(true, result)
    }

    @Test(priority=4)
    fun checkActiveCustomersCallsForPrevTime() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getUsersWithActivePhoneCalls()
    }

    @Test(priority=5)
    fun checkActivePhoneCalls() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.fromAllCallBasesUpdates()
    }

    @Test(priority=6)
    fun checkGetActualReview () {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActualReviews()
    }
}