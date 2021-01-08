package com.wedoctor.leapflow

import androidx.annotation.MainThread
import com.wedoctor.leapflow.dispatch.Dispatcher
import com.wedoctor.leapflow.dispatch.IWorker
import com.wedoctor.leapflow.task.ITask
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
            val generate = Class.forName("com.wedoctor.leapflow.FlowDelegate").newInstance() as IFlow
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