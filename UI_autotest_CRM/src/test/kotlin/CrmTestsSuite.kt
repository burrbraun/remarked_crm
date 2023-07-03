import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.runner.RunWith
import org.junit.runners.Suite


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

@RunWith(Suite::class)
@Suite.SuiteClasses(CRMCallsTest::class, CRMSmokeTest::class, CRMWifiAccessTest::class)
class CrmTestsSuite {
}
