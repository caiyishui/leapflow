package com.water.leapflow

import com.water.leapflow.annotations.AsyncRun
import com.water.leapflow.annotations.DependOns
import com.water.leapflow.annotations.LeapFlowTask
import com.water.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/4.
 */
@LeapFlowTask(name = "taskB")
@DependOns(dependOns = ["taskA","taskC"])
@AsyncRun
class LaunchTaskB : DefaultTask() {
    override fun action() {
        println("LaunchTaskB start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(100)
            println("LaunchTaskB step:${index} ")
        }
        println("LaunchTaskB end")
    }

}