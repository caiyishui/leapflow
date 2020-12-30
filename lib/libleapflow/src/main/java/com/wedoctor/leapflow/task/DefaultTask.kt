package com.wedoctor.leapflow.task

import java.util.concurrent.ExecutorService


/**
 * Created by wujl on 2020/10/23.
 */
abstract class DefaultTask : ITask{

    override var asyncNext: ArrayList<ITask> = ArrayList<ITask>()

    override var runOnlyInMainProcess: Boolean =  false

    override var aloneRun: Boolean = false


    override fun priority(): Int {
        return 1
    }
}