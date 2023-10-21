package com.water.libmodule

import com.water.leapflow.annotations.AloneRun
import com.water.leapflow.annotations.AsyncRun
import com.water.leapflow.annotations.DependOns
import com.water.leapflow.annotations.LeapFlowTask
import com.water.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/13.
 */
@LeapFlowTask(name = "moduleTaskA")
@DependOns(dependOns = ["taskA","moduleTaskB"])
@AloneRun
class ModuleTaskA : DefaultTask() {
    override fun action() {
        println("ModuleTaskA start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            println("ModuleTaskA step:${index} ")
        }
        println("ModuleTaskA end")
    }

}