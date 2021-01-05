package com.base.library.login

import androidx.fragment.app.FragmentActivity
import com.base.library.login.common.constants.LoginType
import com.base.library.login.common.listener.ILoginManager
import com.base.library.login.common.listener.OnLoginListener
import com.base.library.login.facebook.FacebookLoginManager
import com.base.library.login.google.GoogleLoginManager
import com.base.library.login.twitter.TwitterLoginManager

/**
 * Description:
 * LoginManager工厂类
 *
 * @author  Alpinist Wang
 * Date:    2019-05-21
 */
class LoginManagerFactory(private val activity: FragmentActivity, private val onLoginListener: OnLoginListener) {

    /**
     * 反射获取对应的LoginManager
     */
    fun create(loginType: LoginType): ILoginManager {
        return when (loginType) {
            LoginType.Facebook -> FacebookLoginManager(activity, onLoginListener)
            LoginType.Twitter -> TwitterLoginManager(activity, onLoginListener)
            LoginType.Google -> GoogleLoginManager(activity, onLoginListener)
        }
    }
}