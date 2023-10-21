package com.water.leapflow.annotations



@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class LeapFlowTask(val name:String,val priority:Int = 0)