import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite
import org.junit.runner.RunWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

@Suite
@SelectClasses(CRMCallsTest::class, CRMSmokeTest::class, CRMWifiAccessTest::class)
class CrmTestsSuite {
}
