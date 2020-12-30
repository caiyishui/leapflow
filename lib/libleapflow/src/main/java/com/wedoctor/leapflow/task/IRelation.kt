package com.wedoctor.leapflow.task


/**
 * Created by wujl on 2020/10/23.
 */
interface IRelation {

    /**
     * onlyIf返回true时才运行。这个由运行时觉得。
     */
    fun onlyIf():Boolean{
        return true
    }

}