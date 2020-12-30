package com.wedoctor.leapflow.dispatch

import com.wedoctor.leapflow.IProcess
import com.wedoctor.leapflow.task.ITask
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class Dispatcher(private val worker: IWorker) {

    companion object{
        private val mainDispatcher = Dispatcher(WorkerProvider.defaultWorker)
        private val backgroundDispatcher = Dispatcher(WorkerProvider.backgroundWorker)
        private val dispatchers = ArrayList<WeakReference<Dispatcher>>()


        /**
         *
         */
        fun withMainWorker():Dispatcher{
            return mainDispatcher
        }

        /**
         *
         */
        fun withBackgroundWorker():Dispatcher{
            return backgroundDispatcher
        }

        /**
         * 新创建一个Worker来执行分发的Task，只会是后台异步Worker。
         */
        fun withNewWorker():Dispatcher{
            return Dispatcher(WorkerProvider.newWorker())
        }

        /**
         *  停止所有任务并清理为完成任务
         */
        fun stopAll(){
            dispatchers.forEach {
                it.get()?.apply {
                    stop()
                }
            }
            dispatchers.clear()
            backgroundDispatcher.stop()
            mainDispatcher.stop()
        }
    }

    private val queue = LinkedList<ITask>()

    /**
     * 在该Dispatcher中分发一个任务并立即执行。
     */
    fun dispatch(task: ITask){
        worker.work(task)
    }

    fun stop(){
        worker.destroy()
    }

}