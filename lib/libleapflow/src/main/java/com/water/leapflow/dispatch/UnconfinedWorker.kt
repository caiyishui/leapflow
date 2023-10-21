package com.water.leapflow.dispatch

import com.water.leapflow.IProcess
import com.water.leapflow.task.ITask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Created by wujl on 2020/10/28.
 */
class UnconfinedWorker():DefaultWorker() {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Unconfined

    override fun work(task: ITask) {
        launch {
            task.action()
        }
    }

    override fun attach() {

    }

    override fun destroy() {
        coroutineContext.cancel()
    }


}