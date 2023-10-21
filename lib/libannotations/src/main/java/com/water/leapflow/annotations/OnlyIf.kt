package com.water.leapflow.annotations



@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnlyIf (val funName:String)