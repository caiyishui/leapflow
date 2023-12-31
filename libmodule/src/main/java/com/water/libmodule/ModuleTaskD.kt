package com.water.libmodule

import com.water.leapflow.annotations.AloneRun
import com.water.leapflow.annotations.LeapFlowTask
import com.water.leapflow.task.DefaultTask


/**
 * Created by wujl on 2020/11/18.
 */
@LeapFlowTask (name = "moduleTaskC",priority = 2)
@AloneRun
class ModuleTaskD : DefaultTask() {
    override fun action() {
        println("ModuleTaskD start ThreadId:${Thread.currentThread().id}")
        for (index in 0 .. 10){
            Thread.sleep(100)
            println("ModuleTaskD step:${index} ")
        }
        println("ModuleTaskD end")
    }

}