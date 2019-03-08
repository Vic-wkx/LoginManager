![release](https://img.shields.io/badge/release-1.0-green.svg)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# LoginManager

## Description

This is a library to help you add login by Google, Facebook and Twitter to your Android app.

## Download

Step 1. Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```
dependencies {
        implementation 'com.github.wkxjc:LoginManager:1.0'
}
```

## How to use

Login by Google, Facebook or Twitter just need one line of code:

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
        Log.d("~~~", "type:$type, token:${auth.token}, email:${auth.email}")
    }

    override fun onLoginFail(type: String, cause: String) {
        Log.d("~~~","type:$type, cause:$cause")
    }
}
```
## Configuration

Before that, you need some prepare work.

Step 1. add this code in your AndroidManifest.xml:

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

Step 2. add this code in your strings.xml, and replace these keys:

```
<string name="google_web_client_id">Your google web client id</string>
<string name="facebook_app_id">Your facebook app id</string>
<string name="twitter_consumer_key">Your twitter consumer key</string>
<string name="twitter_consumer_secret">Your twitter consumer secret</string>
```

Step 3. init LoginManager in Application:

```
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoginManager.init(this)
    }
}
```

## How to get these key

- Facebook: get facebook app id

    1. Go to the [Facebook Console](https://developers.facebook.com/apps/)
    
    2. create an application
    
    3. Choose Setting &rarr; Basic 
    
        &rarr; add Android platform 
        
        &rarr; fill in Google Play Package Name (Whatever the app is published in google play or not, just fill in the package name of your app) 
        
        &rarr; fill in Class Name(Usually, fill in the MainActivity with package name)
        
        &rarr; fill in Key Hashes
            
            How to get Key Hashes?
            
            - On OS X, run:
            ```shell
            keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
            ```
            Then enter password, The initial password is "android"
            - on Windows, run:
            ```shell
            keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl
            base64
            ```
            Then enter password, The initial password is "android"
            
        &rarr; turn on Single Sign On
        
        &rarr; click "Save Changes" button, ignore Google Play verify warning.
        
- Google: get google web client id
    
    1. Go to [Firebase Console](https://console.firebase.google.com/)
    
    2. Create a project, add SHA1 and SHA256 fingerprint in project settings
    
    3. in Develop &rarr; Authentication &rarr; login method &rarr; enable Google Login
    
    3. Download *google-service.json*, move it to app/ package of main module
    
    4. get the web client id in *google-service.json*
    
- Twitter: get twitter consumer key and twitter consumer secret

    1. Go to [Twitter Console](https://developer.twitter.com/en/apps)
    
    2. create application
    
        &rarr; fill in app name
        
        &rarr; fill in description
        
        &rarr; turn on "enable Twitter Login"
        
        &rarr; fill in privacy policy
        
        &rarr; fill in team url
        
    3. add "twittersdk://" to callback url
    
    4. get API Key and API secret key.

## Bugs Feedback

If you have any questions or suggestions, feel free to create an [issue](https://github.com/wkxjc/LoginManager/issues).

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