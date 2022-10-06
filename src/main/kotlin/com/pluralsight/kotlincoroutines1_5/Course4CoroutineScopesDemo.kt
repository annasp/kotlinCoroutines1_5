package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun course4CoroutineScopes() = runBlocking {
    launch {
//        runWithGlobalScope()
//        println("runWithGlobalScope returned")
        runWithLocalScope()
        println("runWithLocalScope returned")
    }
}

suspend fun runWithLocalScope() {
    coroutineScope {
        launch {
            println("Launch 1")
            delay(1000)
        }

        launch {
            println("Launch 2")
            delay(1000)
        }
    }
    println("runWithLocalScope finished")
    /**
     * Output:
     * Launch 1
     * Launch 2
     * runWithLocalScope finished
     * runWithLocalScope returned
     */
}

// Wrong - Breaking rule of structured concurrency:
// Returning from a suspend function before within it is completed
private suspend fun runWithGlobalScope() {
    GlobalScope.launch {
        println("Launch 1")
        delay(1000)
    }

    GlobalScope.launch {
        println("Launch 2")
        delay(1000)
    }
    println("runWithGlobalScope finished")

    /**
     * Output:
     * Launch 1
     * runWithGlobalScope finished
     * runWithGlobalScope returned
     * Launch 2
     */
}