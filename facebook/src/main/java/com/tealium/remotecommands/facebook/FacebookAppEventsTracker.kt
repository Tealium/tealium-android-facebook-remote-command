package com.tealium.remotecommands.facebook

import Flush
import android.app.Application
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import java.math.BigDecimal
import java.util.*

class FacebookAppEventsTracker(
    val application: Application,
    applicationId: String?,
    accessToken: String?,
    userId: String?
) : FacebookAppEventsTrackable {

    lateinit var logger: AppEventsLogger

    init {
        if (applicationId != null && accessToken != null && userId != null) {
            val token = AccessToken(
                accessToken,
                applicationId,
                userId,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            initialize(applicationId, token)
        } else {
            initialize(applicationId, null)
        }
    }

    override fun initialize(applicationId: String?, accessToken: AccessToken?) {
        if (applicationId != null && accessToken != null) {
            logger = AppEventsLogger.newLogger(application, applicationId, accessToken)
        } else if (applicationId != null && accessToken == null) {
            logger = AppEventsLogger.newLogger(application, applicationId)
        } else if (applicationId == null && accessToken != null) {
            logger = AppEventsLogger.newLogger(application, accessToken)
        } else {
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

    override fun setUserID(userID: String) {
        AppEventsLogger.setUserID(userID)
    }

    override fun clearUserData() {
        AppEventsLogger.clearUserData()
    }

    override fun clearUserID() {
        AppEventsLogger.clearUserID()
    }

    override fun updateUserProperties(bundle: Bundle) {
        AppEventsLogger.updateUserProperties(bundle, null)
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