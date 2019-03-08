package com.base.library.login.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.base.library.login.LoginManager
import com.base.library.login.common.listener.OnLoginListener

/**
 * Description:
 * 登录基类，可以继承此类实现登录，或者仿照此类中的方法调用LoginManager
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/18
 */
abstract class BaseLoginActivity : AppCompatActivity(), OnLoginListener {

    private val loginManager by lazy { LoginManager(this, this) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginManager.handleActivityResult(requestCode, resultCode, data)
    }

    fun loginBy(type: String) {
        loginManager.loginBy(type)
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            loginManager.release()
        }
    }
}