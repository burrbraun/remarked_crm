package ru.javastudy.junit


import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test



class SecondClassTest {
    @Test
    fun someSecondTest() {
        println("someSecondTest")
    }

    companion object {
        @BeforeAll
        fun beforeClass() {
            println("Before SecondClassTest.class")
        }

        @AfterAll
        fun afterClass() {
            println("After SecondClassTest.class")
        }
    }
}
