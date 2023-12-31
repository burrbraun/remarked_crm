package pages

import com.opencsv.CSVWriter
import io.qameta.allure.Allure
import io.qameta.allure.Allure.step
import io.qameta.allure.Step
import java.io.FileWriter
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SqlBaseUtils {
    val utils = CommonUtils()
    val login = "jrsl_sales"
    val password = utils.encryptionPasswords(System.getenv("DATABASE_PASSWORD"))
    fun checkPreviousDaySalesNotEmpty(): Boolean {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)

        try {
            // create our mysql database connection
            val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
            val conn = DriverManager.getConnection(myUrl, login, password )
            val query = "SELECT id FROM cl_purchases WHERE date < '$now' LIMIT 5"
            val st = conn.createStatement()
            val rs = st.executeQuery(query)
            if (rs != null) {
                return true
            }
            st.close()
            conn.close()

        } catch (e: Exception) {
            System.err.println("Got an exception! ")
            System.err.println(e.message)
        }
        return false
    }

    fun getActiveSalesForAllUsers() { //КРИВОй метод для проверки сканера продаж, заменен на getActiveUsersAgain
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val currentDate = LocalDateTime.now()
        val now = LocalDateTime.now().minusHours(48)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl,  login, password )
            val query =
            "SELECT DISTINCT clients_sources.point FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id WHERE clients_sources.source_type LIKE 'sales' AND clients_sources.source_active=1 AND clients_cabinet.active=1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)

        while (rs.next()) {
            var pointId = rs.getInt("point_id")
            val stPurchases = conn.createStatement()
            val queryPurchases =
                "SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point IN ($pointId) AND cl_purchases.date < (CURDATE() - 2) LIMIT 1"
            val st2 = conn.createStatement()
            val rs2 = st.executeQuery(query)

        }
    }


    fun getActiveUsersAgain() //тест проверяет были ли продажи за последние 24 часов у поинтов с активным источником данных продажи
    {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl,  login, password )
        val query =
            "SELECT DISTINCT clients_sources.point , clients_config.name , clients_sources.source_providers FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id JOIN clients_config ON clients_cabinet.client_id = clients_config.id WHERE clients_sources.source_type LIKE 'sales' AND clients_sources.source_active=1 AND clients_cabinet.active=1 AND clients_sources.point <> clients_sources.guest_point AND clients_sources.source_providers NOT IN ('iikodelivery', 'iikocloud')"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)
        val stPurchases = conn.createStatement()
        while (rs.next()) {
            var pointId = rs.getInt("point")
            var name = rs.getString("name")
            var sourceProviders = rs.getString("source_providers")

            val queryPurchases =
                "SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point = $pointId AND order_type = 'sales' AND cl_purchases.date > (CURDATE() - 1)  LIMIT 1"
            val result = stPurchases.executeQuery(queryPurchases)

            if (!result.next())  {
                println(" https://cabinet.clientomer.ru/$pointId/sales.list/ , $sourceProviders , $pointId , $name ")

            }
        }
            stPurchases.close()
            st.close()
        conn.close()
    }




    fun getUsersWithActivePhoneCalls() //тест проверяет какие телефонии не получали обновлений за последние 3+45 часов
    {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl,  login, password )
        val query = "SELECT DISTINCT source_providers  FROM clients_sources WHERE source_type  = 'telephony' AND source_active = 1"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)
        val stCalls = conn.createStatement()
        while (rs.next()) {
            var operator = rs.getString("source_providers")

            val queryCalls = "SELECT source_providers FROM clients_sources WHERE source_providers = '$operator' AND source_update_date > (CURDATE() - INTERVAL 45 HOUR)"
            val result = stCalls.executeQuery(queryCalls)

            if (!result.next()) {
                System.out.format("%s\n", operator)

            }
        }
        stCalls.close()
        st.close()
        conn.close()
    }

    fun fromAllCallBasesUpdates() {
        val pointsWithNames = getPointsWithActiveCalls() // Get the list of points with active calls and names
        val tableNames = listOf(
            "telephony_alloka_data", "telephony_callibri_calls_data", "telephony_calltouch_data",
            "telephony_default_data", "telephony_domru_data", "telephony_mtt_data", "telephony_obit_data",
            "telephony_onlinepbx_data", "telephony_sipuni_data", "telephony_telphin_data",
            "telephony_westcall_data", "telephony_zebratelecom_data"
        )
        val startDate = LocalDate.now().minusDays(1)
        val connection = getConnection() // Get the database connection

        val numbersWithoutUpdates = mutableListOf<Pair<String, String>>() // List of (point, name) pairs

        pointsWithNames.forEach { (point, name) ->
            var hasUpdates = false
            tableNames.forEach { tableName ->
                val rowsCount = getRowsCount(connection, tableName, point, startDate)
                if (rowsCount > 0) {
                    hasUpdates = true
                    return@forEach
                }
            }
            if (!hasUpdates) {
                numbersWithoutUpdates.add(point to name) // Add the (point, name) pair to the list
            }
        }

        numbersWithoutUpdates.forEach { (point, name) ->
            println("https://cabinet.clientomer.ru/$point/integration.telephony.calls/ $point, $name") // Print the point and name
        }

        connection.close() // Close the database connection
    }

private fun getConnection(): Connection {
    val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
    val conn = DriverManager.getConnection(myUrl,  login, password )
    return conn
}

    private fun getPointsWithActiveCalls(): List<Pair<String, String>> {
        val connection = getConnection()
        val statement = connection.createStatement()
        val sql = "SELECT clients_sources.point, clients_config.name FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id JOIN clients_config ON clients_cabinet.client_id = clients_config.id WHERE clients_sources.source_type LIKE 'telephony' AND clients_sources.source_active=1 AND clients_cabinet.active=1 AND clients_sources.source_providers NOT LIKE 'mt%'"
        val resultSet = statement.executeQuery(sql)
        val pointsWithActiveCalls = mutableListOf<Pair<String, String>>()
        while (resultSet.next()) {
            val point = resultSet.getString("point")
            val name = resultSet.getString("name")
            pointsWithActiveCalls.add(point to name)
        }
        statement.close()
        connection.close()
        return pointsWithActiveCalls
    }

private fun getRowsCount(connection: Connection, tableName: String, point: String, startDate: LocalDate): Int {
    val statement = connection.createStatement()
    val sql = "SELECT DISTINCT COUNT(*) FROM $tableName JOIN clients_cabinet ON $tableName.point = clients_cabinet.point_id JOIN clients_config ON clients_cabinet.client_id = clients_config.id WHERE point='$point' AND date >= '${startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}' LIMIT 1"
    val resultSet = statement.executeQuery(sql)
    resultSet.next()
    val rowsCount = resultSet.getInt(1)
    statement.close()
    return rowsCount
}

    fun getActualReviews() {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val connection = DriverManager.getConnection(myUrl,  login, password )
        val statement = connection.createStatement()
        val sources = mutableListOf<String>()
        val sourceQuery =  "SELECT DISTINCT source FROM feedback WHERE source NOT LIKE '' ORDER BY source ASC"
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
        val sortedReviewsCounts = reviewsCounts.toSortedMap(compareBy { it.toLowerCase() })
        for ((source, count) in sortedReviewsCounts) {
            println("Источник: $source, количество отзывов за последние 72 часа: $count ;")
        }
        statement.close()
        connection.close()
    }

    fun getActiveUsersDelivery() //тест проверяет были ли доставки за последние 48 часов у поинтов с активным источником данных доставки
    {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        // try {
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
        val conn = DriverManager.getConnection(myUrl,  login, password )
        val query =
            "SELECT DISTINCT clients_sources.point, clients_config.name, clients_sources.source_providers FROM clients_sources JOIN clients_cabinet ON clients_sources.point = clients_cabinet.point_id JOIN clients_config ON clients_cabinet.client_id = clients_config.id WHERE clients_sources.source_type LIKE 'sales' AND clients_sources.source_active = 1 AND clients_cabinet.active = 1 AND clients_sources.point <> clients_sources.guest_point AND clients_sources.source_providers IN ('iikodelivery', 'iikocloud')"
        val st = conn.createStatement()
        val rs = st.executeQuery(query)
        val stPurchases = conn.createStatement()
        while (rs.next()) {
            var pointId = rs.getInt("point")
            var name = rs.getString("name")
            var sourceProviders = rs.getString("source_providers")

            val queryPurchases =
                "SELECT cl_purchases.point FROM cl_purchases WHERE cl_purchases.point = $pointId AND order_type = 'delivery' AND cl_purchases.date > (CURDATE() - 1)  LIMIT 1"
            val result = stPurchases.executeQuery(queryPurchases)

            if (!result.next())  {
                println(" https://cabinet.clientomer.ru/$pointId/delivery.list/ , $sourceProviders ,  $pointId , $name ")


            }
        }
        stPurchases.close()
        st.close()
        conn.close()

    }

    fun checkWhatsAppFeedbackCollectingForPrev25Hours() {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val now = LocalDateTime.now().minusHours(24)
        val time = dtf.format(now)
        val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"

        try {
            val conn = DriverManager.getConnection(myUrl, login, password)
            val query = "SELECT point , clients_config.name FROM clients_sources JOIN clients_cabinet ON clients_cabinet.point_id = clients_sources.`point` JOIN clients_config ON clients_config.id = clients_cabinet.client_id WHERE source_providers = 'feedbackaftervisit' AND source_active = 1 AND point NOT IN ( SELECT DISTINCT point FROM cl_guests_chats WHERE date > (NOW() - INTERVAL 25 HOUR))"
            val st = conn.createStatement()
            val rs = st.executeQuery(query)

            while (rs.next()) {
                val point = rs.getInt("point")
                val name = rs.getString("name")
                System.out.format("https://cabinet.clientomer.ru/$point/chat.app/ $point $name \n")
            }

            rs.close()
            st.close()
            conn.close()
        } catch (e: SQLException) {
            // Обработка исключения
            e.printStackTrace()
        }
    }
    fun checkPhoneNumberWhatsAppActiveStatusChangeForPrevTwoDays() {
            val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val now = LocalDateTime.now().minusHours(24)
            val time = dtf.format(now)
            val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"

            try {
                step("Подключаемся к базе")
                val conn = DriverManager.getConnection(myUrl, login, password)
                step("Отправляем запрос к базе чтобы получить все поинты и номера телефонов, у которых номер либо отвязался, либо стал неактивен за последний 1 день (меньший период нельзя, т.к. информация обновляется раз в сутки в полночь предыдущего дня)")
                val query = "SELECT DISTINCT point, phone FROM cl_whatsapp_accounts WHERE active < 2 AND notify_date >= (CURDATE() - 1)"
                val st = conn.createStatement()
                val rs = st.executeQuery(query)

                while (rs.next()) {
                    val point = rs.getInt("point")
                    System.out.println("https://cabinet.clientomer.ru/$point/chat.app/ $point")
                }

                rs.close()
                st.close()
                conn.close()
            } catch (e: SQLException) {
                // Обработка исключения
                e.printStackTrace()
            }


    }


}