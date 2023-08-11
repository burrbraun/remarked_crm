
import org.testng.ITestResult
import org.testng.reporters.ExitCodeListener


class AllureScreenShooter: ExitCodeListener() {

        /**
         * Класс для снятия скриншотов Allure.
         *
         * @param result - это ITestResult.
         */
        override fun onTestFailure(result: ITestResult?) {
            super.onTestFailure(result)
            val allureHelpers = AllureHelpers()
            allureHelpers.takeScreenshot("screenshot")

    }

}