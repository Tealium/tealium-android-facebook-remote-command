package com.tealium.remotecommands.facebook

import Advertiser
import AutoInit
import AutoLog
import Commands
import Event.VALUE_TO_SUM
import Flush
import Purchase
import User
import android.os.Bundle
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class FacebookRemoteCommandTest {

    @MockK
    lateinit var mockInstance: FacebookCommand

    @InjectMockKs
    var facebookRemoteCommand: FacebookRemoteCommand = FacebookRemoteCommand(null)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun splitCommands() {
        val json = JSONObject()
        json.put("command_name", "viewedcontent,addtocart,    customizeProducT    ")
        val commands = facebookRemoteCommand.splitCommands(json)

       assertEquals(3, commands.count())
       assertEquals("viewedcontent", commands[0])
       assertEquals("addtocart", commands[1])
       assertEquals("customizeproduct", commands[2])
    }

    @Test
    fun logPurchaseCalledWithPurchaseObject() {
        val purchaseProperties = JSONObject()
        purchaseProperties.put(Purchase.PURCHASE_AMOUNT, 9.99)
        purchaseProperties.put(Purchase.PURCHASE_CURRENCY, "USD")

        val purchase = JSONObject()
        purchase.put(Purchase.PURCHASE, purchaseProperties)

        every { mockInstance.logPurchase(any(), any()) } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)
        verify {
            mockInstance.logPurchase(9.99.toBigDecimal(), Currency.getInstance("USD"))
        }
        confirmVerified(mockInstance)
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

        every { mockInstance.logPurchase(any(), any(), any()) } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PURCHASE), purchase)

        verify {
            mockInstance.logPurchase(9.99.toBigDecimal(), Currency.getInstance("USD"), any())
        }
        confirmVerified(mockInstance)
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
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
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
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setUserCalledWithUserData() {
        val userProperties = JSONObject()
        userProperties.put(User.EMAIL, "test@test.com")

        val user = JSONObject()
        user.put(User.USER_DATA, userProperties)

        every {
            mockInstance.setUserData(
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
            mockInstance.setUserData(
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
        confirmVerified(mockInstance)
    }

    @Test
    fun setUserNotCalledWithoutUserKey() {
        val userProperties = JSONObject()
        userProperties.put(User.EMAIL, "test@test.com")
        val user = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER), user)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setUserIDCalledWithUserID() {
        val payload = JSONObject()
        payload.put(User.USER_ID, "testuser")

        every { mockInstance.setUserID("testuser") } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockInstance.setUserID("testuser")
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setUserIDNotCalledWithoutUserIDKey() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setUserIDNotCalledWithEmptyUserID() {
        val payload = JSONObject()
        payload.put(User.USER_ID, "")

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_USER_ID), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun clearUserDataCalledWithCommand() {
        every {
            mockInstance.clearUserData()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.CLEAR_USER_DATA), JSONObject())
        verify {
            mockInstance.clearUserData()
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun clearUserIDCalledWithCommand() {
        every {
            mockInstance.clearUserID()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.CLEAR_USER_ID), JSONObject())
        verify {
            mockInstance.clearUserID()
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun updateUserValueCalledWithUserKeyAndUserValue() {
        val payload = JSONObject()
        payload.put(User.USER_KEY, "key1")
        payload.put(User.USER_VALUE, "value1")

        every {
            mockInstance.updateUserProperties(any())
        } just Runs


        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockInstance.updateUserProperties(any())
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun updateUserValueNotCalledWithoutUserKey() {
        val payload = JSONObject()
        payload.put(User.USER_VALUE, "value1")

        every {
            mockInstance.updateUserProperties(any())
        } just Runs


        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun updateUserValueNotCalledWithEmptyUserKey() {
        val payload = JSONObject()
        payload.put(User.USER_KEY, "")
        payload.put(User.USER_VALUE, "value1")

        every {
            mockInstance.updateUserProperties(any())
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.UPDATE_USER_VALUE), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setFlushBehaviorCalledWithFlushBehaviorAutoKey() {
        val payload = JSONObject()
        payload.put(Flush.FLUSH_BEHAVIOR, Flush.AUTO)

        every {
            mockInstance.setFlushBehavior(Flush.AUTO)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockInstance.setFlushBehavior(Flush.AUTO)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setFlushBehaviorCalledWithFlushBehaviorExplicitKey() {
        val payload = JSONObject()
        payload.put(Flush.FLUSH_BEHAVIOR, Flush.EXPLICIT_ONLY)

        every {
            mockInstance.setFlushBehavior(Flush.EXPLICIT_ONLY)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockInstance.setFlushBehavior(Flush.EXPLICIT_ONLY)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setFlushBehaviorNotCalledWithoutFlushBehaviorKey() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_FLUSH_BEHAVIOR), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun flushCalled() {
        every {
            mockInstance.flush()
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.FLUSH), JSONObject())
        verify {
            mockInstance.flush()
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setPushRegistrationIdNotCalledWithoutRegistrationId() {
        val payload = JSONObject()

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_PUSH_REGISTRATION_ID), payload)
        verify {
            mockInstance wasNot Called
        }
        confirmVerified(mockInstance)
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
        assertEquals(bundle.getDouble("double"), 0.123, 0.1)
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
    fun mapJsonToBundleDoesNotMapValueToSum() {
        val json = JSONObject()
        val bundle = Bundle()
        json.put(VALUE_TO_SUM, 10.00)
        FacebookRemoteCommand.mapJsonToBundle(json, bundle)
        assertNotSame(10.00, bundle.getDouble(VALUE_TO_SUM))
    }

    @Test
    fun setAutoLogAppEventsCalled() {
        val payload = JSONObject()
        payload.put(AutoLog.AUTO_LOG, true)
        every {
            mockInstance.enableAutoLogAppEvents(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_LOG_APP_EVENTS), payload)
        verify {
            mockInstance.enableAutoLogAppEvents(true)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setAutoLogAppEventsSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockInstance.enableAutoLogAppEvents(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_LOG_APP_EVENTS), payload)
        verify {
            mockInstance.enableAutoLogAppEvents(false)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setAutoInitializedCalled() {
        val payload = JSONObject()
        payload.put(AutoInit.AUTO_INITIALIZED, true)
        every {
            mockInstance.enableAutoInitialize(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_INITIALIZED), payload)
        verify {
            mockInstance.enableAutoInitialize(true)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setAutoInitSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockInstance.enableAutoInitialize(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_AUTO_INITIALIZED), payload)
        verify {
            mockInstance.enableAutoInitialize(false)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setAdvertiserIDCalled() {
        val payload = JSONObject()
        payload.put(Advertiser.ADVERTISER_COLLECTION, true)
        every {
            mockInstance.enableAdvertiserIDCollection(true)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_ADVERTISER_ID_COLLECTION), payload)
        verify {
            mockInstance.enableAdvertiserIDCollection(true)
        }
        confirmVerified(mockInstance)
    }

    @Test
    fun setAdvertiserIDSetToFalseWithoutKey() {
        val payload = JSONObject()
        every {
            mockInstance.enableAdvertiserIDCollection(false)
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.SET_ADVERTISER_ID_COLLECTION), payload)
        verify {
            mockInstance.enableAdvertiserIDCollection(false)
        }
        confirmVerified(mockInstance)
    }
}