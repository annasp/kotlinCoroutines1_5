package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6SupervisorJob() = runBlocking {
    val scope = CoroutineScope(SupervisorJob())

    val job = launch() {

        // By calling scope.launch, breaks the parent/child hierarchy
        // and each of these things becomes its own independent
        // coroutine run as part of the scope.
        // Each of these, when they get created, a job will be created.
        // The direct parent will be a Job
        // but then the grandparent will be a SupervisorJob
        // and that SupervisorJob won't propagate the exception any further
        scope.launch {
            doWorkCourse6Supervisor(1)
        }
        scope.launch {
            doWorkCourse6Supervisor(2)
        }

        scope.launch {
            delay(2000)
            throw Exception()
        }
        delay(5000)
    }

    job.join()
}

suspend fun doWorkCourse6Supervisor(i: Int) {

    while (true) {
        print(i)
        delay(200)
    }
}