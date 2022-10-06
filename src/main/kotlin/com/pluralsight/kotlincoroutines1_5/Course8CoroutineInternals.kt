package com.pluralsight.kotlincoroutines1_5

/**
 * Coroutine:
 * Instance of a suspendable computation.
 * Coroutine Builder:
 * Bridge from non-suspending world to suspending
 * Suspension point:
 * Point where coroutine may be suspended
 * Continuation:
 * State of the suspend coroutine at the suspension point
 * Is used to capture the state of the coroutine at the point it suspended
 * and then we pass that continuation, back to the coroutine when we start it again
 *
 *
 * Coroutine builders are normal non-suspending functions
 * Take suspending lambda as parameter. We call a normal function, then this
 * normal function calls the suspend function for us(bridge).
 * Calls startCoroutineCancellable on the lamdba (indirectly)
 * startCoroutineCancellable -> creates the initial continuation and runs the block
 * oc code that we pass to this, the suspendLamdba as a function.
 *
 * What happens when we run a suspend function:
 * When we add suspend modifier, the compiler transform the function
 * using CPS(Continuation Passing Style) in that transformation
 * Compiler turns into a set of callbacks and uses the suspension points
 * in the code as the place to add the callback
 * Code is transformed into a set of callbacks.
 *
 * Kotlin transforms the code to use something called continuation object
 * and create a massive state machine where each function call is the next
 * state to go to in that state machine.
 *
 */