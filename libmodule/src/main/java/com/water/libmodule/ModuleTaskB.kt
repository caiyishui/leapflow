package com.water.libmodule

import com.water.leapflow.annotations.DependOns
import com.water.leapflow.annotations.LeapFlowTask
import com.water.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/13.
 */
@LeapFlowTask(name = "moduleTaskB")
@DependOns(dependOns = ["taskA"])
class ModuleTaskB : DefaultTask() {
    override fun action() {
        println("ModuleTaskB start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(10)
            println("ModuleTaskB step:${index} ")
        }
        println("ModuleTaskB end")
    }

}