package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun course6CancellingDeferredObjects() = runBlocking {

    val job = async {
        delay(100)
        42
    }

    job.cancelAndJoin()

    // You can't call await on a deferred after it has been cancelled

    if (!job.isCancelled) {
        val result = job.await()
    } else {
        println("Already cancelled")
    }

}