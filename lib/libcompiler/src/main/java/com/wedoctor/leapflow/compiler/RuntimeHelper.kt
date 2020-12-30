package com.wedoctor.leapflow.compiler

import java.io.File
import java.io.Writer
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic
import javax.tools.StandardLocation


/**
 * Created by wujl on 2020/11/12.
 */
object RuntimeHelper {
    //如果为设置ISMAIN，会通过是否项目名是否是app进行判断。
    private const val ISMAIN = "isMain"
    lateinit var outputPath: String
    var isMain:Boolean = true
    lateinit var buildPath:String
    const val TASK_FILE = "_tasks.json"
    const val TASK_LOG = "task_log.txt"
    lateinit var projectName :String

    private lateinit var messager: Messager
    lateinit var filer: Filer
    var logWriter: Writer? = null

    fun init(env: ProcessingEnvironment){
        messager = env.messager
        filer = env.filer
        val options = env.options
        var isMainParams = options[ISMAIN]

        kotlin.runCatching {
            outputPath = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", TASK_FILE).toUri().path

        }.onFailure {
            it.printStackTrace()
        }
        val projectPath = outputPath.replace("${File.separator}build${File.separator}generated${File.separator}source${File.separator}kapt${File.separator}debug${File.separator}$TASK_FILE","")
        projectName = projectPath.substring(projectPath.lastIndexOf(File.separator)+1)
        buildPath = projectPath.substring(0,projectPath.lastIndexOf(File.separator))+File.separator+"build"

        isMain = if(isMainParams == null){
            projectPath.endsWith("app")
        }else{
            isMainParams == "true"
        }
    }

    fun note(msg: String?) {
        messager.printMessage(
            Diagnostic.Kind.NOTE,
            msg
        )
    }

    fun error(msg: String?) {
        messager.printMessage(
            Diagnostic.Kind.ERROR,
            msg
        )
    }

    fun createLog(){
        val log = File(filer.createResource(StandardLocation.SOURCE_OUTPUT, "", TASK_LOG).toUri().path)
        logWriter = log.writer()

    }

    fun appendLog(line:String){
        logWriter?.appendln(line)
    }

    fun flushLog(){
        logWriter?.flush()
        logWriter?.close()
    }

}