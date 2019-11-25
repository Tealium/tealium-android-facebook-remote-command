package com.tealium.remotecommands.facebook

import Event
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
    lateinit var mockTracker: FacebookAppEventsTrackable

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
    fun standardEventFailure() {
        val isStandardEvent = facebookRemoteCommand.standardEvent("notanevent")
        Assert.assertNull(isStandardEvent)
    }

    @Test
    fun logEventCallsEventNameOnly() {
        every {
            mockTracker.logEvent("AdImpression", any<Bundle>())
        } just Runs

        val payload = JSONObject()
        payload.put("event", JSONObject())
        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockTracker.logEvent("AdImpression", any<Bundle>())
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logEventCallsValueToSumWithoutBundle() {
        val payload = JSONObject()
        payload.put(Event.VALUE_TO_SUM, 1.0)
        every {
            mockTracker.logEvent("AdImpression", 1.0)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockTracker.logEvent("AdImpression", 1.0)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logEventCallsBundleWithoutValueToSum() {
        val payload = JSONObject()
        val eventParameters = JSONObject()
        eventParameters.put("key1", "value1")
        eventParameters.put("key2", 123)
        payload.put(Event.EVENT_PARAMETERS, eventParameters)

        every {
            mockTracker.logEvent("AdImpression", any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockTracker.logEvent("AdImpression", any<Bundle>())
        }
        confirmVerified(mockTracker)
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
            mockTracker.logEvent("AdImpression", 1.0, any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("adimpression"), payload)
        verify {
            mockTracker.logEvent("AdImpression", 1.0, any<Bundle>())
        }
        confirmVerified(mockTracker)
    }
}