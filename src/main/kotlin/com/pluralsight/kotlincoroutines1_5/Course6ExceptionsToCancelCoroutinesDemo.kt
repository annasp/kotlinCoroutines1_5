package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6ExceptionsToCancelCoroutines() = runBlocking{
    coroutineScope {
        val job = launch {
            launch {
                doWorkCourse6ExceptionsToCancelCoroutines(1)
            }
            launch {
                doWorkCourse6ExceptionsToCancelCoroutines(2)
            }

            /**
             * The way the exceptions work in coroutines is the coroutine itself
             * does not rethrow the exception.Instead, what it does is it tells
             * its parent that an exception is being thrown. Then the parent
             * propagates that exception up through the parent/child hierarchy
             */
            try {
                launch {
                    // Case 2: Child throws exception, exception is propagated to parent, parent cancels all childs
                    delay(2000)
                    throw Exception("Some Exception")
                }
            } catch (t: Throwable) {
                // The exception is not actually caught
                println("Caught exception")
            }

            launch {
                // You can catch exceptions within coroutines
                // the same way that you would with any other code,
                // but don't think you can surround the coroutine
                // with an exception handler and catch the exception
                // that way.
                try {
                    delay(2000)
                    throw Exception("Some Exception")
                } catch(t: Throwable) {
                    println("Caught Exception")
                }
            }

//            Case 1: Exception is thrown in parent, parent cancels all child coroutines
//            delay(2000)
//            throw Exception("Some Exception")
        }
    }
    println("Scope ended")
}

suspend fun doWorkCourse6ExceptionsToCancelCoroutines(i: Int) {
    while (true) {
        print(i)
        delay(200)
    }
}