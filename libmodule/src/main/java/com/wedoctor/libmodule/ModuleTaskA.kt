package com.wedoctor.libmodule

import com.wedoctor.leapflow.annotations.AloneRun
import com.wedoctor.leapflow.annotations.AsyncRun
import com.wedoctor.leapflow.annotations.DependOns
import com.wedoctor.leapflow.annotations.LeapFlowTask
import com.wedoctor.leapflow.task.DefaultTask


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