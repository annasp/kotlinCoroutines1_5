package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course4CancellingDemo() = runBlocking {
    val job = launch {
        repeat(1000) {
            // The cooperation with cancel happens because delay is used here
            delay(10) // built in suspend function
            print(".")
        }
    }

    delay(250)
//    job.cancel()
//    job.join()
    job.cancelAndJoin()
    println("Done")
}