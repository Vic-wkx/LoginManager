package com.base.library.login.google

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginConstants
import com.base.library.login.common.constants.LoginConstants.Companion.GOOGLE
import com.base.library.login.common.listener.OnLoginListener
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

/**
 * Description:
 * Google登录管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class GoogleLoginManager(private val fragmentActivity: FragmentActivity, private val onLoginListener: OnLoginListener) :
    GoogleApiClient.OnConnectionFailedListener {

    private val signInOptions by lazy {
        val applicationInfo = fragmentActivity.packageManager.getApplicationInfo(
            fragmentActivity.packageName,
            PackageManager.GET_META_DATA
        )
        val metaData = applicationInfo.metaData
        val googleWebClientId = metaData.get("google_web_client_id")?.toString()
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleWebClientId)
            .requestServerAuthCode(googleWebClientId)
            .requestEmail()
            .build()
    }
    private val apiClient by lazy {
        GoogleApiClient.Builder(fragmentActivity)
            .enableAutoManage(fragmentActivity, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
            .build()
    }

    /**
     * Google登录
     */
    fun login() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
        fragmentActivity.startActivityForResult(signInIntent, LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    fun handleActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN) {
            val account = data?.let {
                Auth.GoogleSignInApi.getSignInResultFromIntent(it).signInAccount
            }
            val token = account?.idToken
            if (account == null || token == null || TextUtils.isEmpty(token)) {
                onLoginListener.onLoginFail(GOOGLE, "Google Login fail, token is null, please ensure:\n" +
                        "1. Added SHA1 and SHA256 in Firebase console, and enabled google login\n" +
                        "2. Downloaded the latest google-service.json\n" +
                        "3. The correct web client id is configured in AndroidManifest")
                return
            }
            val auth = LoginAuth().apply {
                this.token = token
                this.name = account.displayName ?: ""
                this.avatar = account.photoUrl.toString()
                this.email = account.email ?: ""
            }
            onLoginListener.onLoginSuccess(GOOGLE, auth)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        onLoginListener.onLoginFail(GOOGLE, "Google login fail:$connectionResult")
    }

}