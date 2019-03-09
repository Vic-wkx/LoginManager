package com.base.library.login

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.base.library.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.library.login.common.constants.LoginConstants.Companion.GOOGLE
import com.base.library.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.library.login.common.listener.OnLoginListener
import com.base.library.login.facebook.FacebookLoginManager
import com.base.library.login.google.GoogleLoginManager
import com.base.library.login.twitter.TwitterLoginManager
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

/**
 * Description:
 * 登录管理类
 *
 * 使用方式见[com.base.library.login.base.BaseLoginActivity]
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class LoginManager(private val activity: FragmentActivity, private val onLoginListener: OnLoginListener) {
    private var facebookLoginManager: FacebookLoginManager? = null
    private var googleLoginManager: GoogleLoginManager? = null
    private var twitterLoginManager: TwitterLoginManager? = null

    companion object {

        fun init(app: Application) {
            initTwitterLogin(app)
        }

        private fun initTwitterLogin(app: Application) {
            val applicationInfo = app.packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
            val metaData = applicationInfo.metaData
            val twitterConsumerKey = metaData.get("twitter_consumer_key")?.toString()
            val twitterConsumerSecret = metaData.get("twitter_consumer_secret")?.toString()
            val config = TwitterConfig.Builder(app)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(twitterConsumerKey, twitterConsumerSecret))
                .debug(BuildConfig.DEBUG)
                .build()
            Twitter.initialize(config)
        }
    }


    fun loginBy(type: String) {
        when (type) {
            FACEBOOK -> getFacebookLoginManager()?.login()
            GOOGLE -> getGoogleLoginManager()?.login()
            TWITTER -> getTwitterLoginManager()?.login()
        }
    }

    private fun getFacebookLoginManager(): FacebookLoginManager? {
        if (facebookLoginManager == null)
            facebookLoginManager = FacebookLoginManager(activity, onLoginListener)
        return facebookLoginManager
    }

    private fun getGoogleLoginManager(): GoogleLoginManager? {
        if (googleLoginManager == null)
            googleLoginManager = GoogleLoginManager(activity, onLoginListener)
        return googleLoginManager
    }

    private fun getTwitterLoginManager(): TwitterLoginManager? {
        if (twitterLoginManager == null)
            twitterLoginManager = TwitterLoginManager(activity, onLoginListener)
        return twitterLoginManager
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookLoginManager?.handleActivityResult(requestCode, resultCode, data)
        googleLoginManager?.handleActivityResult(requestCode, data)
        twitterLoginManager?.handleActivityResult(requestCode, resultCode, data)
    }

    fun release() {
        facebookLoginManager?.release()
    }
}