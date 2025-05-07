package com.tealium.remotecommands.facebook

import android.os.Bundle
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StandardEventTest {
    @MockK
    lateinit var mockInstance: FacebookCommand

    @InjectMockKs
    var facebookRemoteCommand: FacebookRemoteCommand = FacebookRemoteCommand(null)
    
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun standardEventSuccess() {
        val isStandardEvent = facebookRemoteCommand.standardEvent("adimpression")
        Assert.assertNotNull(isStandardEvent)
    }

    @Test
    fun logEventCallsEventNameOnly() {
        every {
            mockInstance.logEvent("AdImpression", any<Bundle>())
        } just Runs

        val payload = JSONObject()
        payload.put("event", JSONObject())
        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockInstance.logEvent("AdImpression", any<Bundle>())
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun logCustomEvent() {
        every {
            mockInstance.logEvent("customfbevent")
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("customfbevent"), JSONObject())
        verify {
            mockInstance.logEvent("customfbevent")
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun logEventCallsValueToSumWithoutBundle() {
        val payload = JSONObject()
        payload.put(Event.VALUE_TO_SUM, 1.0)
        every {
            mockInstance.logEvent("AdImpression", 1.0)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockInstance.logEvent("AdImpression", 1.0)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun logEventCallsBundleWithoutValueToSum() {
        val payload = JSONObject()
        val eventParameters = JSONObject()
        eventParameters.put("key1", "value1")
        eventParameters.put("key2", 123)
        payload.put(Event.EVENT_PARAMETERS, eventParameters)

        every {
            mockInstance.logEvent("AdImpression", any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockInstance.logEvent("AdImpression", any<Bundle>())
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun logEventCallsValueToSumWithBundle() {
        val payload = JSONObject()
        payload.put(Event.VALUE_TO_SUM, 1.0)

        val eventParameters = JSONObject()
        eventParameters.put("key1", "value1")
        eventParameters.put("key2", 123)
        payload.put(Event.EVENT_PARAMETERS, eventParameters)

        every {
            mockInstance.logEvent("AdImpression", 1.0, any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockInstance.logEvent("AdImpression", 1.0, any<Bundle>())
        }
        confirmVerified(mockInstance)
    }
}