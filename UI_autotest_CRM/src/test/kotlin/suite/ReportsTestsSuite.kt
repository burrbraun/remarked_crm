package suite

import FeedbackReportTest3
import ReportsTest
import ReportsTest2
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(FeedbackReportTest3::class, ReportsTest::class, ReportsTest2::class)
class ReportsTestsSuite {
}