package com.pluralsight.kotlincoroutines1_5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

const val SEQUENTIAL_THRESHOLD = 20_000_000

suspend fun compute(array: IntArray, low: Int, high: Int): Long = coroutineScope {
    if (high - low <= SEQUENTIAL_THRESHOLD) {
        (low until high)
            .sumOf { array[it].toLong() }
    } else {
        val mid = low + (high - low) / 2
        val left = async(Dispatchers.Default) { compute(array, low, mid) }
        val right = async(Dispatchers.Default) { compute(array, mid, high) }
        left.await() + right.await()
    }
}

fun course2() = runBlocking {
    println("Start")
    Thread.sleep(1_000)
    val list = mutableListOf<Int>()

    var limit = 20_000_000
    while (limit > 0) {
        list.add(limit--)
    }

    val intArray = list.toIntArray()

    var result: Long

    measureTimeMillis {
        result = compute(intArray, 0, intArray.size)
    }

    val time: Long = measureTimeMillis {
        result = compute(intArray, 0, intArray.size)
    }
    print("$result in ${time}ms")
}