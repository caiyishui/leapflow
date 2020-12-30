package com.wedoctor.leapflow

import com.wedoctor.leapflow.task.ITask


/**
 * Created by wujl on 2020/10/27.
 */
interface IFlow {

    fun build():List<ITask>

}