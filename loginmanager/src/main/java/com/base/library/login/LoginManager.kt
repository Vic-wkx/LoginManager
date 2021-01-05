package com.base.library.login

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import androidx.fragment.app.FragmentActivity
import android.text.TextUtils
import android.util.Log
import com.base.library.login.common.constants.LoginConstants.TWITTER_CONSUMER_KEY
import com.base.library.login.common.constants.LoginConstants.TWITTER_CONSUMER_SECRET
import com.base.library.login.common.constants.LoginType
import com.base.library.login.common.listener.ILoginManager
import com.base.library.login.common.listener.OnLoginListener
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
 * Date:    2018/12/4
 */
class LoginManager(private val activity: FragmentActivity, private val onLoginListener: OnLoginListener) {

    private val managerMap by lazy { HashMap<LoginType, ILoginManager>() }
    private val managerFactory by lazy { LoginManagerFactory(activity, onLoginListener) }

    companion object {

        fun init(app: Application) {
            initTwitterLogin(app)
        }

        private fun initTwitterLogin(app: Application) {
            val applicationInfo = app.packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
            val metaData = applicationInfo.metaData
            val twitterConsumerKey = metaData.get(TWITTER_CONSUMER_KEY)?.toString()
            val twitterConsumerSecret = metaData.get(TWITTER_CONSUMER_SECRET)?.toString()
            if (TextUtils.isEmpty(twitterConsumerKey) || TextUtils.isEmpty(twitterConsumerSecret)) return
            val config = TwitterConfig.Builder(app)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(twitterConsumerKey, twitterConsumerSecret))
                .debug(BuildConfig.DEBUG)
                .build()
            Twitter.initialize(config)
        }
    }

    fun loginBy(type: LoginType) {
        getLoginManager(type).login()
    }

    private fun getLoginManager(type: LoginType): ILoginManager {
        managerMap[type] ?: let {
            managerMap[type] = managerFactory.create(type)
        }
        return managerMap[type]!!
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        managerMap.values.forEach {
            it.handleActivityResult(requestCode, resultCode, data)
        }
    }

    fun release() {
        managerMap.values.forEach {
            it.release()
        }
        managerMap.clear()
    }
}