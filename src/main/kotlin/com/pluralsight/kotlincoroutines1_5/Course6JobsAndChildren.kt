package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course6JobsAndChildren() = runBlocking {
    // We can pass Job objects, into coroutine scopes to act as context
    // and then we can use that Job object to cancel anything within that context
    val launchParent = Job()
    val scope = CoroutineScope(Job())

    // I can pass in a job to act as the parent of this coroutineScope
    val job = scope.launch(launchParent) {
        val j1 = coroutineContext[Job] // == job - child of launchParent

        val j2 = launch {
            delay(500)
        }
        println("Job passed to the scope.launch as the new context (parentLaunch): $launchParent")
        displayChildren(0, launchParent)
        println("Job returned from scope.launch as the new job (j1): $j1")
        displayChildren(0, j1!!)
        println("Job returned from child launch (j2): $j2")
        displayChildren(0, j2)

        j2.join()
    }

    job.join()
}

fun displayChildren(depth: Int = 0, job: Job) {
    job.children.forEach {
        for(i in 0..depth) {
            print("\t")
        }
        println("child: $it")
        displayChildren(depth+1, it)
    }
}