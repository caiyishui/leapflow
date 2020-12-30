package com.wedoctor.leapflow.annotations


/**
 * Created by wujl on 2020/10/23.
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class LeapFlowTask(val name:String,val priority:Int = 0)