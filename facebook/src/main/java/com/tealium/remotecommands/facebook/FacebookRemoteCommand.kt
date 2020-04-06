package com.tealium.remotecommands.facebook

import Advertiser
import AutoInit
import AutoLog
import Commands
import Event
import Event.VALUE_TO_SUM
import Flush
import Product
import ProductItemParameters
import Purchase
import Push
import User
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.facebook.appevents.AppEventsLogger
import com.tealium.internal.tagbridge.RemoteCommand
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*


open class FacebookRemoteCommand : RemoteCommand {

    private val TAG = this::class.java.simpleName

    lateinit var tracker: FacebookAppEventsTrackable
    var application: Application? = null

    /**
     * Constructs a RemoteCommand that integrates with the Facebook App Events SDK to allow Facebook API calls to be implemented through Tealium.
     */
    @JvmOverloads
    constructor(
        application: Application? = null,
        commandId: String = DEFAULT_COMMAND_ID,
        description: String = DEFAULT_COMMAND_DESCRIPTION,
        facebookApplicationId: String? = null
    ) : super(commandId, description) {
        application?.let {
            tracker = FacebookAppEventsTracker(it, facebookApplicationId)
            this.application = it
        }
    }

    @JvmOverloads
    constructor(
        autoInit: Boolean,
        application: Application? = null,
        commandId: String = DEFAULT_COMMAND_ID,
        description: String = DEFAULT_COMMAND_DESCRIPTION
    ) : super(commandId, description) {
        application?.let {
            this.application = it
        }
    }

    companion object {
        val DEFAULT_COMMAND_ID = "facebook"
        val DEFAULT_COMMAND_DESCRIPTION = "Tealium-Facebook Remote Command"
        val REQUIRED_KEY = "key does not exist in the payload."

        /**
         * Maps a JSON object to a Bundle. Does not support nested objects.
         *
         * @param json - JSON object used to set key/value pairs on the Bundle
         * @param bundle - bundle to set key/value pairs that is used in tracking events
         */
        fun mapJsonToBundle(json: JSONObject?, bundle: Bundle) {
            json?.let {
                it.keys().forEach { key ->
                    val value = json[key]
                    when (value) {
                        is Int -> {
                            if (key != VALUE_TO_SUM) {
                                bundle.putInt(key, value)
                            }
                        }
                        is Double -> {
                            if (key != VALUE_TO_SUM) {
                                bundle.putDouble(key, value)
                            }
                        }
                        is Boolean -> bundle.putBoolean(key, value)
                        is Array<*> -> mapArrayToBundle(key, value, bundle)
                        else -> bundle.putString(key, value.toString())
                    }
                }
            }
        }

        /**
         * Maps an array onto a Bundle.
         *
         * @param key - the key to set on the Bundle
         * @param value - the value to set on the Bundle
         * @param bundle - bundle to set key/value pairs that is used in tracking events
         */
        fun <T> mapArrayToBundle(key: String, value: Array<T>, bundle: Bundle) {
            if (value.count() > 0) {
                when (value.first()) {
                    is Double -> {
                        val doubleArray = (value as? ArrayList<Double>)?.toDoubleArray()
                        bundle.putDoubleArray(key, doubleArray)
                    }
                    is Boolean -> {
                        val booleanArray = (value.toList() as? ArrayList<Boolean>)?.toTypedArray()
                            ?.toBooleanArray()
                        bundle.putBooleanArray(key, booleanArray)
                    }
                    is Int -> {
                        val intArray = (value as? ArrayList<Int>)?.toIntArray()
                        bundle.putIntArray(key, intArray)
                    }
                    else -> {
                        val stringArray = (value as? ArrayList<String>)
                        bundle.putStringArrayList(key, stringArray)
                    }
                }
            }
        }
    }

    /**
     * Handles the incoming RemoteCommand response data. Prepares the command and payload for parsing.
     *
     * @param response - the response that includes the command and optional command parameters
     */
    @Throws(Exception::class)
    override fun onInvoke(response: Response) {
        val payload = response.requestPayload
        val commands = splitCommands(payload)
        parseCommands(commands, payload)
    }

    /**
     * Calls the individual commands consecutively with optional parameters from the payload object.
     *
     * @param commands - the list of commands to call
     * @param payload - optional command parameters to be called with specific commands
     */
    fun parseCommands(commands: Array<String>, payload: JSONObject) {
        commands.forEach { command ->
            when (command) {
                Commands.INITIALIZE -> {
                    val applicationId =
                        if (payload.optString(Initialize.APPLICATION_ID).isNotBlank()) payload.optString(
                            Initialize.APPLICATION_ID
                        ) else null
                    application?.let { appContext ->
                        applicationId?.let { appId ->
                            tracker =
                                FacebookAppEventsTracker(
                                    appContext,
                                    appId
                                )
                        } ?: run {
                            Log.e(TAG, "${Initialize.APPLICATION_ID} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.LOG_PURCHASE -> {
                    val purchase: JSONObject? = payload.optJSONObject(Purchase.PURCHASE)
                    purchase?.let {
                        logPurchase(it)
                    } ?: run {
                        Log.e(TAG, "${Purchase.PURCHASE} $REQUIRED_KEY")
                    }
                }
                Commands.SET_USER -> {
                    val userData: JSONObject? = payload.optJSONObject(User.USER_DATA)
                    userData?.let {
                        setUser(it)
                    } ?: run {
                        Log.e(TAG, "${User.USER_DATA} $REQUIRED_KEY")
                    }
                }
                Commands.SET_USER_ID -> {
                    val userId: String? = payload.optString(User.USER_ID)
                    userId?.let {
                        if (it.isNotEmpty()) {
                            tracker.setUserID(it)
                        } else {
                            Log.e(TAG, "${User.USER_ID} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.CLEAR_USER_DATA -> {
                    tracker.clearUserData()
                }
                Commands.CLEAR_USER_ID -> {
                    tracker.clearUserID()
                }
                Commands.UPDATE_USER_VALUE -> {
                    val userValue = payload.optString(User.USER_VALUE)
                    val userKey = payload.optString(User.USER_KEY)
                    val bundle = Bundle()
                    val (key, value) = guardLet(userKey, userValue) { return }
                    if (key.isNotEmpty()) {
                        bundle.putString(key, value)
                        tracker.updateUserProperties(bundle)
                    } else {
                        Log.e(TAG, "${User.USER_KEY} and ${User.USER_VALUE} keys do not exist in the payload.")
                    }
                }
                Commands.LOG_PRODUCT_ITEM -> {
                    val productItem = payload.optJSONObject(Product.PRODUCT_ITEM)
                    productItem?.let {
                        logProductItem(it)
                    } ?: run {
                        Log.e(TAG, "${Product.PRODUCT_ITEM} $REQUIRED_KEY")
                    }
                }
                Commands.SET_FLUSH_BEHAVIOR -> {
                    val flushValue = payload.optString(Flush.FLUSH_BEHAVIOR)
                    if (flushValue.isNotEmpty()) {
                        tracker.setFlushBehavior(flushValue)
                    } else {
                        Log.e(TAG, "${Flush.FLUSH_BEHAVIOR} $REQUIRED_KEY")
                    }
                }
                Commands.FLUSH -> {
                    tracker.flush()
                }
                Commands.SET_PUSH_REGISTRATION_ID -> {
                    val registrationId = payload.optString(Push.REGISTRATION_ID)
                    if (registrationId.isNotEmpty()) {
                        AppEventsLogger.setPushNotificationsRegistrationId(registrationId)
                    } else {
                        Log.e(TAG, "${Push.REGISTRATION_ID} $REQUIRED_KEY")
                    }
                }
                Commands.SET_AUTO_LOG_APP_EVENTS -> {
                    val autoLogEnable = payload.optBoolean(AutoLog.AUTO_LOG)
                    tracker.enableAutoLogAppEvents(autoLogEnable)
                }
                Commands.SET_AUTO_INITIALIZED -> {
                    val autoInitEnable = payload.optBoolean(AutoInit.AUTO_INITIALIZED)
                    tracker.enableAutoInitialize(autoInitEnable)
                }
                Commands.SET_ADVERTISER_ID_COLLECTION -> {
                    val advertiserIDEnable = payload.optBoolean(Advertiser.ADVERTISER_COLLECTION)
                    tracker.enableAdvertiserIDCollection(advertiserIDEnable)
                }
                Commands.EVENT_NAME_ACHIEVED_LEVEL -> {
                    val levelAchieved = payload.optString(Event.EVENT_PARAM_LEVEL)
                    if (levelAchieved.isNotBlank()) {
                        logEvent(Commands.EVENT_NAME_ACHIEVED_LEVEL, 0.0, payload)
                    } else {
                        Log.e(TAG, "${Event.EVENT_PARAM_LEVEL} $REQUIRED_KEY")
                    }
                }
                Commands.EVENT_NAME_UNLOCKED_ACHIEVEMENT -> {
                    val achievement = payload.optString(Event.EVENT_PARAM_DESCRIPTION)
                    if (achievement.isNotBlank()) {
                        logEvent(Commands.EVENT_NAME_UNLOCKED_ACHIEVEMENT, 0.0, payload)
                    } else {
                        Log.e(TAG, "${Event.EVENT_PARAM_DESCRIPTION} $REQUIRED_KEY")
                    }
                }
                Commands.EVENT_NAME_COMPLETED_REGISTRATION -> {
                    val registrationMethod =
                        payload.optString(Event.EVENT_PARAM_REGISTRATION_METHOD)
                    if (registrationMethod.isNotBlank()) {
                        logEvent(Commands.EVENT_NAME_COMPLETED_REGISTRATION, 0.0, payload)
                    } else {
                        Log.e(TAG,
                            "${Event.EVENT_PARAM_REGISTRATION_METHOD} $REQUIRED_KEY")
                    }
                }
                Commands.EVENT_NAME_COMPLETED_TUTORIAL -> {
                    val contentId = payload.optString(Event.EVENT_PARAM_CONTENT_ID)
                    if (contentId.isNotBlank()) {
                        logEvent(Commands.EVENT_NAME_COMPLETED_TUTORIAL, 0.0, payload)
                    } else {
                        Log.e(
                            TAG,
                            "${Event.EVENT_PARAM_CONTENT_ID} $REQUIRED_KEY")
                    }
                }
                Commands.EVENT_NAME_INITIATED_CHECKOUT -> {
                    val valueToSum = payload.optDouble(VALUE_TO_SUM, 0.0)
                    if (valueToSum > 0.0) {
                        logEvent(Commands.EVENT_NAME_INITIATED_CHECKOUT, valueToSum, payload)
                    } else {
                        Log.e(TAG, "${Event.VALUE_TO_SUM} $REQUIRED_KEY")
                    }
                }
                Commands.EVENT_NAME_SEARCHED -> {
                    val contentType = payload.optString(Event.EVENT_PARAM_SEARCH_STRING)
                    if (contentType.isNotBlank()) {
                        logEvent(Commands.EVENT_NAME_SEARCHED, 0.0, payload)
                    } else {
                        Log.e(TAG, "${Event.EVENT_PARAM_SEARCH_STRING} $REQUIRED_KEY")
                    }
                }
                else -> {
                    if (isStandardEvent(command)) {
                        val valueToSum = payload.optDouble(VALUE_TO_SUM, 0.0)
                        logEvent(command, valueToSum, payload)
                    }
                }
            }
        }
    }

    /**
     * Checks if the event is a standard Facebook event name.
     *
     * @param commandName - name of the command
     */
    fun isStandardEvent(commandName: String): Boolean {
        return StandardEvents.standardEventNames.contains(commandName)
    }

    fun logEvent(command: String, valueToSum: Double, eventParameters: JSONObject) {
        val bundle = Bundle()
        if (valueToSum > 0) {
            valueToSum?.let { sumValue ->
                if (eventParameters.length() > 0) {
                    mapJsonToBundle(eventParameters, bundle)
                    if (bundle.isEmpty) {
                        tracker.logEvent(command, sumValue)
                    } else {
                        tracker.logEvent(command, sumValue, bundle)
                    }
                }
            }
        } else {
            if (eventParameters.length() > 0) {
                mapJsonToBundle(eventParameters, bundle)
                tracker.logEvent(command, bundle)
            } else {
                tracker.logEvent(command)
            }
        }
    }

    fun logPurchase(purchase: JSONObject) {
        val amount = purchase.optDouble(Purchase.PURCHASE_AMOUNT) as? Double
        amount?.let {
            if (it.isNaN()) {
                return
            }
        }
        val purchaseAmount = amount?.toBigDecimal() as BigDecimal
        val currencyString = purchase.optString(Purchase.PURCHASE_CURRENCY, "USD")
        val (currency) = guardLet(getCurrency(currencyString)) { return }
        val parameters: JSONObject? = purchase.optJSONObject(Purchase.PURCHASE_PARAMETERS)

        parameters?.let { purchaseParameters ->
            val bundle = Bundle()
            mapJsonToBundle(purchaseParameters, bundle)
            tracker.logPurchase(purchaseAmount, currency, bundle)
        } ?: run {
            tracker.logPurchase(purchaseAmount, currency)
        }
    }

    fun setUser(user: JSONObject) {
        val email = user.optString(User.EMAIL)
        val firstName = user.optString(User.FIRST_NAME)
        val lastName = user.optString(User.LAST_NAME)
        val phone = user.optString(User.PHONE)
        val dateOfBirth = user.optString(User.DATE_OF_BIRTH)
        val gender = user.optString(User.GENDER)
        val city = user.optString(User.CITY)
        val state = user.optString(User.STATE)
        val zip = user.optString(User.ZIP)
        val country = user.optString(User.COUNTRY)

        tracker.setUserData(
            email,
            firstName,
            lastName,
            phone,
            dateOfBirth,
            gender,
            city,
            state,
            zip,
            country
        )
    }

    fun logProductItem(productItem: JSONObject) {
        val productId = productItem.optString(ProductItemParameters.PRODUCT_ID)
        val productAvailability = productItem.optInt(ProductItemParameters.PRODUCT_AVAILABILITY)
        val productCondition = productItem.optInt(ProductItemParameters.PRODUCT_CONDITION)
        val productDescription = productItem.optString(ProductItemParameters.PRODUCT_DESCRIPTION)
        val productImageLink = productItem.optString(ProductItemParameters.PRODUCT_IMAGE_LINK)
        val productLink = productItem.optString(ProductItemParameters.PRODUCT_LINK)
        val productTitle = productItem.optString(ProductItemParameters.PRODUCT_TITLE)
        val amount = productItem.optDouble(ProductItemParameters.PRODUCT_PRICE_AMOUNT) as? Double
        amount?.let {
            if (it.isNaN()) {
                return
            }
        }
        val productPriceAmount = amount?.toBigDecimal() as BigDecimal
        val productGtin = productItem.optString(ProductItemParameters.PRODUCT_GTIN)
        val productMpn = productItem.optString(ProductItemParameters.PRODUCT_MPN)
        val productBrand = productItem.optString(ProductItemParameters.PRODUCT_BRAND)
        val productPriceCurrency =
            getCurrency(productItem.optString(ProductItemParameters.PRODUCT_PRICE_CURRENCY))
        val productParameters = productItem.optJSONObject(ProductItemParameters.PRODUCT_PARAMETERS)
        val bundle = Bundle()
        mapJsonToBundle(productParameters, bundle)

        productAvailability?.let { productAvailability ->
            val (availability) = guardLet(productAvailabilityFrom(productAvailability)) { return }
            productCondition?.let { productCondition ->
                val (condition) = guardLet(productConditionFrom(productCondition)) { return }

                if (productId.isEmpty()
                    || productDescription.isEmpty()
                    || productImageLink.isEmpty()
                    || productLink.isEmpty()
                    || productTitle.isEmpty()
                    || productGtin.isEmpty()
                    || productMpn.isEmpty()
                    || productBrand.isEmpty()
                ) {
                    return
                }
                tracker.logProductItem(
                    productId,
                    availability,
                    condition,
                    productDescription,
                    productImageLink,
                    productLink,
                    productTitle,
                    productPriceAmount,
                    productPriceCurrency,
                    productGtin,
                    productMpn,
                    productBrand,
                    bundle
                )
            }
        }
    }

    fun enableAutoLogAppEvents(enable: Boolean) {
        tracker.enableAutoLogAppEvents(enable)
    }

    fun splitCommands(payload: JSONObject): Array<String> {
        val command = payload.optString(Commands.COMMAND_KEY)
        return command.split(Commands.SEPARATOR.toRegex())
            .dropLastWhile {
                it.isEmpty()
            }
            .map {
                it.trim().toLowerCase()
            }
            .toTypedArray()
    }

    private fun productAvailabilityFrom(availability: Int): AppEventsLogger.ProductAvailability? {
        return when (availability) {
            Product.IN_STOCK -> AppEventsLogger.ProductAvailability.IN_STOCK
            Product.OUT_OF_STOCK -> AppEventsLogger.ProductAvailability.OUT_OF_STOCK
            Product.PREORDER -> AppEventsLogger.ProductAvailability.PREORDER
            Product.AVAILABLE_FOR_ORDER -> AppEventsLogger.ProductAvailability.AVALIABLE_FOR_ORDER
            Product.DISCONTINUED -> AppEventsLogger.ProductAvailability.DISCONTINUED
            else -> null
        }
    }

    private fun productConditionFrom(condition: Int): AppEventsLogger.ProductCondition? {
        return when (condition) {
            Product.NEW -> AppEventsLogger.ProductCondition.NEW
            Product.REFURBISHED -> AppEventsLogger.ProductCondition.REFURBISHED
            Product.USED -> AppEventsLogger.ProductCondition.USED
            else -> null
        }
    }

    private fun getCurrency(currencyString: String? = "USD"): Currency? {
        return try {
            Currency.getInstance(currencyString)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.toString())
            null
        }
    }

}

inline fun <T : Any> guardLet(vararg elements: T?, closure: () -> Nothing): List<T> {
    return if (elements.all { it != null }) {
        elements.filterNotNull()
    } else {
        closure()
    }
}

inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}