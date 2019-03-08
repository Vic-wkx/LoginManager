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
 * Facebook登录，需要用浏览器打开 Facebook开发者平台：https://developers.facebook.com/apps/
 * 1.创建应用，将应用编号赋值给 gradle.properties 的 facebook_app_id 变量
 * 2.在Facebook开发者平台控制台的 设置->基本 中添加Android平台，填入Google Play包名、类名、密钥散列、隐私权政策、启用单点登录并保存
 * 4.在Facebook开发者平台控制台公开发布应用
 *
 * Google登录，需要用浏览器打开 firebase控制台：firebase.com
 * 1.创建项目，身份验证中，启用Google登录
 * 2.项目设置中，填入SHA1和包名
 * 3.下载google_service.json，放入主工程app模块下
 * 4.在 google-service.json 中找到网页客户端ID，将其赋值给 gradle.properties 的 google_web_client_id 变量
 *
 * Twitter登录，需要在Twitter开发者平台：https://developer.twitter.com/en/apps
 * 1.创建应用，填写app名，描述，勾选"启用twitter登录"，填写隐私权政策,team url
 * 2.在callback url中添加：twittersdk://
 * 3.将API Key赋值给 gradle.properties 的twitter_consumer_key，API secret key赋值给 gradle.properties 的twitter_consumer_secret
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class LoginManager(val activity: FragmentActivity, private val onLoginListener: OnLoginListener) {
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