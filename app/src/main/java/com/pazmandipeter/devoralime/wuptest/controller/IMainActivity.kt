package com.pazmandipeter.devoralime.wuptest.controller

/**
 * Created by Kovacs David on 2/3/21.
 */
interface IMainActivity {


    fun showBadge(count: Int?)

    fun setTitle(titleId: Int, duration: Long, outDuration: Long?, textGravity: Int)


}