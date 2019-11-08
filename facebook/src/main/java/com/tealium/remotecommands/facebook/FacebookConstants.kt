@file:JvmName("FacebookConstants")

import com.facebook.appevents.AppEventsConstants

object Commands {
    const val COMMAND_KEY = "command_name"
    const val SEPARATOR = ","

    const val INITIALIZE = "initialize"
    const val LOG_PURCHASE = "logpurchase"
    const val SET_USER = "setuser"
    const val SET_USER_ID = "setuserid"
    const val CLEAR_USER_DATA = "clearuserdata"
    const val CLEAR_USER_ID = "clearuserid"
    const val UPDATE_USER_VALUE = "updateuservalue"
    const val LOG_PRODUCT_ITEM = "logproductitem"
    const val SET_FLUSH_BEHAVIOR = "setflushbehavior"
    const val FLUSH = "flush"
    const val SET_PUSH_REGISTRATION_ID = "setpushregistrationid"
    const val SET_AUTO_LOG_APP_EVENTS = "setautologappevents"
    const val SET_AUTO_INITIALIZED = "setautoinitialized"
    const val SET_ADVERTISER_ID_COLLECTION = "setadvertiseridcollection"
    const val LOG_PUSH_NOTIFICATION_OPEN = "logpushnotificationopen"
    const val EVENT_NAME_ACTIVATED_APP = AppEventsConstants.EVENT_NAME_ACTIVATED_APP
    const val EVENT_NAME_DEACTIVATED_APP = AppEventsConstants.EVENT_NAME_DEACTIVATED_APP
    const val EVENT_NAME_SESSION_INTERRUPTIONS = AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS
    const val EVENT_NAME_TIME_BETWEEN_SESSIONS = AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS
    const val EVENT_NAME_COMPLETED_REGISTRATION =
        AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION
    const val EVENT_NAME_VIEWED_CONTENT = AppEventsConstants.EVENT_NAME_VIEWED_CONTENT
    const val EVENT_NAME_SEARCHED = AppEventsConstants.EVENT_NAME_SEARCHED
    const val EVENT_NAME_RATED = AppEventsConstants.EVENT_NAME_RATED
    const val EVENT_NAME_COMPLETED_TUTORIAL = AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL
    const val EVENT_NAME_PUSH_TOKEN_OBTAINED = AppEventsConstants.EVENT_NAME_PUSH_TOKEN_OBTAINED
    const val EVENT_NAME_ADDED_TO_CART = AppEventsConstants.EVENT_NAME_ADDED_TO_CART
    const val EVENT_NAME_ADDED_TO_WISHLIST = AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST
    const val EVENT_NAME_INITIATED_CHECKOUT = AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT
    const val EVENT_NAME_ADDED_PAYMENT_INFO = AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO
    const val EVENT_NAME_ACHIEVED_LEVEL = AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL
    const val EVENT_NAME_UNLOCKED_ACHIEVEMENT = AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT
    const val EVENT_NAME_SPENT_CREDITS = AppEventsConstants.EVENT_NAME_SPENT_CREDITS
    const val EVENT_NAME_CONTACT = AppEventsConstants.EVENT_NAME_CONTACT
    const val EVENT_NAME_CUSTOMIZE_PRODUCT = AppEventsConstants.EVENT_NAME_CUSTOMIZE_PRODUCT
    const val EVENT_NAME_DONATE = AppEventsConstants.EVENT_NAME_DONATE
    const val EVENT_NAME_FIND_LOCATION = AppEventsConstants.EVENT_NAME_FIND_LOCATION
    const val EVENT_NAME_SCHEDULE = AppEventsConstants.EVENT_NAME_SCHEDULE
    const val EVENT_NAME_START_TRIAL = AppEventsConstants.EVENT_NAME_START_TRIAL
    const val EVENT_NAME_SUBMIT_APPLICATION = AppEventsConstants.EVENT_NAME_SUBMIT_APPLICATION
    const val EVENT_NAME_SUBSCRIBE = AppEventsConstants.EVENT_NAME_SUBSCRIBE
    const val EVENT_NAME_AD_IMPRESSION = AppEventsConstants.EVENT_NAME_AD_IMPRESSION
    const val EVENT_NAME_AD_CLICK = AppEventsConstants.EVENT_NAME_AD_CLICK
    const val EVENT_NAME_LIVE_STREAMING_START = AppEventsConstants.EVENT_NAME_LIVE_STREAMING_START
    const val EVENT_NAME_LIVE_STREAMING_STOP = AppEventsConstants.EVENT_NAME_LIVE_STREAMING_STOP
    const val EVENT_NAME_LIVE_STREAMING_PAUSE = AppEventsConstants.EVENT_NAME_LIVE_STREAMING_PAUSE
    const val EVENT_NAME_LIVE_STREAMING_RESUME = AppEventsConstants.EVENT_NAME_LIVE_STREAMING_RESUME
    const val EVENT_NAME_LIVE_STREAMING_ERROR = AppEventsConstants.EVENT_NAME_LIVE_STREAMING_ERROR
    const val EVENT_NAME_LIVE_STREAMING_UPDATE_STATUS =
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_UPDATE_STATUS
    const val EVENT_NAME_PRODUCT_CATALOG_UPDATE =
        AppEventsConstants.EVENT_NAME_PRODUCT_CATALOG_UPDATE
}

object StandardEvents {
    val standardEventNames = arrayOf(
        AppEventsConstants.EVENT_NAME_ACTIVATED_APP,
        AppEventsConstants.EVENT_NAME_DEACTIVATED_APP,
        AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS,
        AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS,
        AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION,
        AppEventsConstants.EVENT_NAME_VIEWED_CONTENT,
        AppEventsConstants.EVENT_NAME_SEARCHED,
        AppEventsConstants.EVENT_NAME_RATED,
        AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL,
        AppEventsConstants.EVENT_NAME_PUSH_TOKEN_OBTAINED,
        AppEventsConstants.EVENT_NAME_ADDED_TO_CART,
        AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST,
        AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT,
        AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO,
        AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL,
        AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT,
        AppEventsConstants.EVENT_NAME_SPENT_CREDITS,
        AppEventsConstants.EVENT_NAME_CONTACT,
        AppEventsConstants.EVENT_NAME_CUSTOMIZE_PRODUCT,
        AppEventsConstants.EVENT_NAME_DONATE,
        AppEventsConstants.EVENT_NAME_FIND_LOCATION,
        AppEventsConstants.EVENT_NAME_SCHEDULE,
        AppEventsConstants.EVENT_NAME_START_TRIAL,
        AppEventsConstants.EVENT_NAME_SUBMIT_APPLICATION,
        AppEventsConstants.EVENT_NAME_SUBSCRIBE,
        AppEventsConstants.EVENT_NAME_AD_IMPRESSION,
        AppEventsConstants.EVENT_NAME_AD_CLICK,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_START,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_STOP,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_PAUSE,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_RESUME,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_ERROR,
        AppEventsConstants.EVENT_NAME_LIVE_STREAMING_UPDATE_STATUS,
        AppEventsConstants.EVENT_NAME_PRODUCT_CATALOG_UPDATE
    )
}

object Event {
    const val EVENT = "event"
    const val VALUE_TO_SUM = "fb_value_to_sum"

    const val EVENT_PARAM_LIVE_STREAMING_PREV_STATUS =
        AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_PREV_STATUS
    const val EVENT_PARAM_LIVE_STREAMING_STATUS =
        AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_STATUS
    const val EVENT_PARAM_LIVE_STREAMING_ERROR = AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_ERROR
    const val EVENT_PARAM_CURRENCY = AppEventsConstants.EVENT_PARAM_CURRENCY
    const val EVENT_PARAM_REGISTRATION_METHOD = AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD
    const val EVENT_PARAM_CONTENT_TYPE = AppEventsConstants.EVENT_PARAM_CONTENT_TYPE
    const val EVENT_PARAM_CONTENT = AppEventsConstants.EVENT_PARAM_CONTENT
    const val EVENT_PARAM_CONTENT_ID = AppEventsConstants.EVENT_PARAM_CONTENT_ID
    const val EVENT_PARAM_SEARCH_STRING = AppEventsConstants.EVENT_PARAM_SEARCH_STRING
    const val EVENT_PARAM_SUCCESS = AppEventsConstants.EVENT_PARAM_SUCCESS
    const val EVENT_PARAM_MAX_RATING_VALUE = AppEventsConstants.EVENT_PARAM_MAX_RATING_VALUE
    const val EVENT_PARAM_PAYMENT_INFO_AVAILABLE =
        AppEventsConstants.EVENT_PARAM_PAYMENT_INFO_AVAILABLE
    const val EVENT_PARAM_NUM_ITEMS = AppEventsConstants.EVENT_PARAM_NUM_ITEMS
    const val EVENT_PARAM_LEVEL = AppEventsConstants.EVENT_PARAM_LEVEL
    const val EVENT_PARAM_DESCRIPTION = AppEventsConstants.EVENT_PARAM_DESCRIPTION
    const val EVENT_PARAM_SOURCE_APPLICATION = AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION
    const val EVENT_PARAM_VALUE_YES = AppEventsConstants.EVENT_PARAM_VALUE_YES
    const val EVENT_PARAM_VALUE_NO = AppEventsConstants.EVENT_PARAM_VALUE_NO
    const val EVENT_PARAM_AD_TYPE = AppEventsConstants.EVENT_PARAM_AD_TYPE
    const val EVENT_PARAM_ORDER_ID = AppEventsConstants.EVENT_PARAM_ORDER_ID
    const val EVENT_PARAM_VALUE_TO_SUM = AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM
    const val EVENT_PARAM_PRODUCT_CUSTOM_LABEL_0 =
        AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_0
    const val EVENT_PARAM_PRODUCT_CUSTOM_LABEL_1 =
        AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_1
    const val EVENT_PARAM_PRODUCT_CUSTOM_LABEL_2 =
        AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_2
    const val EVENT_PARAM_PRODUCT_CUSTOM_LABEL_3 =
        AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_3
    const val EVENT_PARAM_PRODUCT_CUSTOM_LABEL_4 =
        AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_4
    const val EVENT_PARAM_PRODUCT_APPLINK_IOS_URL =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_URL
    const val EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_STORE_ID =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_STORE_ID
    const val EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_NAME =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_NAME
    const val EVENT_PARAM_PRODUCT_APPLINK_IPHONE_URL =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_URL
    const val EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_STORE_ID =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_STORE_ID
    const val EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_NAME =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_NAME
    const val EVENT_PARAM_PRODUCT_APPLINK_IPAD_URL =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_URL
    const val EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_STORE_ID =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_STORE_ID
    const val EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_NAME =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_NAME
    const val EVENT_PARAM_PRODUCT_APPLINK_ANDROID_URL =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_URL
    const val EVENT_PARAM_PRODUCT_APPLINK_ANDROID_PACKAGE =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_PACKAGE
    const val EVENT_PARAM_PRODUCT_APPLINK_ANDROID_APP_NAME =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_APP_NAME
    const val EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_URL =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_URL
    const val EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_ID =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_ID
    const val EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_NAME =
        AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_NAME
}

object ProductItemParameters {
    const val PRODUCT_ID = "fb_product_item_id"
    const val PRODUCT_AVAILABILITY = "fb_product_availability"
    const val PRODUCT_CONDITION = "fb_product_condition"
    const val PRODUCT_DESCRIPTION = "fb_product_description"
    const val PRODUCT_IMAGE_LINK = "fb_product_image_link"
    const val PRODUCT_LINK = "fb_product_link"
    const val PRODUCT_TITLE = "fb_product_title"
    const val PRODUCT_GTIN = "fb_product_gtin"
    const val PRODUCT_MPN = "fb_product_mpn"
    const val PRODUCT_BRAND = "fb_product_brand"
    const val PRODUCT_PRICE_AMOUNT = "fb_product_price_amount"
    const val PRODUCT_PRICE_CURRENCY = "fb_product_price_currency"
    const val PRODUCT_PARAMETERS = "fb_product_parameters"
}


object User {
    const val USER_VALUE = "fb_user_value"
    const val USER_KEY = "fb_user_key"
    const val USER_DATA = "user"

    const val EMAIL = "em"
    const val FIRST_NAME = "fn"
    const val LAST_NAME = "ln"
    const val PHONE = "ph"
    const val DATE_OF_BIRTH = "db"
    const val GENDER = "ge"
    const val CITY = "ct"
    const val STATE = "st"
    const val ZIP = "zp"
    const val COUNTRY = "country"

    const val USER_ID = "user_id"
}

object Product {
    const val PRODUCT_PRICE_AMOUNT = "fb_product_price_amount"
    const val PRODUCT_PRICE_CURRENCY = "fb_product_price_currency"
    const val PRODUCT_PARAMETERS = "fb_product_parameters"
    const val PRODUCT_ITEM = "product_item"

    const val IN_STOCK = 0
    const val OUT_OF_STOCK = 1
    const val PREORDER = 2
    const val AVAILABLE_FOR_ORDER = 3
    const val DISCONTINUED = 4

    const val NEW = 0
    const val REFURBISHED = 1
    const val USED = 2
}

enum class ProductAvailability(val value: Int) {
    IN_STOCK(0),
    OUT_OF_STOCK(1),
    PREORDER(2),
    AVAILABLE_FOR_ORDER(3),
    DISCONTINUED(4),
}

enum class ProductCondition(val value: Int) {
    NEW(0),
    REFURBISHED(1),
    USED(2)
}

object Purchase {
    const val PURCHASE_AMOUNT = "fb_purchase_amount"
    const val PURCHASE_CURRENCY = "fb_purchase_currency"
    const val PURCHASE_PARAMETERS = "fb_purchase_parameters"
    const val PURCHASE = "purchase"
}

object Push {
    const val PUSH_ACTION = "fb_push_action"
    const val PUSH_PAYLOAD = "fb_push_payload"
    const val PUSH = "push"
    const val REGISTRATION_ID = "registration_id"
}

object Flush {
    const val FLUSH_BEHAVIOR = "flush_behavior"
    const val AUTO = "auto"
    const val EXPLICIT_ONLY = "explicit_only"
}

object AutoLog {
    const val AUTO_LOG = "auto_log"
}

object AutoInit {
    const val AUTO_INITIALIZED = "auto_initialized"
}

object Advertiser {
    const val ADVERTISER_COLLECTION = "advertiser_collection"
}

object Initialize {
    const val APPLICATION_ID = "applicationid"
}