package com.water.leapflow

import com.water.leapflow.task.ITask


/**
 * Created by wujl on 2020/10/27.
 */
interface IFlow {

    fun build():List<ITask>

}