//import BaseTest.Companion.driver
//import org.openqa.selenium.devtools.v110.database.model.Database
//import org.testng.annotations.Test
//import pages.CommonUtils
//import java.lang.Math.ceil
//import java.sql.DriverManager
//import java.text.SimpleDateFormat
//import java.util.*
//
//class WhatsAppMonitorSegmentTest {
////    @Test
////    fun whatsAppMonitorCheck() {
//////        val db = mysqli(
//////            "185.189.167.3",
//////            "clientomer_rd",
//////            "kysbH0PmEMitG3SK",
//////            "clientomer",
//////            3310,
//////            "mysql:host=185.189.167.3;port=3310;dbname=clientomer;charset=utf8"
//////        )
////        val myUrl = "jdbc:mysql://185.189.167.3:3310/clientomer?serverTimezone=UTC"
////        val conn = DriverManager.getConnection(myUrl,  "clientomer_rd", "kysbH0PmEMitG3SK" )
////
////        val sql = "SELECT * FROM sms_by_segment_settings WHERE type = whatsapp AND active = 1"
////        val dateFrom = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date().time - 90000)
////        val dateTo = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
////        val st = conn.createStatement()
////
////        val res = st.executeQuery(sql)
////        while (res.next()) {
////            val item = res.getInt("segment_id")
////
////            val sqlSegment = "SELECT * FROM segments_cache WHERE segment_id = $item"
////            val resSegment = st.executeQuery(sqlSegment)
////            val itemSegment = resSegment.getInt("with_allowed_phone_count")
////            var itemSegmentNum = itemSegment("with_allowed_phone_count")
////            if (itemSegmentNum == "") {
////                itemSegmentNum = 0
////            }
////            val itemSegmentNumDate = if (itemSegment["date"] != "" && itemSegment["date"] != "0000-00-00 00:00:00") {
////                itemSegment["date"]
////            } else {
////                "0"
////            }
////
////            var historyClass = ""
////            var queueClass = ""
////            var errorClass = ""
////            var successClass = ""
////
////            val sqlError = "SELECT COUNT(id) FROM sms_by_segment_stats WHERE cfg_id = $item AND (date BETWEEN $dateFrom AND $dateTo) AND internal_status = error"
////            val resError = db.query(sqlError)
////            val itemError = resError.fetchArray()
////            val itemErrorNum = itemError[0]
////
////            val sqlSent = "SELECT COUNT(id) FROM `sms_by_segment_stats` WHERE cfg_id = $item AND (date BETWEEN $dateFrom AND $dateTo) AND internal_status = history"
////            val resSent = db.query(sqlSent)
////            val itemSent = resSent.fetchArray()
////            val itemSentNum = itemSent[0]
////            val tmpSentNum = itemSentNum - itemErrorNum
////            var percent = 0
////            if (itemSegmentNum >= tmpSentNum) {
////                percent = (100 - ceil((itemSegmentNum - tmpSentNum) / itemSegmentNum * 100)).toInt()
////            } else if (itemSegmentNum <= tmpSentNum) {
////                percent = (tmpSentNum - itemSegmentNum) / itemSegmentNum * 100
////            } else if (itemSegmentNum == tmpSentNum) {
////                percent = 100
////            }
////            if (itemSegmentNum == 0) {
////                percent = 0
////            } else {
////                historyClass = if (percent >= 60) "" else "text-red"
////                successClass = if (percent >= 60) "block-green" else ""
////            }
////
////            val sqlQueue = "SELECT COUNT(id) FROM `sms_by_segment_stats` WHERE `cfg_id` = ${item["id"]} AND (`date` BETWEEN '$dateFrom' AND '$dateTo') AND internal_status = 'queue'"
////            val resQueue = db.query(sqlQueue)
////            val itemQueue = resQueue.fetchArray()
////            val itemQueueNum = itemQueue[0]
////
////            println("")
////            println("")
////            println("")
////            println("")
////            println("")
////        }
////    }
//    @Test
//    fun whatsAppMonitorCheck2() {
//    val utils = CommonUtils()
//    val password = utils.encryptionPasswords(System.getenv("DATABASE_PASSWORD"))
//    val myUrl = "jdbc:mysql://95.143.188.9:3310/clientomer?serverTimezone=UTC"
//    val conn = DriverManager.getConnection(myUrl, "jrsl_sales", password)
//
//    val sql = "SELECT * FROM sms_by_segment_settings WHERE type = 'whatsapp' AND active = 1"
//    val dateFrom = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date().time - 90000)
//    val dateTo = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
//    val st = conn.createStatement()
//
//    val res = st.executeQuery(sql)
//    while (res. ()) {
//        var item = res.getInt("segment_id")
//        val st2 = conn.createStatement()
//        val sqlSegment = "SELECT * FROM segments_cache WHERE segment_id = $item"
//        val resSegment = st.executeQuery(sqlSegment)
//        if (resSegment.next()) {
//            val itemSegment = resSegment.getInt("with_allowed_phone_count")
//            var itemSegmentNum = itemSegment
//            if (itemSegmentNum == 0) {
//                itemSegmentNum = 0
//            }
//            val itemSegmentNumDate = if (resSegment.getString("date") != "" && resSegment.getString("date") != "0000-00-00 00:00:00") {
//                resSegment.getString("date")
//            } else {
//                "0"
//            }
//
//            var historyClass = ""
//            var queueClass = ""
//            var errorClass = ""
//            var successClass = ""
//
//            val sqlError = "SELECT COUNT(id) FROM sms_by_segment_stats WHERE cfg_id = $item AND date BETWEEN '$dateFrom' AND '$dateTo' AND internal_status = 'error'"
//            val resError = st.executeQuery(sqlError)
//            if (resError.next()) {
//                val itemErrorNum = resError.getInt(1)
//
//                val sqlSent = "SELECT COUNT(id) FROM sms_by_segment_stats WHERE cfg_id = $item AND date BETWEEN '$dateFrom' AND '$dateTo' AND internal_status = 'history'"
//                val resSent = st.executeQuery(sqlSent)
//                if (resSent.next()) {
//                    val itemSentNum = resSent.getInt(1)
//                    val tmpSentNum = itemSentNum - itemErrorNum
//                    var percent = 0
//                    if (itemSegmentNum >= tmpSentNum) {
//                        percent = (100 - ceil((itemSegmentNum - tmpSentNum) / itemSegmentNum.toDouble() * 100)).toInt()
//                    } else if (itemSegmentNum <= tmpSentNum) {
//                        percent = ((tmpSentNum - itemSegmentNum) / itemSegmentNum.toDouble() * 100).toInt()
//                    } else if (itemSegmentNum == tmpSentNum) {
//                        percent = 100
//                    }
//                    if (itemSegmentNum == 0) {
//                        percent = 0
//                    } else {
//                        historyClass = if (percent >= 60) "" else "text-red"
//                        successClass = if (percent >= 60) "block-green" else ""
//                    }
//
//                    val sqlQueue = "SELECT COUNT(id) FROM sms_by_segment_stats WHERE cfg_id = $item AND date BETWEEN '$dateFrom' AND '$dateTo' AND internal_status = 'queue'"
//                    val resQueue = st.executeQuery(sqlQueue)
//                    if (resQueue.next()) {
//                        val itemQueueNum = resQueue.getInt(1)
//
//                        println("")
//                        println("")
//                        println("")
//                        println("")
//                        println("")
//                    }
//                    resQueue.close()
//                }
//                resSent.close()
//            }
//            resError.close()
//        }
//        resSegment.close()
//        st2.close()
//    }
//    res.close()
//
//    st.close()
//    conn.close()
//}
//
//}