package com.tealium.remotecommands.facebook

import com.tealium.remotecommands.facebook.Event.VALUE_TO_SUM
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.facebook.appevents.AppEventsLogger
import com.tealium.remotecommands.RemoteCommand
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*
import kotlin.jvm.Throws

class FacebookRemoteCommand
/**
 * Constructs a RemoteCommand that integrates with the Facebook App Events SDK to allow Facebook API calls to be implemented through Tealium.
 */
@JvmOverloads constructor(
    application: Application? = null,
    commandId: String = DEFAULT_COMMAND_ID,
    description: String = DEFAULT_COMMAND_DESCRIPTION,
    facebookApplicationId: String? = null,
    debugEnabled: Boolean? = null
) : RemoteCommand(commandId, description, BuildConfig.TEALIUM_FACEBOOK_VERSION) {

    private lateinit var facebookInstance: FacebookCommand
    private var application: Application? = null

    init {
        application?.let { app ->
            this.application = app
            facebookApplicationId?.let {
                facebookInstance = FacebookInstance(app, it, debugEnabled)
            }
        }
    }

    companion object {
        const val DEFAULT_COMMAND_ID = "facebook"
        const val DEFAULT_COMMAND_DESCRIPTION = "Tealium-Facebook Remote Command"
        const val REQUIRED_KEY = "key does not exist in the payload."
        const val TAG = "FacebookRemoteCommand"

        /**
         * Maps a JSON object to a Bundle. Does not support nested objects.
         *
         * @param json - JSON object used to set key/value pairs on the Bundle
         * @param bundle - bundle to set key/value pairs that is used in tracking events
         */
        fun mapJsonToBundle(json: JSONObject?, bundle: Bundle) {
            json?.let {
                it.keys().forEach { key ->
                    when (val value = json[key]) {
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
        private fun <T> mapArrayToBundle(key: String, value: Array<T>, bundle: Bundle) {
            if (value.isNotEmpty()) {
                when (value.first()) {
                    is Double -> {
                        val doubleArray = value.filterIsInstance<Double>().toDoubleArray()
                        bundle.putDoubleArray(key, doubleArray)
                    }
                    is Boolean -> {
                        val booleanArray = value.filterIsInstance<Boolean>().toBooleanArray()
                        bundle.putBooleanArray(key, booleanArray)
                    }
                    is Int -> {
                        val intArray = value.filterIsInstance<Int>().toIntArray()
                        bundle.putIntArray(key, intArray)
                    }
                    else -> {
                        val stringArray = value.map { it.toString() }
                        bundle.putStringArrayList(key, ArrayList(stringArray))
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
                    val debugEnabled = if (payload.optString(Initialize.DEBUG_ENABLED).isNotBlank()) payload.optBoolean(
                        Initialize.DEBUG_ENABLED
                    ) else null
                    application?.let { appContext ->
                        applicationId?.let { appId ->
                            facebookInstance =
                                FacebookInstance(
                                    appContext,
                                    appId,
                                    debugEnabled
                                )
                        } ?: run {
                            Log.e(TAG, "${Initialize.APPLICATION_ID} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.LOG_PURCHASE -> {
                    val purchase = payload.optJSONObject(Purchase.PURCHASE)
                    purchase?.let {
                        val purchaseParameters: JSONObject? = payload.optJSONObject(Purchase.PURCHASE_PARAMETERS)
                        it.putOpt(Purchase.PURCHASE_PARAMETERS, purchaseParameters)
                        logPurchase(it)
                    } ?: run {
                        Log.e(TAG, "${Purchase.PURCHASE} $REQUIRED_KEY")
                    }
                }
                Commands.SET_USER -> {
                    val userData = payload.optJSONObject(User.USER_DATA)
                    userData?.let {
                        setUser(it)
                    } ?: run {
                        Log.e(TAG, "${User.USER_DATA} $REQUIRED_KEY")
                    }
                }
                Commands.SET_USER_ID -> {
                    val userId = payload.optString(User.USER_ID)
                    userId.let {
                        if (it.isNotEmpty()) {
                            facebookInstance.setUserID(it)
                        } else {
                            Log.e(TAG, "${User.USER_ID} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.CLEAR_USER_DATA -> {
                    facebookInstance.clearUserData()
                }
                Commands.CLEAR_USER_ID -> {
                    facebookInstance.clearUserID()
                }
                Commands.LOG_PRODUCT_ITEM -> {
                    val productItem = payload.optJSONObject(Product.PRODUCT_ITEM)
                    productItem?.let {
                        val productParameters: JSONObject? = payload.optJSONObject(Product.PRODUCT_PARAMETERS)
                        it.putOpt(Product.PRODUCT_PARAMETERS, productParameters)
                        logProductItem(it)
                    } ?: run {
                        Log.e(TAG, "${Product.PRODUCT_ITEM} $REQUIRED_KEY")
                    }
                }
                Commands.SET_FLUSH_BEHAVIOR -> {
                    val flushValue = payload.optString(Flush.FLUSH_BEHAVIOR)
                    if (flushValue.isNotEmpty()) {
                        facebookInstance.setFlushBehavior(flushValue)
                    } else {
                        Log.e(TAG, "${Flush.FLUSH_BEHAVIOR} $REQUIRED_KEY")
                    }
                }
                Commands.FLUSH -> {
                    facebookInstance.flush()
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
                    facebookInstance.enableAutoLogAppEvents(autoLogEnable)
                }
                Commands.SET_AUTO_INITIALIZED -> {
                    val autoInitEnable = payload.optBoolean(AutoInit.AUTO_INITIALIZED)
                    facebookInstance.enableAutoInitialize(autoInitEnable)
                }
                Commands.SET_ADVERTISER_ID_COLLECTION -> {
                    val advertiserIDEnable = payload.optBoolean(Advertiser.ADVERTISER_COLLECTION)
                    facebookInstance.enableAdvertiserIDCollection(advertiserIDEnable)
                }
                Commands.ACHIEVED_LEVEL -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val levelAchieved = it.optString(Event.LEVEL)
                        if (levelAchieved.isNotBlank()) {
                            logEvent(Commands.ACHIEVED_LEVEL, 0.0, it)
                        } else {
                            Log.e(TAG, "${Event.LEVEL} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.UNLOCKED_ACHIEVEMENT -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val achievement = it.optString(Event.DESCRIPTION)
                        if (achievement.isNotBlank()) {
                            logEvent(Commands.UNLOCKED_ACHIEVEMENT, 0.0, it)
                        } else {
                            Log.e(TAG, "${Event.DESCRIPTION} $REQUIRED_KEY")
                        }
                    }
                }
                Commands.COMPLETED_REGISTRATION -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val registrationMethod = it.optString(Event.REGISTRATION_METHOD)
                        if (registrationMethod.isNotBlank()) {
                            logEvent(Commands.COMPLETED_REGISTRATION, 0.0, it)
                        } else {
                            Log.e(TAG, "${Event.REGISTRATION_METHOD} $REQUIRED_KEY")
                        }
                    }

                }
                Commands.COMPLETED_TUTORIAL -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val contentId = it.optString(Event.CONTENT_ID)
                        if (contentId.isNotBlank()) {
                            logEvent(Commands.COMPLETED_TUTORIAL, 0.0, it)
                        } else {
                            Log.e(
                                TAG, "${Event.CONTENT_ID} $REQUIRED_KEY"
                            )
                        }
                    }
                }
                Commands.INITIATED_CHECKOUT -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val valueToSum = it.optDouble(VALUE_TO_SUM, 0.0)
                        if (valueToSum > 0.0) {
                            logEvent(Commands.INITIATED_CHECKOUT, valueToSum, it)
                        } else {
                            Log.e(TAG, "$VALUE_TO_SUM $REQUIRED_KEY")
                        }
                    }
                }
                Commands.SEARCHED -> {
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    eventParameters?.let {
                        val contentType = it.optString(Event.SEARCH_STRING)
                        if (contentType.isNotBlank()) {
                            logEvent(Commands.SEARCHED, 0.0, it)
                        } else {
                            Log.e(TAG, "${Event.SEARCH_STRING} $REQUIRED_KEY")
                        }
                    }
                }
                else -> {
                    val event = standardEvent(command)
                    val valueToSum = payload.optDouble(VALUE_TO_SUM, 0.0)
                    val eventParameters = payload.optJSONObject(Event.EVENT_PARAMETERS)
                    logEvent(event, valueToSum, eventParameters)
                }
            }
        }
    }

    /**
     * Returns the Facebook standard event name if it exists.
     *
     * @param commandName - name of the Tealium command name.
     */
    fun standardEvent(commandName: String): String {
        return StandardEvents.standardEventNames[commandName] ?: commandName
    }

    private fun logEvent(command: String, valueToSum: Double, eventParameters: JSONObject? = null) {
        val bundle = Bundle()
        if (valueToSum > 0 && eventParameters != null) {
            valueToSum.let { sumValue ->
                mapJsonToBundle(eventParameters, bundle)
                facebookInstance.logEvent(command, sumValue, bundle)
            }
        } else if (valueToSum > 0) {
            valueToSum.let { sumValue ->
                facebookInstance.logEvent(command, sumValue)
            }
        } else if (valueToSum == 0.0 && eventParameters != null) {
            mapJsonToBundle(eventParameters, bundle)
            facebookInstance.logEvent(command, bundle)
        } else {
            facebookInstance.logEvent(command)
        }
    }

    private fun logPurchase(purchase: JSONObject) {
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
            facebookInstance.logPurchase(purchaseAmount, currency, bundle)
        } ?: run {
            facebookInstance.logPurchase(purchaseAmount, currency)
        }
    }

    private fun setUser(user: JSONObject) {
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

        facebookInstance.setUserData(
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

    private fun logProductItem(productItem: JSONObject) {
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

        productAvailability.let { availabilityValue ->
            val (availability) = guardLet(productAvailabilityFrom(availabilityValue)) { return }
            productCondition.let { conditionValue ->
                val (condition) = guardLet(productConditionFrom(conditionValue)) { return }

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
                facebookInstance.logProductItem(
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

    fun splitCommands(payload: JSONObject): Array<String> {
        val command = payload.optString(Commands.COMMAND_KEY)
        return command.split(Commands.SEPARATOR.toRegex())
            .dropLastWhile {
                it.isEmpty()
            }
            .map {
                it.trim().lowercase()
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
