package com.wedoctor.leapflow.compiler

import com.wedoctor.leapflow.annotations.LeapFlowTask
import com.wedoctor.leapflow.annotations.OnlyIf
import com.wedoctor.leapflow.compiler.helper.LeapFlowTaskHelper
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation


/**
 * Created by wujl on 2020/11/2.
 */
class LeapFlowProcessor: AbstractProcessor() {


    lateinit var filer: Filer

    @Synchronized
    override fun init(ProcessingEnv: ProcessingEnvironment?) {
        super.init(ProcessingEnv)
        filer = processingEnv.filer
        RuntimeHelper.init(processingEnv)

    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        RuntimeHelper.note("\nprocess:${annotations.toString()} isMain${RuntimeHelper.isMain}\n")
        roundEnv?.let {
            kotlin.runCatching {
                if (RuntimeHelper.isMain){
                    LeapFlowTaskHelper.parseAnn(it,filer)
                }else{
                    LeapFlowTaskHelper.parseAnn(it)
                }

            }.onFailure {
                it.printStackTrace()
            }

        }

        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(LeapFlowTask::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}