package pages

import com.codeborne.selenide.Selenide.`$`

class LoginPage {

    val loginEditBox = `$`("[class='container'] [type='text']")
    val passwordEditBox = `$`("[class='container'] [type='password']")
    val pointEditBox = `$`("[class='container'] [name='point']")
    val loginButton = `$`("[id='login-button']")

    fun setValueToLoginEditBox(newValue: String) {
        loginEditBox.value = newValue
    }

    fun setValueToPasswordEditBox(newValue: String?) {
        passwordEditBox.value = newValue
    }

    fun setValueToPointEditBox(newValue: String) {
        pointEditBox.value = newValue
    }

    fun loginButtonClick() {
        loginButton.click()
    }
}
