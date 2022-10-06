package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Coroutine builders are bridges between non-suspending and suspending world
 * Normal main function is not a suspend function.
 * There are global builders, however, these should not generally be used
 */

fun course3CoroutineBuilders() = runBlocking {
    launch {
        delay(1000)
        println("World")
    }
    print("Hello, ")

    doWork()
    delay(1500)

}

suspend fun doWork() {
    delay(1500)
}
