package com.water.libmodule

import com.water.leapflow.annotations.AloneRun
import com.water.leapflow.annotations.DependOns
import com.water.leapflow.annotations.LeapFlowTask
import com.water.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/18.
 */
@LeapFlowTask(name = "moduleTaskC")
@AloneRun
class ModuleTaskC : DefaultTask() {
    override fun action() {
        println("ModuleTaskC start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(100)
            println("ModuleTaskC step:${index} ")
        }
        println("ModuleTaskC end")
    }

}