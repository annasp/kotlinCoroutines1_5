package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun Course6CooperativeCancellationDemo() = runBlocking() {

    var child1: Job? = null
    var child2: Job? = null
    var child3: Job? = null
    var child4: Job? = null
    var child5: Job? = null
    var child6: Job? = null
    coroutineScope {
        val job = GlobalScope.launch {
            child1 = launch {
                repeat(1000) {
                    Thread.sleep(1000)
                    print("1")
                    // cooperative functions delay and yield when the cancel is called they throw a CancellationException
                    yield() // cooperate with cancellation instead of using delay()
                }
            }

            child2 = launch {
                repeat(1000) {
//                    if(isActive) return@repeat // wrong - does not cancel the coroutine
                    if(isActive) return@launch // Correct
                    Thread.sleep(1000)
                    print("2") }
            }

            child3 = launch {
                repeat(1000) {
                    Thread.sleep(1000)
                    if(!isActive) throw CancellationException()
                    print("3") }
            }

            child4 = launch {
                repeat(1000) {
                    Thread.sleep(1000)
                    // It checks for the active flag, and if it's false - recommended approach
                    ensureActive()
                    print("4") }
            }

            child5 = launch {
                try {
                    repeat(1000) {
                        Thread.sleep(1000)
                        // It checks for the active flag, and if it's false throws exception - recommended approach
                        ensureActive()
                        print("5")
                    }
                } catch (ex: CancellationException) {
                    // even though we catch the exception the coroutine will still be cancelled
                    println("Cancelled: ${ex.message}")
                }
            }

            try {
                child6 = launch {
                    repeat(1000) {
                        Thread.sleep(1000)
                        // It checks for the active flag, and if it's false throws exception - recommended approach
                        ensureActive()
                        print("6")
                    }
                }
            } catch (ex: CancellationException) {
                // The exception is swallowed by the coroutine
                println("Cancelled: ${ex.message}")
            }

            repeat(1000) {
                delay(1000)
                print("0")
            }
        }

        delay(4000)
//        child1?.cancelAndJoin()
//        job.cancelAndJoin()
//        child2?.cancelAndJoin()
//        child3?.cancelAndJoin()
//        child4?.cancelAndJoin()
//        child5?.cancelAndJoin()
        child6?.cancelAndJoin()
        println()
        println("Job is cancelled: ${job.isCancelled}")
        println("Job is active: ${job.isActive}")

        // wait until the job completes
        job.join()
    }

    println("coroutine scope finished")
}