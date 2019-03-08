package com.base.library.login.common.listener

import com.base.library.login.common.bean.LoginAuth

/**
 * Description:
 * 登录接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
interface OnLoginListener {
    /**
     * 登录成功
     * @param type 登录方式
     * @param auth 获取到的身份信息
     */
    fun onLoginSuccess(type: String, auth: LoginAuth)

    /**
     * 登录失败
     * @param type 登录方式
     * @param cause 失败原因
     */
    fun onLoginFail(type: String, cause: String)
}