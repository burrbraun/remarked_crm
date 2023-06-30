package ru.javastudy.junit

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.platform.suite.api.Suite


class SecondClassTest {
    @Test
    fun someSecondTest() {
        println("someSecondTest")
    }

    companion object {
        @BeforeClass
        fun beforeClass() {
            println("Before SecondClassTest.class")
        }

        @AfterClass
        fun afterClass() {
            println("After SecondClassTest.class")
        }
    }
}
