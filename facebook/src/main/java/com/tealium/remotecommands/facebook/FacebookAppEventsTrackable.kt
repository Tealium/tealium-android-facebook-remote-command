package com.tealium.remotecommands.facebook

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.appevents.AppEventsLogger
import java.math.BigDecimal
import java.util.*

interface FacebookAppEventsTrackable {

    fun initialize(applicationId: String?, accessToken: AccessToken?)

    fun logEvent(eventName: String)
    fun logEvent(eventName: String, valueToSum: Double)
    fun logEvent(eventName: String, parameters: Bundle)
    fun logEvent(eventName: String, valueToSum: Double, parameters: Bundle)


    fun logPurchase(purchaseAmount: BigDecimal, currency: Currency)
    fun logPurchase(purchaseAmount: BigDecimal, currency: Currency, parameters: Bundle)

    fun setUserData(
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
    )

    fun setUserID(userId: String)

    fun clearUserData()

    fun clearUserID()

    fun updateUserProperties(bundle: Bundle)

    fun logProductItem(
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
    )

    fun setFlushBehavior(flushValue: String)

    fun flush()

    fun setPushNotificationsRegistrationId(registrationId: String)

    fun enableAutoLogAppEvents(enable: Boolean)

    fun enableAutoInitialize(enable: Boolean)

    fun enableAdvertiserIDCollection(enable: Boolean)
}