package com.example.login

import android.os.Bundle
import android.util.Log
import com.base.library.login.base.BaseLoginActivity
import com.base.library.login.common.bean.LoginAuth
import com.base.library.login.common.constants.LoginConstants
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Description:
 * Main Page
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/3/8
 */
class MainActivity : BaseLoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_google_login.setOnClickListener {
            loginBy(LoginConstants.GOOGLE)
        }

        btn_facebook_login.setOnClickListener {
            loginBy(LoginConstants.FACEBOOK)
        }

        btn_twitter_login.setOnClickListener {
            loginBy(LoginConstants.TWITTER)
        }
    }

    override fun onLoginSuccess(type: String, auth: LoginAuth) {
        Log.d("~~~", "Login Result: Success\n\nLogin type:$type\n\nauth:$auth")
        tv_login_result.text = "Login Result: Success\n\nLogin type:$type\n\nauth:$auth"
    }

    override fun onLoginFail(type: String, cause: String) {
        Log.d("~~~", "Login Result: Fail\n\nLogin type:$type\n\ncause:$cause")
        tv_login_result.text = "Login Result: Fail\n\nLogin type:$type\n\ncause:$cause"
    }
}
