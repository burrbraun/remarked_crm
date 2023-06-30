package suite

import SQLDataBaseTests
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(SQLDataBaseTests::class)
class DataBaseTestsSuite {
}