package com.base.library.login.twitter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginType
import com.base.library.login.common.listener.ILoginManager
import com.base.library.login.common.listener.OnLoginListener
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.models.User

/**
 * Description:
 * Twitter登录管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterLoginManager(context: Context, private val onLoginListener: OnLoginListener) :
    Callback<TwitterSession>(), ILoginManager {

    private val twitterLoginButton = TwitterLoginButton(context)
    private val auth = LoginAuth()

    override fun login() {
        twitterLoginButton.callback = this
        twitterLoginButton.callOnClick()
    }

    override fun success(result: Result<TwitterSession>?) {
        val token = result?.data?.authToken?.token
        val secret = result?.data?.authToken?.secret
        if (token == null || TextUtils.isEmpty(token)) {
            onLoginListener.onLoginFail(LoginType.Twitter, "Twitter Login fail, token is null")
            return
        }
        if (secret == null || TextUtils.isEmpty(secret)) {
            onLoginListener.onLoginFail(LoginType.Twitter, "Twitter Login fail, secret is null")
            return
        }
        auth.token = token
        auth.twitterSecret = secret
        TwitterCore.getInstance().apiClient.accountService.verifyCredentials(true, false, true)
            .enqueue(object : Callback<User>() {
                override fun success(result: Result<User>?) {
                    val user = result?.data
                    auth.email = user?.email ?: ""
                    auth.name = user?.name ?: ""
                    auth.avatar = user?.profileImageUrlHttps ?: ""
                    onLoginListener.onLoginSuccess(LoginType.Twitter, auth)
                }

                override fun failure(exception: TwitterException?) {
                    onLoginListener.onLoginSuccess(LoginType.Twitter, auth)
                }
            })
    }

    override fun failure(exception: TwitterException?) {
        onLoginListener.onLoginFail(LoginType.Twitter, "Twitter login fail:$exception")
    }

    override fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }

    override fun release() {

    }
}