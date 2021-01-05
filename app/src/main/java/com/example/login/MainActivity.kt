package com.example.login

import android.os.Bundle
import android.util.Log
import com.base.library.login.base.BaseLoginActivity
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginType
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Description:
 * Main Page
 *
 * @author  Alpinist Wang
 * Date:    2019/3/8
 */
class MainActivity : BaseLoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnGoogleLogin.setOnClickListener {
            loginBy(LoginType.Google)
        }

        btnFacebookLogin.setOnClickListener {
            loginBy(LoginType.Facebook)
        }

        btnTwitterLogin.setOnClickListener {
            loginBy(LoginType.Twitter)
        }
    }

    override fun onLoginSuccess(type: LoginType, auth: LoginAuth) {
        Log.d("~~~", "Login Result: Success\n\nLogin type:$type\n\nauth:$auth")
        tvLoginResult.text = "Login Result: Success\n\nLogin type:$type\n\nauth:$auth"
    }

    override fun onLoginFail(type: LoginType, cause: String) {
        Log.d("~~~", "Login Result: Fail\n\nLogin type:$type\n\ncause:$cause")
        tvLoginResult.text = "Login Result: Fail\n\nLogin type:$type\n\ncause:$cause"
    }
}
