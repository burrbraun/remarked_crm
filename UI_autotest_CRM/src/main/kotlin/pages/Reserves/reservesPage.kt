package pages.Reserves

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class reservesPage {

    fun generateRandomPhoneNumbers(count: Int): List<String> {
        val phoneNumbers = mutableListOf<String>()

        repeat(count) {
            val phoneNumber = StringBuilder()
            phoneNumber.append("999")

            repeat(7) {
                phoneNumber.append(Random.nextInt(7))

                if (it == 1 || it == 4 || it == 7) {
                    phoneNumber.append("")
                }
            }

            phoneNumbers.add(phoneNumber.toString())
        }

        return phoneNumbers
    }
fun buttonToCreateReserve() {
    val createReserveButton = Selenide.`$`("button[class='btn btn-success waves-effect waves-float waves-light header-site__add-open']")
    createReserveButton.click()
}
    fun fulfillSurnameInformation(newValue: String?) {
        val fulfillSurname = Selenide.`$`("[class = 'col-6 mb-1'] [name = 'surname']")
        fulfillSurname.value = newValue
    }
    fun fulfillName(newValue: String?) {
        val fulfillName = Selenide.`$`("[class = 'col-6 mb-1'] [name = 'name']")
        fulfillName.value = newValue
    }
    fun fulfillPhone(newValue: String?) {
        val fulfillPhone = Selenide.`$`("[class = 'col-6 mb-1'] [class = 'crm-phone-search'] [class = 'crm-phone-search__block'] [class = 'crm-phone-search__mask form-control']")
        fulfillPhone.value = newValue
    }
    fun fulfillDate(newValue: String?) {
        val dateField = Selenide.`$`("[class = 'col-6 mb-1'] [class = 'modal-form__inputs'] [class = 'modal-form__input modal-form__input-reserve_date'] [name = 'reserve_date']")
        dateField.value = newValue
    }
    fun fulfillTime() {
        val timeField = Selenide.`$`(".modal-form__input.modal-form__input-reserve_time")
        timeField.click()
        sleep(2000)
        val hoursFieldCollection = `$$`(".timemodal__item.col-4")
        println("$hoursFieldCollection")
        // Расчет времени с округлением до ближайшего 15-минутного интервала
        val dt: LocalDateTime = LocalDateTime.now().plusMinutes(15).truncatedTo(ChronoUnit.HOURS).plusMinutes((15 - LocalDateTime.now().minute % 15).toLong())
        sleep(2000)
        // Перебор элементов списка часов
        for (item in hoursFieldCollection) {
            val itemHour = item.getAttribute("textContent")?.toInt()
            if (dt.hour == itemHour) {
                item.click()
                break
            }

        }
        val minutesFieldCollection = timeField.`$$`(".timemodal__item.col-6")
        for (item in minutesFieldCollection) {
            val itemMinute = item.getAttribute("textContent")?.toInt()
            if (dt.minute+15 == itemMinute) {
                item.click()
                break
            }
        }
        sleep(3000)
        val okButton = `$`("[class= 'modal-footer'] [class = 'btn btn-primary timemodal__button']")
        sleep(2000)
        okButton.click()

    }
    fun chooseHall() {
        val hallList = `$`("[class = 'room-fake default-room-fake']")
        hallList.click()
        sleep(4000)
        val chooseHall = `$`("[data-room-id = '496521']")
        sleep(3000)
        chooseHall.click()

        val chooseTable = `$`("[data-table-id = '29646533226431']")
        chooseTable.click()

        val okButton = `$`("#reserveRoomsTablesModal .waves-light")
        okButton.click()

    }
    fun chooseResponsible() {
        val responsibleList = `$$`("[class = 'select2-selection select2-selection--single'] ")
        val chooseResponsible = responsibleList[4]
        chooseResponsible.click()
        sleep(2000)
        val responsiblePerson = `$$`("[class = 'select2-results__option']")
        val selectResponsible = responsiblePerson[0]
        selectResponsible.click()
    }

    fun reserveStatusChange() {
        val reserveListOpen = `$`("[class = 'btn-group dropup']")
        reserveListOpen.click()
        val status = `$$`(".inner_status-dropdown-menu>.dropdown-item")
        for (item in status) {
            if (item.getAttribute("data-inner-status") == "confirmed") {
                item.click()
                break
            }
        }
    }
    fun confirmReserve() {
        val confirmReserveButton = `$`("[id = 'modalReserveFormSubmit']")
        confirmReserveButton.click()
        sleep(2000)
    }
   fun closeSuccessReserveCreateWindow() {
       val closeSuccessWindow = `$`("[id = 'successModalTitle'][class = 'btn-close']")
       sleep(2000)
       closeSuccessWindow.click()
   }

}

