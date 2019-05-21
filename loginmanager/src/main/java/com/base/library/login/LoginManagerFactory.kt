package com.base.library.login

import android.content.Context
import android.support.v4.app.FragmentActivity
import com.base.library.login.common.constants.LoginType
import com.base.library.login.common.listener.ILoginManager
import com.base.library.login.common.listener.OnLoginListener

/**
 * Description:
 * LoginManager工厂类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019-05-21
 */
class LoginManagerFactory(private val activity: FragmentActivity, private val onLoginListener: OnLoginListener) {
    /**
     * 反射获取对应的LoginManager
     */
    fun create(loginType: LoginType): ILoginManager {
        val type = loginType.toString()
        val managerClass =
            Class.forName("com.base.library.login.${type.toLowerCase()}.${type[0].toUpperCase()}${type.substring(1).toLowerCase()}LoginManager")
        val constructor = when (loginType) {
            LoginType.Google -> managerClass.getDeclaredConstructor(
                FragmentActivity::class.java,
                OnLoginListener::class.java
            )
            else -> managerClass.getDeclaredConstructor(Context::class.java, OnLoginListener::class.java)
        }
        return constructor.newInstance(activity, onLoginListener) as ILoginManager
    }
}