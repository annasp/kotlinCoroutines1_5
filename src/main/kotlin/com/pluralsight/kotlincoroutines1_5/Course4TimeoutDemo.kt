package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

fun course4TimeoutDemo() {
    useTimeoutOrNull()
}

private fun useTimeoutOrNull() = runBlocking {
    val job = withTimeoutOrNull(100) {
        repeat(1000) {
            yield()

            print(".")
            Thread.sleep(1)
        }
    }

    if (job == null) {
        println("Builder timed-out")
    }
}

private fun handleTimeoutWithTryCatch() = runBlocking{
    try {
        val job = withTimeout(100) {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        }
    } catch (ex: TimeoutCancellationException) {
        println()
        println("Handled exception: $ex")
    }
}