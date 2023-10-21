package com.water.leapflow.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class DependOns (val dependOns:Array<String> = [])
