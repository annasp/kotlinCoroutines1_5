package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Course3CoroutineBuildersTest {

    @Test
    fun firstTest() = runBlocking {
        doWork()
        assertEquals(2, 1 + 1)
    }
}