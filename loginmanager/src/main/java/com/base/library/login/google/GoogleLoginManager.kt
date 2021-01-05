package com.base.library.login.google

import android.content.Intent
import android.content.pm.PackageManager
import androidx.fragment.app.FragmentActivity
import android.text.TextUtils
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginConstants
import com.base.library.login.common.constants.LoginConstants.GOOGLE_CLIENT_ID
import com.base.library.login.common.constants.LoginType
import com.base.library.login.common.listener.ILoginManager
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
 * Date:    2018/12/4
 */
class GoogleLoginManager(private val fragmentActivity: androidx.fragment.app.FragmentActivity, private val onLoginListener: OnLoginListener) :
    GoogleApiClient.OnConnectionFailedListener, ILoginManager {

    private val signInOptions by lazy {
        val applicationInfo = fragmentActivity.packageManager.getApplicationInfo(
            fragmentActivity.packageName,
            PackageManager.GET_META_DATA
        )
        val metaData = applicationInfo.metaData
        val googleWebClientId = metaData.get(GOOGLE_CLIENT_ID)?.toString()
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
    override fun login() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
        fragmentActivity.startActivityForResult(signInIntent, LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LoginConstants.REQUEST_CODE_GOOGLE_SIGN_IN) {
            val account = data?.let {
                Auth.GoogleSignInApi.getSignInResultFromIntent(it)?.signInAccount
            }
            val token = account?.idToken
            if (account == null || token == null || TextUtils.isEmpty(token)) {
                onLoginListener.onLoginFail(
                    LoginType.Google, "Google Login fail, token is null, please ensure:\n" +
                            "1. Added SHA1 and SHA256 (debug and release jks) in Firebase console, and enabled google login\n" +
                            "2. The correct web client id is configured in AndroidManifest"
                )
                return
            }
            val auth = LoginAuth().apply {
                this.token = token
                this.name = account.displayName ?: ""
                this.avatar = account.photoUrl.toString()
                this.email = account.email ?: ""
            }
            onLoginListener.onLoginSuccess(LoginType.Google, auth)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        onLoginListener.onLoginFail(LoginType.Google, "Google login fail:$connectionResult")
    }

    override fun release() {

    }

}