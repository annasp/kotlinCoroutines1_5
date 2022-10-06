package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun course5AwaitAndAsync() = runBlocking{
    val result = doWorkAsync("Work1")
    val answer = result.await()

    println("The answer is $answer")

    val res1: Int = doWorkNotAsync("Work2")
    // To run it concurrently, wrap it in our async coroutine builder.
    // If you want to run concurrently and get data back from it wrap it in the async coroutine builder
    // Don't make it explicitly asynchronous
    val res2: Deferred<Int> = async { doWorkNotAsync("Work3") }
    res2.await()
}

fun doWorkAsync(msg: String): Deferred<Int> = GlobalScope.async {
    log("$msg - Working")
    delay(500)
    log("$msg - Work done")

    return@async 42
}

suspend fun doWorkNotAsync(msg: String): Int {
    log("$msg - Working")
    delay(500)
    log("$msg - Work done")

    return 42
}


fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}