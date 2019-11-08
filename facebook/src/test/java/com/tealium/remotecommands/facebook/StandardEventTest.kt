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
    fun isStandardEventSuccess() {
        val isStandardEvent = facebookRemoteCommand.isStandardEvent("fb_mobile_level_achieved")
        Assert.assertTrue(isStandardEvent)
    }

    @Test
    fun isStandardEventFailure() {
        val isStandardEvent = facebookRemoteCommand.isStandardEvent("notanevent")
        Assert.assertFalse(isStandardEvent)
    }

    @Test
    fun logEventCallsEventNameOnly() {
        every {
            mockTracker.logEvent("fb_mobile_catalog_update")
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("fb_mobile_catalog_update"), JSONObject())
        verify {
            mockTracker.logEvent("fb_mobile_catalog_update")
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logEventCallsValueToSumWithoutBundle() {
        val payload = JSONObject()
        payload.put(Event.VALUE_TO_SUM, 1.0)
        every {
            mockTracker.logEvent("fb_mobile_catalog_update", 1.0)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("fb_mobile_catalog_update"), payload)
        verify {
            mockTracker.logEvent("fb_mobile_catalog_update", 1.0)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logEventCallsBundleWithoutValueToSum() {
        val payload = JSONObject()
        val eventParameters = JSONObject()
        eventParameters.put("key1", "value1")
        eventParameters.put("key2", 123)
        payload.put(Event.EVENT, eventParameters)

        every {
            mockTracker.logEvent("fb_mobile_catalog_update", any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("fb_mobile_catalog_update"), payload)
        verify {
            mockTracker.logEvent("fb_mobile_catalog_update", any<Bundle>())
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
        payload.put(Event.EVENT, eventParameters)

        every {
            mockTracker.logEvent("fb_mobile_catalog_update", 1.0, any<Bundle>())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf("fb_mobile_catalog_update"), payload)
        verify {
            mockTracker.logEvent("fb_mobile_catalog_update", 1.0, any<Bundle>())
        }
        confirmVerified(mockTracker)
    }
}