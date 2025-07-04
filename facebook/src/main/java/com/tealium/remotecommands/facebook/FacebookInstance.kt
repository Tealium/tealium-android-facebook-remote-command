package com.tealium.remotecommands.facebook

import android.app.Application
import android.os.Bundle
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import java.math.BigDecimal
import java.util.*

class FacebookInstance(
    private val application: Application,
    applicationId: String? = null,
    clientToken: String? = null,
    debugEnabled: Boolean? = null
) : FacebookCommand {

    private lateinit var logger: AppEventsLogger

    init {
        initialize(applicationId, clientToken, debugEnabled)
    }

    override fun initialize(applicationId: String?, clientToken: String?, debugEnabled: Boolean?) {
        applicationId?.let { appId ->
            FacebookSdk.setApplicationId(applicationId)
            clientToken?.let { token ->
                FacebookSdk.setClientToken(token)
            }
            FacebookSdk.fullyInitialize()
            debugEnabled?.let {
                FacebookSdk.setIsDebugEnabled(it)
                FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)
            }
            AppEventsLogger.activateApp(application)
            AppEventsLogger.initializeLib(application, appId)
            logger = AppEventsLogger.newLogger(application, applicationId)
        } ?: run {
            logger = AppEventsLogger.newLogger(application)
        }
    }

    override fun logEvent(eventName: String) {
        logger.logEvent(eventName)
    }

    override fun logEvent(eventName: String, valueToSum: Double) {
        logger.logEvent(eventName, valueToSum)
    }

    override fun logEvent(eventName: String, parameters: Bundle) {
        logger.logEvent(eventName, parameters)
    }

    override fun logEvent(eventName: String, valueToSum: Double, parameters: Bundle) {
        logger.logEvent(eventName, valueToSum, parameters)
    }

    override fun logPurchase(purchaseAmount: BigDecimal, currency: Currency) {
        logger.logPurchase(purchaseAmount, currency)
    }

    override fun logPurchase(purchaseAmount: BigDecimal, currency: Currency, parameters: Bundle) {
        logger.logPurchase(purchaseAmount, currency, parameters)
    }

    override fun setUserData(
        email: String,
        firstName: String,
        lastName: String,
        phone: String,
        dateOfBirth: String,
        gender: String,
        city: String,
        state: String,
        zip: String,
        country: String
    ) {
        AppEventsLogger.setUserData(
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

    override fun setUserID(userId: String) {
        AppEventsLogger.setUserID(userId)
    }

    override fun clearUserData() {
        AppEventsLogger.clearUserData()
    }

    override fun clearUserID() {
        AppEventsLogger.clearUserID()
    }

    override fun logProductItem(
        itemID: String,
        availability: AppEventsLogger.ProductAvailability?,
        condition: AppEventsLogger.ProductCondition?,
        description: String,
        imageLink: String,
        link: String,
        title: String,
        priceAmount: BigDecimal,
        currency: Currency?,
        gtin: String,
        mpn: String,
        brand: String,
        parameters: Bundle
    ) {
        logger.logProductItem(
            itemID,
            availability,
            condition,
            description,
            imageLink,
            link,
            title,
            priceAmount,
            currency,
            gtin,
            mpn,
            brand,
            parameters
        )
    }

    override fun setFlushBehavior(flushValue: String) {
        when (flushValue) {
            Flush.AUTO -> AppEventsLogger.setFlushBehavior(AppEventsLogger.FlushBehavior.AUTO)
            Flush.EXPLICIT_ONLY -> AppEventsLogger.setFlushBehavior(AppEventsLogger.FlushBehavior.EXPLICIT_ONLY)
        }
    }

    override fun flush() {
        logger.flush()
    }

    override fun setPushNotificationsRegistrationId(registrationId: String) {
        AppEventsLogger.setPushNotificationsRegistrationId(registrationId)
    }

    override fun enableAutoLogAppEvents(enable: Boolean) {
        FacebookSdk.setAutoLogAppEventsEnabled(enable)
    }

    override fun enableAutoInitialize(enable: Boolean) {
        FacebookSdk.setAutoInitEnabled(enable)
        if (enable) {
            FacebookSdk.fullyInitialize()
        }
    }

    override fun enableAdvertiserIDCollection(enable: Boolean) {
        FacebookSdk.setAdvertiserIDCollectionEnabled(enable)
    }
}