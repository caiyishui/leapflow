package com.water.leapflow.dispatch

import com.water.leapflow.task.ITask
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.atomic.AtomicInteger


/**
 * Created by wujl on 2020/10/27.
 */
interface IWorker: CoroutineScope {

    companion object{
        val idGenerator = AtomicInteger(1)
    }

    fun work(task:ITask)

    fun attach()

    fun destroy()

    val workId: Int
        get() = idGenerator.getAndIncrement()

}