package com.tealium.example

import android.app.Application
import android.os.Build
import android.webkit.WebView
import com.tealium.library.Tealium
import com.tealium.remotecommands.facebook.FacebookRemoteCommand

class App: Application() {

    lateinit var tealium: Tealium

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        val config = Tealium.Config.create(this, "tealiummobile", "facebook", "dev")
        config.forceOverrideLogLevel = "dev"
        tealium = Tealium.createInstance(TealiumHelper.instanceName, config)
        val facebookRemoteCommand = FacebookRemoteCommand(this
        )
        tealium.addRemoteCommand(facebookRemoteCommand)
    }
}