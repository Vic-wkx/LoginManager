[![](https://jitpack.io/v/wkxjc/LoginManager.svg)](https://jitpack.io/#wkxjc/LoginManager)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# LoginManager 中文文档 | [English Doc](https://github.com/wkxjc/LoginManager/blob/master/README.MD)

<img src="screenshots/loginDemo.gif" width="240" align="right" hspace="20">

## 描述

这是一个帮助添加Google，Facebook，Twitter登录的库。

## 下载

第一步. 在根目录的*build.gradle*中添加以下代码:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

第二步. 添加gradle依赖

```
dependencies {
        implementation 'com.github.wkxjc:LoginManager:1.3'
        // 确保添加了support-design依赖，可以使用你自己使用的版本
        implementation 'com.android.support:design:28.0.0'
}
```

## 如何使用

Google, Facebook 或 Twitter 登录仅需要一行代码:

```
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
        Log.d("~~~","Login Result: Fail\n\nLogin type:$type\n\ncause:$cause")
        tv_login_result.text = "Login Result: Fail\n\nLogin type:$type\n\ncause:$cause"
    }
}
```
## 配置

在这之前，你需要一些准备工作.

第一步. 在 *AndroidManifest.xml* 添加以下代码:

```
<!--Google Login-->
<meta-data
        android:name="google_web_client_id"
        android:value="@string/google_web_client_id"/>
<!--Facebook Login-->
<meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="@string/facebook_app_id"/>
<!--Twitter Login-->
<meta-data
        android:name="twitter_consumer_key"
        android:value="@string/twitter_consumer_key"/>
<meta-data
        android:name="twitter_consumer_secret"
        android:value="@string/twitter_consumer_secret"/>
```

第二步. 在 *strings.xml* 中添加以下代码, 并替换这些key:

```
<string name="google_web_client_id">Your google web client id</string>
<string name="facebook_app_id">Your facebook app id</string>
<string name="twitter_consumer_key">Your twitter consumer key</string>
<string name="twitter_consumer_secret">Your twitter consumer secret</string>
```

第三步. 在Application中初始化LoginManager:

```
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoginManager.init(this)
    }
}
```

## 如何获取这些key

- Facebook: 获取 facebook app id

    1. 访问 [Facebook控制台](https://developers.facebook.com/apps/)
    
    2. 创建一个应用
    
    3. 选择 设置 &rarr; 基本 
    
        &rarr; 添加 Android 平台 
        
        &rarr; 填写 Google Play Package Name (无论这个app是否在 google play 上架, 只要填写app的包名即可) 
        
        &rarr; 填写 Class Name(通常填入带包名的 MainActivity)
        
        &rarr; 填写 Key Hashes
            
            如何获取 Key Hashes?
            
            - 在Mac上, 运行:
            
            keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
            
            然后输入密码, 初始密码是 "android"
            
            - 在Windows上, 运行:
            
            keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl base64
            
            然后输入密码, 初始密码是 "android"
            
        &rarr; 打开 "单点登录"
        
        &rarr; 点击 "保存修改" 按钮, 忽略 Google Play 验证包名警告.
        
- Google: 获取 google web client id
    
    1. 访问 [Firebase 控制台](https://console.firebase.google.com/)
    
    2. 创建一个项目,在项目设置中添加 SHA1 和 SHA256 指纹
            
            如何获取 SHA1 和 SHA256 指纹？
            
            (1). 获取调试签名 SHA1 和 SHA256
            
            - 在Mac上，运行：
            
            keytool -exportcert -list -v \
            -alias androiddebugkey -keystore ~/.android/debug.keystore
            
            - 在Windows上, 运行:
            
            keytool -exportcert -list -v \
            -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore
            
            然后输入密码, 初始密码是 "android"
            
            (1). 获取正式签名 SHA1 和 SHA256
            
            keytool -exportcert -list -v \
            -alias 密钥名 -keystore 密钥库路径
            
            然后输入密钥密码即可。
            
    3. 在 开发 &rarr; Authentication &rarr; 登录方法 &rarr; 启用Google登录
    
    4. 下载 *google-service.json*, 在 *google-service.json* 中获取 google web client id
    
- Twitter: 获取 twitter consumer key 和 twitter consumer secret

    1. 访问 [Twitter 控制台](https://developer.twitter.com/en/apps)
    
    2. 创建应用
    
        &rarr; 填写 app 名字
        
        &rarr; 填写描述
        
        &rarr; 打开 "启用 Twitter 登录"
        
        &rarr; 填写隐私权政策
        
        &rarr; 填写 team url
        
    3. 将 "twittersdk://" 添加到 callback url
    
    4. 获取 API Key 和 API secret key.

## 不希望继承BaseLoginActivity?

你可以仿照 BaseLoginActivity，直接使用 LoginManager:

```
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
```

## 不希望添加全部的登录方式？

你可以单独使用其中任何一个功能。

## Bug 反馈

如果您有任何反馈或建议，欢迎提交到 [Github issues](https://github.com/wkxjc/LoginManager/issues).

## LICENSE

    Copyright (c) 2018-present, (Company name) Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.