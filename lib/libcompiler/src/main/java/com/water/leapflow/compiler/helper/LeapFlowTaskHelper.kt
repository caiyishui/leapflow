package com.water.leapflow.compiler.helper

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.water.leapflow.annotations.*
import com.water.leapflow.compiler.RuntimeHelper
import java.io.File
import java.nio.charset.Charset
import javax.annotation.processing.Filer
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import kotlin.collections.ArrayList


/**
 * Created by wujl on 2020/11/2.
 */
object LeapFlowTaskHelper {

    @Throws(Exception::class)
    fun parseAnn(env: RoundEnvironment) {

        val output = File("${RuntimeHelper.buildPath}${File.separator}${RuntimeHelper.projectName}${RuntimeHelper.TASK_FILE}")

        val elements = env.getElementsAnnotatedWith(LeapFlowTask::class.java)
        if(elements.isEmpty()){
            return
        }

        val writer = output.writer()
        elements.forEach {
            (it as TypeElement).let { type ->
                RuntimeHelper.note("element:${type.simpleName}\n")
                val obj = JsonObject()
                type.getAnnotation(LeapFlowTask::class.java).apply {
                    val dependOnArray = JsonArray()
                    type.getAnnotation(DependOns::class.java)?.dependOns?.
                    forEach {depend ->
                        dependOnArray.add(depend)
                    }
                    obj.add("dependOns",dependOnArray)
                    obj.addProperty("finallyBy",type.getAnnotation(FinallyBy::class.java)?.finallyBy?:"")
                    obj.addProperty("name",name)
                    obj.addProperty("clazzName",type.qualifiedName.toString())
                    obj.addProperty("asyncRun",type.getAnnotation(AsyncRun::class.java) != null)
                    obj.addProperty("onlyInMainProcess",type.getAnnotation(MainProcess::class.java) != null)
                    obj.addProperty("aloneRun",type.getAnnotation(AloneRun::class.java) != null)
                    obj.addProperty("priority",priority)
                }
                writer.write(obj.toString())
                writer.write("\n")
            }
        }

        writer.flush()
        writer.close()
    }

    @Throws(Exception::class)
    fun parseAnn(env: RoundEnvironment,filer: Filer) {

        val elements = env.getElementsAnnotatedWith(LeapFlowTask::class.java)
        if(elements.isEmpty()){
            return
        }

        RuntimeHelper.createLog()


        //单独执行任务记录
        val aloneTasks = ArrayList<LeapFlowTaskObj>()

        //异步执行任务记录
        val asyncTasks = ArrayList<LeapFlowTaskObj>()

        //主线程同步执行任务记录
        val syncTasks = ArrayList<LeapFlowTaskObj>()

        //所有任务纪录，同名任务会通过比较优先级选取最高的。
        val allTasks = hashMapOf<String,LeapFlowTaskObj>()
        elements.forEach {
            (it as TypeElement).let { type ->
                RuntimeHelper.note("type:${type.simpleName}")

                type.getAnnotation(LeapFlowTask::class.java).apply {

                    val obj = LeapFlowTaskObj(name,
                        type.getAnnotation(DependOns::class.java)?.dependOns?:arrayOf(),
                        type.getAnnotation(FinallyBy::class.java)?.finallyBy?:"",
                        type.qualifiedName.toString(),
                        type.getAnnotation(AsyncRun::class.java) != null,
                        type.getAnnotation(MainProcess::class.java) != null,
                        type.getAnnotation(AloneRun::class.java) != null,
                        priority
                    )

                    if (allTasks.containsKey(name)){
                        RuntimeHelper.appendLog("注意：存在多个命名为${name}的启动任务,将选取优先级高的执行！")
                        RuntimeHelper.appendLog(allTasks[name].toString())
                        RuntimeHelper.appendLog(obj.toString())
                        RuntimeHelper.appendLog("\n")
                        if (obj.priority > allTasks[name]!!.priority){
                            allTasks[name] = obj
                        }


                    }else{
                        allTasks[name] = obj
                    }
                }
            }

        }

        val dir = File(RuntimeHelper.buildPath)
        dir.listFiles { _, name ->
            name.endsWith(RuntimeHelper.TASK_FILE)
        }?.forEach {
            it.readLines().forEach {line ->
                val obj = Gson().fromJson(line,LeapFlowTaskObj::class.java)
                obj.asyncNext = ArrayList<LeapFlowTaskObj>()

                if (allTasks.containsKey(obj.name)){
                    RuntimeHelper.appendLog("注意：存在多个命名为${obj.name}的启动任务,将选取优先级高的执行！")
                    RuntimeHelper.appendLog(allTasks[obj.name].toString())
                    RuntimeHelper.appendLog(obj.toString())
                    RuntimeHelper.appendLog("\n")
                    if (obj.priority > allTasks[obj.name]!!.priority){
                        allTasks[obj.name] = obj
                    }
                }else{
                    allTasks[obj.name] = obj
                }
            }
        }

        allTasks.values.forEach { obj ->
            when {
                obj.aloneRun && obj.dependOns.isEmpty() ->
                    aloneTasks.add(obj)
                obj.aloneRun && obj.dependOns.isNotEmpty() ->
                    asyncTasks.add(obj)
                obj.asyncRun ->
                    asyncTasks.add(obj)
                else ->
                    syncTasks.add(obj)
            }
        }

//        sortTask(tasks)
//        RuntimeHelper.note(tasks.toString())
        val sorted = FlowSortedHelper.sortTask(asyncTasks,syncTasks)

        //将单独执行的任务添加到最前面。在任务流中最新触发。
        sorted.addAll(0,aloneTasks)
        CodeGenHelper.genClass(
            sorted,
            filer
        )

        RuntimeHelper.flushLog()
    }

}