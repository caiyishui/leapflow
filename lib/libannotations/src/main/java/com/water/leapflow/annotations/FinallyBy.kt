package com.water.leapflow.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class FinallyBy (val finallyBy:String = "")