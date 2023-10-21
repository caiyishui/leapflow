package com.water.leapflow.compiler.helper

import com.squareup.javapoet.*
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier

object CodeGenHelper {

    private val TASK_INTERFACE = ClassName.get("com.water.leapflow.task", "ITask")
    private val FLOW_INTERFACE = ClassName.get("com.water.leapflow", "IFlow")
    private val LIST = ClassName.get("java.util", "List")
    private val ARRAYLIST = ClassName.get("java.util", "ArrayList")

    private const val className = "FlowDelegate"
    private const val packageName = "com.water.leapflow"
    private const val methodName = "build"

    fun genClass(tasks:List<LeapFlowTaskObj>,filer: Filer){
        //定义类
        val type = TypeSpec.classBuilder(className)
        type.addSuperinterface(FLOW_INTERFACE)

        //定义函数
        val returns = ParameterizedTypeName.get(LIST,TASK_INTERFACE)
        val method = MethodSpec.methodBuilder(methodName)
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .returns(returns)

        //添加函数体
        method.addStatement("\$T returns = new \$T<>()", returns, ARRAYLIST)
        method.addStatement("try {")
        tasks.forEach {task ->
            method.addStatement("   ITask \$L = (ITask)Class.forName(\$S).newInstance()",task.name, task.clazzName)
            if (task.aloneRun){
                method.addStatement("   \$L.setAloneRun(true)",task.name)
            }
            if (task.onlyInMainProcess){
                method.addStatement("   \$L.setRunOnlyInMainProcess(true)",task.name)
            }
            task.asyncNext.forEach { async->
                method.addStatement("   ITask \$L = (ITask)Class.forName(\$S).newInstance()",async.name, async.clazzName)
                if (async.aloneRun){
                    method.addStatement("   \$L.setAloneRun(true)",task.name)
                }
                if (async.onlyInMainProcess){
                    method.addStatement("   \$L.setRunOnlyInMainProcess(true)",task.name)
                }
                method.addStatement("   \$L.getAsyncNext().add(\$L)",task.name,async.name)
            }

            method.addStatement("   returns.add(\$L)", task.name)
        }
        method.addStatement("} catch (Exception e) {")
        method.addStatement("   e.printStackTrace()")
        method.addStatement("}")
        method.addStatement("return returns")
        type.addMethod(method.build())

        val file = JavaFile.builder(packageName, type.build()).build()

        file.writeTo(filer)

    }

}