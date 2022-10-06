package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread

@OptIn(DelicateCoroutinesApi::class)
fun course2Launch() {

    GlobalScope.launch {
        delay(1000)
        print("world")
    }

    print("Hello, ")
    sleep(1500)
}

private fun old_main() {
    thread {
        sleep(1000)
        println("World")
    }

    print("Hello, ")

    sleep(1500)
}
