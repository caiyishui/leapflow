package com.wedoctor.leapflow

import com.wedoctor.leapflow.annotations.DependOns
import com.wedoctor.leapflow.annotations.LeapFlowTask
import com.wedoctor.leapflow.task.DefaultTask
import com.wedoctor.leapflow.task.ITask


/**
 * Created by wujl on 2020/11/4.
 */

@LeapFlowTask(name = "taskA")
@DependOns(dependOns = ["taskC"])
class LaunchTaskA : DefaultTask() {
    override fun action() {
        println("LaunchTaskA start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(10)
            println("LaunchTaskA step:${index} ")
        }
        println("LaunchTaskA end")
    }

}