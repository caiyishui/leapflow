package com.wedoctor.leapflow.dispatch

import com.wedoctor.leapflow.IProcess
import com.wedoctor.leapflow.task.ITask
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