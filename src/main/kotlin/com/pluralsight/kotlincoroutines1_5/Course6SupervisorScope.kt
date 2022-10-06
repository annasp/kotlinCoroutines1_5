package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

val exceptionHandler = CoroutineExceptionHandler { context, exception ->
    println("Exception $exception")
}

val scope1 = CoroutineScope(Job() + exceptionHandler)

fun course6SupervisorScope() = runBlocking {


    val job = scope1.launch {

        supervisorScope {
            launch {
                doWorkCourse6SupervisorScope(1)
            }
            launch {
                doWorkCourse6SupervisorScope(2)
            }

            launch {
                delay(2000)
                throw Exception()
            }
        }

        delay(5000)
    }

    job.join()
}

suspend fun doWorkCourse6SupervisorScope(i: Int) {

    while (true) {
        print(i)
        delay(200)
    }
}