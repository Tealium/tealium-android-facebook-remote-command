package com.tealium.remotecommands.facebook

import Advertiser
import AutoInit
import AutoLog
import Commands
import Flush
import Purchase
import User
import android.os.Bundle
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class FacebookRemoteCommandTest {

    @MockK
    lateinit var mockTracker: FacebookAppEventsTrackable

    @InjectMockKs
    var facebookRemoteCommand: FacebookRemoteCommand = FacebookRemoteCommand(null)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun splitCommands() {
        val json = JSONObject()
        json.put("command_name", "viewedcontent,addtocart,customizeproduct")
        val commands = facebookRemoteCommand.splitCommands(json)

        Assert.assertEquals(3, commands.count())
        Assert.assertEquals("viewedcontent", commands[0])
        Assert.assertEquals("addtocart", commands[1])
        Assert.assertEquals("customizeproduct", commands[2])
    }

    @Test
    fun logPurchaseCalledWithPurchaseObject() {
        val purchaseProperties = JSONObject()
        purchaseProperties.put(Purchase.PURCHASE_AMOUNT, 9.99)
        purchaseProperties.put(Purchase.PURCHASE_CURRENCY, "USD")

        val purchase = JSONObject()
        purchase.put(Purchase.PURCHASE, purchaseProperties)

        every { mockTracker.logPurchase(any(), any()) } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)
        verify {
            mockTracker.logPurchase(9.99.toBigDecimal(), Currency.getInstance("USD"))
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logPurchaseCalledWithPurchaseObjectAndPurchaseParameters() {
        val purchaseProperties = JSONObject()
        purchaseProperties.put(Purchase.PURCHASE_AMOUNT, 9.99)
        purchaseProperties.put(Purchase.PURCHASE_CURRENCY, "USD")

        val parameters = JSONObject()
        parameters.put("key1", "value1")
        parameters.put("key2", 2)
        purchaseProperties.put(Purchase.PURCHASE_PARAMETERS, parameters)

        val purchase = JSONObject()
        purchase.put(Purchase.PURCHASE, purchaseProperties)

        every { mockTracker.logPurchase(any(), any(), any()) } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)

        verify {
            mockTracker.logPurchase(9.99.toBigDecimal(), Currency.getInstance("USD"), any())
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logPurchaseNotCalledWithoutAmount() {
        val purchaseProperties = JSONObject()
        purchaseProperties.put(Purchase.PURCHASE_AMOUNT, "nine")
        purchaseProperties.put(Purchase.PURCHASE_CURRENCY, "USD")

        val purchase = JSONObject()
        purchase.put(Purchase.PURCHASE, purchaseProperties)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logPurchaseNotCalledWithoutCurrency() {
        val purchaseProperties = JSONObject()
        purchaseProperties.put(Purchase.PURCHASE_AMOUNT, 9.99)
        purchaseProperties.put(Purchase.PURCHASE_CURRENCY, "USDzzzz")

        val purchase = JSONObject()
        purchase.put(Purchase.PURCHASE, purchaseProperties)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setUserCalledWithUserData() {
        val userProperties = JSONObject()
        userProperties.put(User.EMAIL, "test@test.com")

        val user = JSONObject()
        user.put(User.USER_DATA, userProperties)

        every {
            mockTracker.setUserData(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER), user)
        verify {
            mockTracker.setUserData(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setUserNotCalledWithoutUserKey() {
        val userProperties = JSONObject()
        userProperties.put(User.EMAIL, "test@test.com")
        val user = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER), user)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setUserIDCalledWithUserID() {
        val payload = JSONObject()
        payload.put(User.USER_ID, "testuser")

        every { mockTracker.setUserID("testuser") } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockTracker.setUserID("testuser")
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setUserIDNotCalledWithoutUserIDKey() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setUserIDNotCalledWithEmptyUserID() {
        val payload = JSONObject()
        payload.put(User.USER_ID, "")

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun clearUserDataCalledWithCommand() {
        every {
            mockTracker.clearUserData()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.CLEAR_USER_DATA), JSONObject())
        verify {
            mockTracker.clearUserData()
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun clearUserIDCalledWithCommand() {
        every {
            mockTracker.clearUserID()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.CLEAR_USER_ID), JSONObject())
        verify {
            mockTracker.clearUserID()
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun updateUserValueCalledWithUserKeyAndUserValue() {
        val payload = JSONObject()
        payload.put(User.USER_KEY, "key1")
        payload.put(User.USER_VALUE, "value1")

        every {
            mockTracker.updateUserProperties(any())
        } just Runs


        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockTracker.updateUserProperties(any())
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun updateUserValueNotCalledWithoutUserKey() {
        val payload = JSONObject()
        payload.put(User.USER_VALUE, "value1")

        every {
            mockTracker.updateUserProperties(any())
        } just Runs


        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun updateUserValueNotCalledWithEmptyUserKey() {
        val payload = JSONObject()
        payload.put(User.USER_KEY, "")
        payload.put(User.USER_VALUE, "value1")

        every {
            mockTracker.updateUserProperties(any())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setFlushBehaviorCalledWithFlushBehaviorAutoKey() {
        val payload = JSONObject()
        payload.put(Flush.FLUSH_BEHAVIOR, Flush.AUTO)

        every {
            mockTracker.setFlushBehavior(Flush.AUTO)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockTracker.setFlushBehavior(Flush.AUTO)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setFlushBehaviorCalledWithFlushBehaviorExplicitKey() {
        val payload = JSONObject()
        payload.put(Flush.FLUSH_BEHAVIOR, Flush.EXPLICIT_ONLY)

        every {
            mockTracker.setFlushBehavior(Flush.EXPLICIT_ONLY)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockTracker.setFlushBehavior(Flush.EXPLICIT_ONLY)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setFlushBehaviorNotCalledWithoutFlushBehaviorKey() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun flushCalled() {
        every {
            mockTracker.flush()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.FLUSH), JSONObject())
        verify {
            mockTracker.flush()
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setPushRegistrationIdNotCalledWithoutRegistrationId() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_PUSH_REGISTRATION_ID), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun mapJsonToBundleMapsKeyValues() {
        val json = JSONObject()
        val bundle = Bundle()
        val stringArray = arrayOf("a", "b", "c")
        val intArray = arrayOf(4, 5, 6)
        val doubleArray = arrayOf(0.0, 0.1, 0.2, 0.3)
        val booleanArray = arrayOf(true, false, true)

        json.put("string", "123")
        json.put("int", 123)
        json.put("double", 0.123)
        json.put("boolean", true)
        json.put("stringArray", stringArray)
        json.put("intArray", intArray)
        json.put("doubleArray", doubleArray)
        json.put("booleanArray", booleanArray)
        FacebookRemoteCommand.mapJsonToBundle(json, bundle)

        assertEquals(bundle.getString("string"), "123")
        assertEquals(bundle.getInt("int"), 123)
        assertEquals(bundle.getDouble("double"), 0.123)
        assertEquals(bundle.getBoolean("boolean"), true)

        bundle.getStringArray("stringArray")?.forEachIndexed { index, value ->
            assertEquals(stringArray[index], value)
        }
        bundle.getIntArray("intArray")?.forEachIndexed { index, value ->
            assertEquals(intArray[index], value)
        }
        bundle.getDoubleArray("doubleArray")?.forEachIndexed { index, value ->
            assertEquals(doubleArray[index], value)
        }
        bundle.getBooleanArray("booleanArray")?.forEachIndexed { index, value ->
            assertEquals(booleanArray[index], value)
        }
    }

    @Test
    fun setAutoLogAppEventsCalled() {
        val payload = JSONObject()
        payload.put(AutoLog.AUTO_LOG, true)
        every {
            mockTracker.enableAutoLogAppEvents(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_LOG_APP_EVENTS), payload)
        verify {
            mockTracker.enableAutoLogAppEvents(true)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setAutoLogAppEventsSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockTracker.enableAutoLogAppEvents(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_LOG_APP_EVENTS), payload)
        verify {
            mockTracker.enableAutoLogAppEvents(false)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setAutoInitializedCalled() {
        val payload = JSONObject()
        payload.put(AutoInit.AUTO_INITIALIZED, true)
        every {
            mockTracker.enableAutoInitialize(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_INITIALIZED), payload)
        verify {
            mockTracker.enableAutoInitialize(true)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setAutoInitSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockTracker.enableAutoInitialize(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_INITIALIZED), payload)
        verify {
            mockTracker.enableAutoInitialize(false)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setAdvertiserIDCalled() {
        val payload = JSONObject()
        payload.put(Advertiser.ADVERTISER_COLLECTION, true)
        every {
            mockTracker.enableAdvertiserIDCollection(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_ADVERTISER_ID_COLLECTION), payload)
        verify {
            mockTracker.enableAdvertiserIDCollection(true)
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun setAdvertiserIDSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockTracker.enableAdvertiserIDCollection(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_ADVERTISER_ID_COLLECTION), payload)
        verify {
            mockTracker.enableAdvertiserIDCollection(false)
        }
        confirmVerified(mockTracker)
    }
}