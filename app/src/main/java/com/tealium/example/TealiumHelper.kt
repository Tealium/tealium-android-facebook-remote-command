package com.tealium.example

import android.app.Application
import android.webkit.WebView
import com.tealium.core.*
import com.tealium.dispatcher.TealiumEvent
import com.tealium.dispatcher.TealiumView
import com.tealium.lifecycle.Lifecycle
import com.tealium.remotecommanddispatcher.RemoteCommands
import com.tealium.remotecommanddispatcher.remoteCommands
import com.tealium.remotecommands.facebook.FacebookRemoteCommand

object TealiumHelper {
    private const val TEALIUM_MAIN = "main"

    @JvmStatic
    fun initialize(application: Application) {
        WebView.setWebContentsDebuggingEnabled(true)

        val config = TealiumConfig(application,
            "tealiummobile",
            "facebook-tag",
            Environment.DEV).apply {
            useRemoteLibrarySettings = true
            modules.add(Modules.Lifecycle)
            // TagManagement controlled RemoteCommand
            // dispatchers.add(Dispatchers.TagManagement)

            // JSON controlled RemoteCommand
            dispatchers.add(Dispatchers.RemoteCommands)
        }

        val facebookRemoteCommand = FacebookRemoteCommand(application)

        Tealium.create(TEALIUM_MAIN, config) {
            // Remote Command Tag - requires TiQ
            // remoteCommands?.add(facebookRemoteCommand)

            // JSON Remote Command - requires filename or remote url
            remoteCommands?.add(facebookRemoteCommand, "facebook.json")
        }
    }

    @JvmStatic
    fun trackView(viewName: String, data: Map<String, Any>? = null) {
        val instance: Tealium? = Tealium[TEALIUM_MAIN]

        // Instance can be remotely destroyed through publish settings
        instance?.track(TealiumView(viewName, data))
    }

    @JvmStatic
    fun trackEvent(eventName: String, data: Map<String, Any>? = null) {
        val instance: Tealium? = Tealium[TEALIUM_MAIN]

        // Instance can be remotely destroyed through publish settings
        instance?.track(TealiumEvent(eventName, data))
    }

}
