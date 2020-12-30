package com.wedoctor.leapflow.dispatch

object WorkerProvider {

    val defaultWorker = MainWorker()
    val backgroundWorker = AsyncWorker()

    /**
     * 对于newWorker，只能是后台异步Worker。
     */
    fun newWorker():DefaultWorker = AsyncWorker()

}