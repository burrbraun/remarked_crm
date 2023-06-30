package pages.DataBase

import com.opencsv.CSVWriter
import java.io.FileWriter
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SqlBaseUtils {
    fun checkPreviousDaySalesNotEmpty(): Boolean {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)

        try {
            // create our mysql database connection
            val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
            val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
            )
            val query = "SELECT id FROM cl_purchases WHERE date < '$now' LIMIT 5"
            val st = conn.createStatement()
            val rs = st.executeQuery(query)
            if (rs != null) {
                return true
            }
            st.close()

            // iterate through the java resultset
            /*while (rs.next()) {
                val id = rs.getInt("id")
                //if id != now

                // print the results
                System.out.format("%s\n", id)
            }
            st.close()*/
        } catch (e: Exception) {
            System.err.println("Got an exception! ")
            System.err.println(e.message)
        }
        return false
    }

    fun getActiveUsers() {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
        )
        val query = "SELECT point_id FROM clients_cabinet WHERE active = 1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)

        while (rs.next()) {
            var pointId = rs.getInt("point_id")
            val stPurchases = conn.createStatement()
            val queryPurchases =
                "SELECT id FROM cl_purchases WHERE point = '$pointId' AND date < '$time' LIMIT 1"
            val result = stPurchases.executeQuery(queryPurchases)
            var sizeOfThePurchaseTable = 0
            if (result != null) {
                result.last()
                sizeOfThePurchaseTable = result.row
                System.out.format("%s active\n", pointId) //проверяю, какие поинты попадают в цикл
            }

            if (sizeOfThePurchaseTable == 0) {
                System.out.format("%s disabled\n", pointId)
            }
            stPurchases.close()
        }
        st.close()


        //} catch (e: Exception)
        /*{
        System.err.println("Got an exception! ")
        System.err.println(e.message)
    }*/

        // }
    }

    fun getActiveSalesForAllUsers() {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val currentDate = LocalDateTime.now()
        val now = LocalDateTime.now().minusHours(48)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
        )
            val query =
            "SELECT DISTINCT clients_sources.point FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id WHERE clients_sources.source_type LIKE 'sales' AND clients_sources.source_active=1 AND clients_cabinet.active=1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)

        while (rs.next()) {
            var pointId = rs.getInt("point_id")
            val stPurchases = conn.createStatement()
            val queryPurchases =
                "SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point IN ($pointId) AND cl_purchases.date < (CURDATE() - 2) LIMIT 1"
            //"SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point IN ($pointId) AND cl_purchases.date = (CURRENT DATE - 2) GROUP BY cl_purchases.point HAVING COUNT(point)>0"
            val st2 = conn.createStatement()
            val rs2 = st.executeQuery(query)
            /*while (rs2.next()) {
                var pointId2 = rs2.getInt("point_id")
                val stPurchases2 = conn.createStatement()
                val queryPurchases2 = ""
            }*/

        }
    }

    fun main() {
        val csvData = createCsvDataSimple()
        CSVWriter(FileWriter("/Users/Shared/test/test.csv")).use { writer -> writer.writeAll(csvData) }
    }

    private fun createCsvDataSimple(): List<Array<String>> {
        val header = arrayOf("id", "name", "address", "phone")
        val record1 = arrayOf("1", "first name", "address 1", "11111")
        val record2 = arrayOf("2", "second name", "address 2", "22222")
        val list: MutableList<Array<String>> = ArrayList()
        list.add(header)
        list.add(record1)
        list.add(record2)
        return list
    }

    fun getActiveUsersAgain()  {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
        )
        val query =
            "SELECT DISTINCT clients_sources.point FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id WHERE clients_sources.source_type LIKE 'sales' AND clients_sources.source_active=1 AND clients_cabinet.active=1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)
        val stPurchases = conn.createStatement()
        while (rs.next()) {
            var pointId = rs.getInt("point")

            val queryPurchases =
                "SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point = $pointId AND cl_purchases.date < (CURDATE() - 2)  LIMIT 1"
            val result = stPurchases.executeQuery(queryPurchases)

            if (!result.next())  {
                System.out.format("%s  \n", pointId)

               // return false
            }
        }
            stPurchases.close()
            st.close()
   //     return true
    }


/*        fun main(args: Array<String>) {
            val csvData = createCsvDataSimple()
            CSVWriter(FileWriter("/Users/Shared/test")).use { writer -> writer.writeAll(csvData) }
        }

        fun createCsvDataSimple(): List<Array<String>> {
            val header = arrayOf("point")
            val record1 = arrayOf("1")
            val list: MutableList<Array<String>> = ArrayList()
            list.add(header)
            list.add(record1)

            return list
        }*/

    fun getUsersWithActivePhoneCalls() {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
        )
        val query = "SELECT DISTINCT source_providers  FROM clients_sources WHERE source_type  = 'telephony' AND source_active = 1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)
        val stCalls = conn.createStatement()
        while (rs.next()) {
            var operator = rs.getString("source_providers")

            val queryCalls = "SELECT source_providers FROM clients_sources WHERE source_providers = '$operator' AND source_update_date > (CURDATE() - INTERVAL 45 HOUR)"
            val result = stCalls.executeQuery(queryCalls)

            if (!result.next()) {
                System.out.format("%s  not active\n", operator)

            }
        }
        stCalls.close()
        st.close()
    }

//    fun getActiveCallsBaseUpdates() {
//        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val now = LocalDateTime.now().minusHours(24)
//        val time = dtf.format(now)
//        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
//        val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
//        )
//        val pointsAndOperatorsWithActiveCalls = "SELECT clients_sources.point, clients_sources.source_providers FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id WHERE clients_sources.source_type LIKE 'telephony' AND clients_sources.source_active=1 AND clients_cabinet.active=1"
//        val st = conn.createStatement()
//        val rs = st.executeQuery(pointsAndOperatorsWithActiveCalls)
//        //val stCalls = conn.createStatement()
//        //val operators = listOf("alloka", "calltouch", "domru", "onlinepbx", "sipuni", "telphin", "westcall", "zebratelecom")
//        val tableNames = listOf("telephony_alloka_data", "telephony_callibri_calls_data","telephony_calltouch_data","telephony_default_data","telephony_domru_data","telephony_mtt_data","telephony_obit_data","telephony_onlinepbx_data","telephony_sipuni_data","telephony_telphin_data","telephony_westcall_data","telephony_zebratelecom_data")
//
//            pointsAndOperatorsWithActiveCalls.forEach{point ->
//                tableNames.forEach{
//                tableName ->
//                val rowsCount = getRowsCount(tableName, point, now)
//                println("Таблица: $tableName, POINT: $point, Количество новых строк за последние два дня: $rowsCount")
//            }
//            }
//
//        st.close() // Закрываем соединение с базой данных
//    }
//            }
//                println(it)
//            }




fun fromAllCallBasesUpdates() {
    val points = getPointsWithActiveCalls() // Получаем список поинтов с активной телефонией
    val tableNames = listOf("telephony_alloka_data", "telephony_callibri_calls_data","telephony_calltouch_data","telephony_default_data","telephony_domru_data","telephony_mtt_data","telephony_obit_data","telephony_onlinepbx_data","telephony_sipuni_data","telephony_telphin_data","telephony_westcall_data","telephony_zebratelecom_data")
    val startDate = LocalDate.now().minusDays(1)
    val connection = getConnection() // Получаем соединение с базой данных

    val numbersWithoutUpdates = mutableListOf<String>()

    points.forEach { point ->
        var hasUpdates = false
        tableNames.forEach { tableName ->
            val rowsCount = getRowsCount(connection, tableName, point, startDate)
            if (rowsCount > 0) {
                hasUpdates = true
                return@forEach
            }
        }
        if (!hasUpdates) {
            numbersWithoutUpdates.add(point)
        }
    }

    numbersWithoutUpdates.forEach { point ->
        println("Поинт без обновлений последние 24 часа: $point")
    }

    connection.close() // Закрываем соединение с базой данных
}

private fun getConnection(): Connection {
    val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
    val conn = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
    )
    return conn
}

private fun getPointsWithActiveCalls(): List<String> {
    val connection = getConnection()
    val statement = connection.createStatement()
    val sql = "SELECT clients_sources.point FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id WHERE clients_sources.source_type LIKE 'telephony' AND clients_sources.source_active=1 AND clients_cabinet.active=1 AND clients_sources.source_providers NOT LIKE 'mt%'"
    val resultSet = statement.executeQuery(sql)
    val pointsWithActiveCalls = mutableListOf<String>()
    while (resultSet.next()) {
        pointsWithActiveCalls.add(resultSet.getString("point"))
    }
    statement.close()
    connection.close()
    return pointsWithActiveCalls
}

private fun getRowsCount(connection: Connection, tableName: String, point: String, startDate: LocalDate): Int {
    val statement = connection.createStatement()
    val sql = "SELECT DISTINCT COUNT(*) FROM $tableName WHERE point='$point' AND date >= '${startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}' LIMIT 1"
    val resultSet = statement.executeQuery(sql)
    resultSet.next()
    val rowsCount = resultSet.getInt(1)
    statement.close()
    return rowsCount
}

    fun getActualReviews() {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val connection = DriverManager.getConnection(myUrl, // здесь были логин и пароль )
        )
        val statement = connection.createStatement()
        val sources = mutableListOf<String>()
        val sourceQuery =  "SELECT DISTINCT source FROM feedback WHERE source NOT LIKE ''"
        val sourceResultSet = statement.executeQuery(sourceQuery)
        while (sourceResultSet.next()) {
            val source = sourceResultSet.getString("source")
            sources.add(source)
        }
        val reviewsCounts = mutableMapOf<String, Int>()
        for (source in sources) {
            val countQuery = "SELECT COUNT(*) AS rows_count FROM feedback WHERE source = '$source' AND time >= (CURDATE() - 3)"
            val countResultSet = statement.executeQuery(countQuery)
            if (countResultSet.next()) {
                val count = countResultSet.getInt("rows_count")
                reviewsCounts[source] = count
            }
        }
        for ((source, count) in reviewsCounts) {
            println("Источник: $source, количество отзывов за последние 72 часа: $count")
        }
        statement.close()
        connection.close()
    }

}