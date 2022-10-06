package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun course6ExtendingCancellation() = runBlocking {
    val job = launch {
        try {
            repeat(1000) {
                delay(10)
                print(".")
            }
        } catch (ex: CancellationException) {
            println("Exception!!!")
            withContext(NonCancellable) {
                reportError()
            }
        }
    }

    delay(1000)
    job.cancelAndJoin()
}

suspend fun reportError() {
    println("Reporting error")
    try {
        delay(10)
    } catch (t: Throwable) {
        println(t)
    }
    println("Reported error")
}