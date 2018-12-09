package com.example.anton.sb

import com.example.anton.sb.extensions.isEmailValid
import com.example.anton.sb.extensions.isPasswordValid
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationUnitTest {
    @Test
    fun password_isCorrect() {
        assertEquals(isPasswordValid("twg2"), false)
        assertEquals(isPasswordValid("twg337kxln2"), true)
    }

    @Test
    fun email_isCorrect() {
        assertEquals(isEmailValid("twg3n2"), false)
        assertEquals(isEmailValid("aKirichenkov99@mail.ru"), true)
    }
}
