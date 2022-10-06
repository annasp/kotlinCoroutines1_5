package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * If async is the root coroutine and the code inside the async coroutine
 * throws an exception, then that exception is thrown when we await on the coroutine.
 * If the async is a child coroutine, then the exception will propagate as soon it is thrown.
 */


val scope3 = CoroutineScope(Job())

fun course6ExceptionsWithAsyncBuilder() = runBlocking {

    val job = scope3.launch {
        /**
         * If an async coroutine is a non-root coroutine, it will always
         * notify its parent that an exception is thrown
         * and that exception will then get notified upwards until,
         * on this case. it hits the default exception handler.
         * If the async is a top-level coroutine and there is nothing to notify to,
         * the exceptions gets thrown when you call await to the deffered.
         */
        val deferred = scope3.async {
            delay(1000)
            throw Exception()
        }

        try {
            deferred.await()
        } catch (t: Throwable) {
            println("Caught $t")
        }
    }

    job.join()
}