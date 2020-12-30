package com.wedoctor.leapflow.annotations


/**
 * Created by wujl on 2020/11/2.
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnlyIf (val funName:String)