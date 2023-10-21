package com.water.leapflow

import android.app.Application


/**
 * Created by wujl on 2020/11/4.
 */
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        println("ThreadId:${Thread.currentThread().id}")
        println("start---")
        LeapFlow.attachOnCreate(object : IProcess {
            override fun isMain(): Boolean {
                return true
            }

            override fun context(): Application {
                return this@MainApplication
            }
        })

        println("end---")

    }

}