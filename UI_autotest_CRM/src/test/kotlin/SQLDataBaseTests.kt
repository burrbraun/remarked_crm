import org.testng.annotations.Test
import pages.SqlBaseUtils
import kotlin.test.assertEquals

class SQLDataBaseTests  {
    @Test
    fun checkSales() {
        val sqlBaseUtils = SqlBaseUtils()
        val result = sqlBaseUtils.checkPreviousDaySalesNotEmpty()
        assertEquals(true,result)
    }

    @Test(dependsOnMethods = ["checkSales"])
    fun checkActiveCustomersSales() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsers()
        //assertEquals(true, result)
    }

    @Test(dependsOnMethods = ["checkActiveCustomersSales"])
    fun checkActiveCustomersSalesForPrevTwoDays(){
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActiveUsersAgain()
        //val file = SqlBaseUtils.OpenCsvWriterExample
        //assertEquals(true, result)
    }

    @Test(dependsOnMethods = ["checkActiveCustomersSalesForPrevTwoDays"])
    fun checkActiveCustomersCallsForPrevTime() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getUsersWithActivePhoneCalls()
    }

    @Test(dependsOnMethods = ["checkActiveCustomersCallsForPrevTime"])
    fun checkActivePhoneCalls() {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.fromAllCallBasesUpdates()
    }

    @Test(dependsOnMethods = ["checkActivePhoneCalls"])
    fun checkGetActualReview () {
        val sqlBaseUtils = SqlBaseUtils()
        sqlBaseUtils.getActualReviews()
    }
}