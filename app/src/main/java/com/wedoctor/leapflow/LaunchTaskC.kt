package com.wedoctor.leapflow

import com.wedoctor.leapflow.annotations.LeapFlowTask
import com.wedoctor.leapflow.annotations.MainProcess
import com.wedoctor.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/13.
 */
@LeapFlowTask(name = "taskC")
@MainProcess
class LaunchTaskC : DefaultTask() {
    override fun action() {
        println("LaunchTaskC start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(10)
            println("LaunchTaskC step:${index} ")
        }
        println("LaunchTaskC end")
    }

}