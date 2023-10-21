package com.water.leapflow.dispatch

import com.water.leapflow.IProcess
import com.water.leapflow.task.ITask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Created by wujl on 2020/10/27.
 */
abstract class DefaultWorker() :IWorker{

}