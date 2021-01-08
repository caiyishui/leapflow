package com.wedoctor.leapflow.dispatch

import com.wedoctor.leapflow.LeapFlow
import com.wedoctor.leapflow.task.ITask
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by wujl on 2020/10/27.
 */
class MainWorker() : DefaultWorker() {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private var job: Job? = null

    override fun work(task: ITask) {
        kotlin.runCatching {
            task.action()
        }.onFailure {
            it.printStackTrace()
        }

        task.asyncNext.filter {
            println(it.javaClass)
            LeapFlow.mProcess.isMain() || !it.runOnlyInMainProcess
        }.forEach {
            when{
                it.aloneRun->
                    Dispatcher.withNewWorker().dispatch(it)
                else ->
                    Dispatcher.withBackgroundWorker().dispatch(it)
            }
        }
    }


    override fun attach() {
    }

    override fun destroy() {
        job?.cancel()
    }
}