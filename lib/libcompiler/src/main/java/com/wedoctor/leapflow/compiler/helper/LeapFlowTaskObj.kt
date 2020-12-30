package com.wedoctor.leapflow.compiler.helper


/**
 * Created by wujl on 2020/11/2.
 */
class LeapFlowTaskObj (var name:String = "",
                       var dependOns:Array<String> = arrayOf(),
                       var finallyBy:String = "",
                       var clazzName:String = "",
                       var asyncRun:Boolean = true,
                       var onlyInMainProcess:Boolean = false,
                       var aloneRun:Boolean,
                       var priority:Int
){

    override fun toString(): String {
        return "name:$name dependOns:$dependOns finallyBy:$finallyBy clazzName:$clazzName asyncRun:$asyncRun onlyInMainProcess:$onlyInMainProcess aloneRun:$aloneRun  priority:$priority"
    }

    var asyncNext = ArrayList<LeapFlowTaskObj>()
}