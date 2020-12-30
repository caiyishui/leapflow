package com.wedoctor.leapflow.task


/**
 * Created by wujl on 2020/10/23.
 */
interface ITask : IRelation,IRunner,IAction{

    var asyncNext: ArrayList<ITask>

}