package suite

import BaseTest
import FeedbackReportTest3
import ReportsTest
import ReportsTest2
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

//@ExtendWith(BaseTest::class)
//@Suite
//@SelectClasses(FeedbackReportTest3::class, ReportsTest::class, ReportsTest2::class)
class ReportsTestsSuite {
}