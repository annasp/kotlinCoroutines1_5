package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.measureTimeMillis

fun course5AsyncAwait() = runBlocking {
    val job = launch {
        val time = measureTimeMillis {
            val res1 = async { doWorkOne() }
            val res2 = async { doWorkTwo() }

            println("The result is ${res1.await() + res2.await()}")
        }
        println("This took $time to run")
    }

    job.join()
}

suspend fun doWorkOne(): Int {
    delay(100)
    println("Working 1")
    return Random(System.currentTimeMillis()).nextInt(42)
}

suspend fun doWorkTwo(): Int {
    delay(200)
    println("Working 2")
    return Random(System.currentTimeMillis()).nextInt(42)
}