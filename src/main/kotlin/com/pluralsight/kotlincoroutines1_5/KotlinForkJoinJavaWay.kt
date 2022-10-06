import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask
import kotlin.system.measureTimeMillis

internal class Sum(private var array: IntArray, private var low: Int, private var high: Int) : RecursiveTask<Long>() {
    override fun compute(): Long {
        return if (high - low <= SEQUENTIAL_THRESHOLD) {
            (low until high)
                .sumOf { array[it].toLong() }
        } else {
            val mid = low + (high - low) / 2
            val left = Sum(array, low, mid)
            val right = Sum(array, mid, high)
            left.fork()
            val rightAns = right.compute()
            val leftAns = left.join()
            leftAns + rightAns
        }
    }

    companion object {
        const val SEQUENTIAL_THRESHOLD = 20_000_000 / 7
        fun sumArray(array:IntArray): Long {
            println("Number of cores: ${ForkJoinPool.getCommonPoolParallelism()}")
            return ForkJoinPool.commonPool().invoke(Sum(array, 0, array.size))
        }
    }
}

fun forkJoinJavaWay() {

    println("Start")
    Thread.sleep(1_000)
    val list = mutableListOf<Int>()

    var limit = 20_000_000
    while (limit > 0) {
        list.add(limit--)
    }

    var result: Long

    measureTimeMillis {
        result = Sum.sumArray(list.toIntArray())
    }

    val time: Long = measureTimeMillis {
        result = Sum.sumArray(list.toIntArray())
    }
    print("$result in ${time}ms")
}
