package com.water.leapflow

import android.app.Application


/**
 * Created by wujl on 2020/10/27.
 */
interface IProcess {

    fun isMain():Boolean

    fun context():Application

}