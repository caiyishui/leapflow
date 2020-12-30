package com.wedoctor.leapflow.annotations


/**
 * Created by wujl on 2020/11/16.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class DependOns (val dependOns:Array<String> = [])
