package com.water.leapflow

import androidx.annotation.MainThread
import com.water.leapflow.dispatch.Dispatcher
import com.water.leapflow.dispatch.IWorker
import com.water.leapflow.task.ITask
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by wujl on 2020/10/23.
 */
object LeapFlow{

    lateinit var mProcess :IProcess

    fun attachOnCreate(process :IProcess) {
        mProcess = process

        //反射创建动态生成类
        kotlin.runCatching {
            val generate = Class.forName("com.water.leapflow.FlowDelegate").newInstance() as IFlow
            generate.build().filter {
                mProcess.isMain() || !it.runOnlyInMainProcess
            }.forEach {
                println("start task: ${it.javaClass}")
                it.context = process.context()
                when{
                    it.aloneRun->
                        Dispatcher.withNewWorker().dispatch(it)
                    else ->
                        Dispatcher.withMainWorker().dispatch(it)
                }

            }

        }

    }

    @MainThread
    fun stopAndClear(){
        Dispatcher.stopAll()
    }

}