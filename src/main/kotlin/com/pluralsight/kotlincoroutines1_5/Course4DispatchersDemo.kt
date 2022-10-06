package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

/**
 * coroutine dispatcher determines which thread the coroutine is run on
 * Dispatchers:
 * 1. Default: Decides for itself which thread to use
 * 2. Main: will run the coroutines on whatever it regards as the main thread of the application
 * 3. IO: will use the IO thread pool to run threads
 * 4. Other: like unconfined dispatcher that comes as default, and we can create our own dispatchers
 */

val scope = CoroutineScope((Job()))

// Create our own dispatcher
val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor = Executors.newFixedThreadPool(10)

@OptIn(DelicateCoroutinesApi::class)
fun course4DispatchersDemo() = runBlocking {
    val jobs = arrayListOf<Job>()

    jobs += launch {// the 'default' context
        println(" 'default:' In thread ${Thread.currentThread().name}")
    }

    jobs += launch(Dispatchers.Default) {// the 'default' context
        doWorkCourse4("'defaultDispatcher:'")
//        println(" 'defaultDispatcher:' In thread ${Thread.currentThread().name}")
    }

    jobs += launch(Dispatchers.IO) {// the 'IO' context
        doWorkCourse4("'IO dispatcher:'")
//        println(" 'IO dispatcher:' In thread ${Thread.currentThread().name}")
    }

    jobs += launch(Dispatchers.Unconfined) {// not confined -- will work with main thread
        doWorkCourse4("'Unconfined:'")
//        println(" 'Unconfined:' In thread ${Thread.currentThread().name}")
    }

    jobs += launch(newSingleThreadContext("OwnThread")) { // will get its own new thread
        doWorkCourse4("'newSTC:'")
//        println(" 'newSTC:' In thread ${Thread.currentThread().name}")
    }

    jobs += launch(dispatcher) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
        println("'cachedThreadPool': In thread ${Thread.currentThread().name}")
    }

    jobs += launch(executor.asCoroutineDispatcher()) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
        println("'fixedThreadPool': In thread ${Thread.currentThread().name}")
    }


    jobs.forEach { it -> it.join() }

    println()
    println()
    println()

    Thread.sleep(100)
    val job1 = scope.launch {
        val jobs = arrayListOf<Job>()

        jobs += launch {// the 'default' context
            println(" 'default:' In thread ${Thread.currentThread().name}")
        }

        jobs += launch(Dispatchers.Default) {// the 'default' context
            println(" 'defaultDispatcher:' In thread ${Thread.currentThread().name}")
        }

        jobs += launch(Dispatchers.IO) {// the 'IO' context
            println(" 'IO dispatcher:' In thread ${Thread.currentThread().name}")
        }

        jobs += launch(Dispatchers.Unconfined) {// not confined -- will work with main thread
            println(" 'Unconfined:' In thread ${Thread.currentThread().name}")
        }

        jobs += launch(newSingleThreadContext("OwnThread")) { // will get its own new thread
            println(" 'newSTC:' In thread ${Thread.currentThread().name}")
        }

        jobs += launch(dispatcher) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
            println(" 'cachedThreadPool': In thread ${Thread.currentThread().name}")
        }

        jobs += launch(executor.asCoroutineDispatcher()) { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
            println(" 'fixedThreadPool': In thread ${Thread.currentThread().name}")
        }

    }
    job1.join()

    // if you don't shut down the service then the main thread never ends
    // make sure when you use your own dispatchers, to close the dispatcher, and shut down the executor service when you use this
    executor.shutdownNow()
    dispatcher.close()

}

/**
 * We should use withContext to dispatch out coroutines, rather than passing a dispatcher into the coroutine builder
 * It's the responsibility of the suspend function itself to know how to be dispatched
 * It's not the responsibility of the caller of that suspend function to dispatch it appropriately
 */
suspend fun doWorkCourse4(dispatcherName: String) {
    withContext(Dispatchers.IO) {
        println("$dispatcherName: (But) in thread ${Thread.currentThread().name}")
    }
}