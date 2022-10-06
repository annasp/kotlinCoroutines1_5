package com.pluralsight.kotlincoroutines1_5

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCoroutines15Application

fun main(args: Array<String>) {
    runApplication<KotlinCoroutines15Application>(*args)
//    course2()
//    forkJoinJavaWay()
//    course2CoroutinesScalability()
//    course2Launch()
//    course3CoroutineBuilders()
//    course4CancellingDemo()
//    course4TimeoutDemo()
//    course4CoroutineScopes()
//    course4DispatchersDemo()
//    course5AsyncAwait()
//    course5AwaitAndAsync()
//    course5InitCoroutineLazily()
//    course6JobsAndChildren()
//    Course6CooperativeCancellationDemo()
//    course6CancellingDeferredObjects()
//    course6ExceptionsToCancelCoroutines()
//    course6SupervisorJob()
//    course6SupervisorScope()
    course6ExceptionsWithAsyncBuilder()
}
