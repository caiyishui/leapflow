package com.water.leapflow.task

import java.util.concurrent.ExecutorService


/**
 * Created by wujl on 2020/10/23.
 */
interface IRunner {

    var runOnlyInMainProcess : Boolean

    var aloneRun: Boolean

    /**
     *
     * 线程优先级
     * @return Int
     */
    fun priority() : Int
}