package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

const val num_tasks = 10_000
const val loops = 500
const val wait_ms = 10L

// Using limited number of threads, and then we run the coroutines on this limited number of threads
fun course2CoroutinesScalability() = runBlocking {

    println("Starting...")

    val result = AtomicInteger()
    val jobs = mutableListOf<Job>()

    for (i in 1..num_tasks) {
        jobs.add(
            // make sure that all the jobs I am launching get launched on a thread but not the main thread
            launch(Dispatchers.IO) {
                for (x in 1..loops) {
                    delay(wait_ms)
                }
                result.getAndIncrement()
            }
        )
    }

    jobs.forEach { it.join() }
    println(result.get())

}

// Many threads are scheduled
private fun old_main() {

    println("Starting...")

    val result = AtomicInteger()
    val threads = mutableListOf<Thread>()

    for (i in 1..num_tasks) {
        threads.add(
            thread {
                for (x in 1..loops) {
                    sleep(wait_ms)
                }
                result.getAndIncrement()
            }
        )
    }

    threads.forEach { it.join() }
    println(result.get())

}