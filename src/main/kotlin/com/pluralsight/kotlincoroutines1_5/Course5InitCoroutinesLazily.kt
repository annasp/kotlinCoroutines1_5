package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Person {
    val children = GlobalScope.async(start = CoroutineStart.LAZY) { loadChildren() }

    companion object {
        suspend fun loadChildren() : List<String> {
            println("Loading children")
            delay(4000)
            return listOf("Harry", "Sam", "Alex")
        }
    }
}

fun course5InitCoroutineLazily() = runBlocking {
    println("Creating Person")

    val kevin = Person()
    kevin.children.start()

    Thread.sleep(2000)

    val time = measureTimeMillis {
        kevin.children.await().forEach { println(it) }
//        kevin.children.forEach{ println(it) }
    }
    println("Person created in ${time}ms")
}