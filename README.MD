# LoginManager

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
        implementation 'com.github.wkxjc:LoginManager:Tag'
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

add this code in your AndroidManifest.xml:

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

add this code in your strings.xml, and replace these keys:

```
<string name="google_web_client_id">Your google web client id</string>
<string name="facebook_app_id">Your facebook app id</string>
<string name="twitter_consumer_key">Your twitter consumer key</string>
<string name="twitter_consumer_secret">Your twitter consumer secret</string>
```


