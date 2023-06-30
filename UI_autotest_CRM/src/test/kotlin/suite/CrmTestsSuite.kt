package suite

import BaseTest
import CRMCallsTest
import CRMSmokeTest
import CRMWifiAccessTest
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(BaseTest::class, CRMCallsTest::class, CRMSmokeTest::class, CRMWifiAccessTest::class)
class CrmTestsSuite {
}