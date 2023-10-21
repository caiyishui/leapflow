package com.water.leapflow.compiler.helper

import com.water.leapflow.compiler.RuntimeHelper
import java.lang.IllegalStateException


/**
 * Created by wujl on 2020/11/17.
 */
object FlowSortedHelper {

    // 标记每个节点的状态：0=未搜索，1=搜索中，2=已完成
    private lateinit var visited: IntArray
    // 判断有向图中是否有环,有环说明互相依赖，task graph构建失败。
    private var error = false

    //保存待搜索项name
    private var names : Array<String>? = null

    //最终结果
    private val sorts = ArrayList<LeapFlowTaskObj>()


    fun sortTask(asyncTasks:ArrayList<LeapFlowTaskObj>,syncTasks:ArrayList<LeapFlowTaskObj>):ArrayList<LeapFlowTaskObj>{

        //对主线程任务进行排序
        names = Array(syncTasks.size){
            syncTasks[it].name
        }

        visited = IntArray(syncTasks.size){0}
        syncTasks.forEachIndexed { index, _ ->
            if (visited[index] == 0){
                bfs(index,syncTasks)
            }
        }

        //将异步任务挂载到主线程任务上
        val hashMap = HashMap<String,Int>()

        sorts.forEachIndexed { index, leapFlowTaskObj ->
            hashMap[leapFlowTaskObj.name] = index
        }

        asyncTasks.forEach {
            if (it.dependOns.isEmpty()){
                throw IllegalStateException("cant build an async task named:${it.name} without dependencies! task class:${it.clazzName}")
            }

            var taskPosition :Int= -1
            it.dependOns.forEach {name ->

                if(hashMap.containsKey(name)){
                    RuntimeHelper.appendLog("注意：${it.name} 启动任务使用dependOns依赖了一个非主线程执行任务，名为 $name。忽略了这个依赖关系。")
                    RuntimeHelper.appendLog("\n")
                }
                hashMap[name]?.let {pos->
                    taskPosition = Math.max(taskPosition,pos)
                }
            }

            if (taskPosition == -1){
                throw IllegalStateException("cant build an async task named:${it.name} without sync dependencies! task class:${it.clazzName}")
            }
            sorts[taskPosition].asyncNext.add(it)

        }

        return sorts

    }

    private fun bfs(index:Int,elements: ArrayList<LeapFlowTaskObj>){
        //标记对第index的元素为搜索状态
        visited[index] = 1

        val task = elements[index]

        task.dependOns.forEach {
            val dependIndex = names!!.indexOf(it)
            if (dependIndex == -1){
                throw IllegalArgumentException("the task named $it not found!!! task:${task.name}" )
            }

            if (visited[dependIndex] == 0){
                //0说明未进行搜索
                bfs(dependIndex,elements)
                if (error){
                    return
                }
            }else if (visited[dependIndex] == 1){
                //0说明真正搜索，在搜索中的两个task互相依赖，说明存在环
                error = true
                return
            }else{
                //2说明已经完成搜索，已经在栈中，会先于该任务执行。
            }
        }

        visited[index] = 2
        if (task.dependOns.isNotEmpty()){
            sorts.add(task)
        }else{
            sorts.add(0,task)
        }

    }

}