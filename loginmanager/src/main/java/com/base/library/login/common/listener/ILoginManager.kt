package com.base.library.login.common.listener

import android.content.Intent

/**
 * Description:
 * LoginManager接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019-05-21
 */
interface ILoginManager {
    /**
     * 登录方法
     */
    fun login()

    /**
     * 处理onActivityResult
     */
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * 释放资源
     */
    fun release()
}