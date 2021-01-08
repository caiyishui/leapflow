package com.wedoctor.leapflow.task

import android.app.Application


/**
 * Created by wujl on 2020/10/23.
 */
interface ITask : IRelation,IRunner,IAction{

    var asyncNext: ArrayList<ITask>

    var context: Application?


}