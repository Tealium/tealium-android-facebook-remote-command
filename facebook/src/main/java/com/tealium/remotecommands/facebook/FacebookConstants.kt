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
    const val SET_AUTO_LOG_APP_EVENTS = "setautologappeventsenabled"
    const val SET_AUTO_INITIALIZED = "setautoinitenabled"
    const val SET_ADVERTISER_ID_COLLECTION = "enableadvertiseridcollection"
    const val LOG_PUSH_NOTIFICATION_OPEN = "logpushnotificationopen"
    const val ACTIVATED_APP = "activatedapp"
    const val DEACTIVATED_APP = "deactivatedapp"
    const val SESSION_INTERRUPTIONS = "sessioninterruptions"
    const val TIME_BETWEEN_SESSIONS = "timebetweensessions"
    const val COMPLETED_REGISTRATION = "completedregistration"
    const val VIEWED_CONTENT = "viewedcontent"
    const val SEARCHED = "searched"
    const val RATED = "rated"
    const val COMPLETED_TUTORIAL = "completedtutorial"
    const val PUSH_TOKEN_OBTAINED = "pushtokenobtained"
    const val ADDED_TO_CART = "addedtocart"
    const val ADDED_TO_WISHLIST = "addedtowishlist"
    const val INITIATED_CHECKOUT = "initiatedcheckout"
    const val ADDED_PAYMENT_INFO = "addedpaymentinfo"
    const val ACHIEVED_LEVEL = "achievedlevel"
    const val UNLOCKED_ACHIEVEMENT = "unlockedachievement"
    const val SPENT_CREDITS = "spentcredits"
    const val CONTACT = "contact"
    const val CUSTOMIZE_PRODUCT = "customizeproduct"
    const val DONATE = "donate"
    const val FIND_LOCATION = "findlocation"
    const val SCHEDULE = "schedule"
    const val START_TRIAL = "starttrial"
    const val SUBMIT_APPLICATION = "submitapplication"
    const val SUBSCRIBE = "subscribe"
    const val AD_IMPRESSION = "adimpression"
    const val AD_CLICK = "adclick"
    const val LIVE_STREAMING_START = "livestreamingstart"
    const val LIVE_STREAMING_STOP = "livestreamingstop"
    const val LIVE_STREAMING_PAUSE = "livestreamingpause"
    const val LIVE_STREAMING_RESUME = "livestreamingresume"
    const val LIVE_STREAMING_ERROR = "livestreamingerror"
    const val LIVE_STREAMING_UPDATE_STATUS = "livestreamingupdatestatus"
    const val PRODUCT_CATALOG_UPDATE = "productcatalogupdate"
}

object StandardEvents {
    val standardEventNames = mapOf(
        "activatedapp" to AppEventsConstants.EVENT_NAME_ACTIVATED_APP,
        "deactivatedapp" to AppEventsConstants.EVENT_NAME_DEACTIVATED_APP,
        "sessioninterruptions" to AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS,
        "timebetweensessions" to AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS,
        "completedregistration" to AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION,
        "viewedcontent" to AppEventsConstants.EVENT_NAME_VIEWED_CONTENT,
        "searched" to AppEventsConstants.EVENT_NAME_SEARCHED,
        "rated" to AppEventsConstants.EVENT_NAME_RATED,
        "completedtutorial" to AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL,
        "pushtokenobtained" to AppEventsConstants.EVENT_NAME_PUSH_TOKEN_OBTAINED,
        "addedtocart" to AppEventsConstants.EVENT_NAME_ADDED_TO_CART,
        "addedtowishlist" to AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST,
        "initiatedcheckout" to AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT,
        "addedpaymentinfo" to AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO,
        "achievedlevel" to AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL,
        "unlockedachievement" to AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT,
        "spentcredits" to AppEventsConstants.EVENT_NAME_SPENT_CREDITS,
        "contact" to AppEventsConstants.EVENT_NAME_CONTACT,
        "customizeproduct" to AppEventsConstants.EVENT_NAME_CUSTOMIZE_PRODUCT,
        "donate" to AppEventsConstants.EVENT_NAME_DONATE,
        "findlocation" to AppEventsConstants.EVENT_NAME_FIND_LOCATION,
        "schedule" to AppEventsConstants.EVENT_NAME_SCHEDULE,
        "starttrial" to AppEventsConstants.EVENT_NAME_START_TRIAL,
        "submitapplication" to AppEventsConstants.EVENT_NAME_SUBMIT_APPLICATION,
        "subscribe" to AppEventsConstants.EVENT_NAME_SUBSCRIBE,
        "adimpression" to AppEventsConstants.EVENT_NAME_AD_IMPRESSION,
        "adclick" to AppEventsConstants.EVENT_NAME_AD_CLICK,
        "livestreamingstart" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_START,
        "livestreamingstop" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_STOP,
        "livestreamingpause" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_PAUSE,
        "livestreamingresume" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_RESUME,
        "livestreamingerror" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_ERROR,
        "livestreamingupdatestatus" to AppEventsConstants.EVENT_NAME_LIVE_STREAMING_UPDATE_STATUS,
        "productcatalogupdate" to AppEventsConstants.EVENT_NAME_PRODUCT_CATALOG_UPDATE
    )
}

object Event {
    const val EVENT_PARAMETERS = "event"
    const val VALUE_TO_SUM = "_valueToSum"

    const val LIVE_STREAMING_PREV_STATUS = AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_PREV_STATUS
    const val LIVE_STREAMING_STATUS = AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_STATUS
    const val LIVE_STREAMING_ERROR = AppEventsConstants.EVENT_PARAM_LIVE_STREAMING_ERROR
    const val CURRENCY = AppEventsConstants.EVENT_PARAM_CURRENCY
    const val REGISTRATION_METHOD = AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD
    const val CONTENT_TYPE = AppEventsConstants.EVENT_PARAM_CONTENT_TYPE
    const val CONTENT = AppEventsConstants.EVENT_PARAM_CONTENT
    const val CONTENT_ID = AppEventsConstants.EVENT_PARAM_CONTENT_ID
    const val SEARCH_STRING = AppEventsConstants.EVENT_PARAM_SEARCH_STRING
    const val SUCCESS = AppEventsConstants.EVENT_PARAM_SUCCESS
    const val MAX_RATING_VALUE = AppEventsConstants.EVENT_PARAM_MAX_RATING_VALUE
    const val PAYMENT_INFO_AVAILABLE = AppEventsConstants.EVENT_PARAM_PAYMENT_INFO_AVAILABLE
    const val NUM_ITEMS = AppEventsConstants.EVENT_PARAM_NUM_ITEMS
    const val LEVEL = AppEventsConstants.EVENT_PARAM_LEVEL
    const val DESCRIPTION = AppEventsConstants.EVENT_PARAM_DESCRIPTION
    const val SOURCE_APPLICATION = AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION
    const val VALUE_YES = AppEventsConstants.EVENT_PARAM_VALUE_YES
    const val VALUE_NO = AppEventsConstants.EVENT_PARAM_VALUE_NO
    const val AD_TYPE = AppEventsConstants.EVENT_PARAM_AD_TYPE
    const val ORDER_ID = AppEventsConstants.EVENT_PARAM_ORDER_ID
    const val PARAM_PRODUCT_CUSTOM_LABEL_0 = AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_0
    const val PARAM_PRODUCT_CUSTOM_LABEL_1 = AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_1
    const val PARAM_PRODUCT_CUSTOM_LABEL_2 = AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_2
    const val PARAM_PRODUCT_CUSTOM_LABEL_3 = AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_3
    const val PARAM_PRODUCT_CUSTOM_LABEL_4 = AppEventsConstants.EVENT_PARAM_PRODUCT_CUSTOM_LABEL_4
    const val PARAM_PRODUCT_APPLINK_IOS_URL = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_URL
    const val PARAM_PRODUCT_APPLINK_IOS_APP_STORE_ID = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_STORE_ID
    const val PARAM_PRODUCT_APPLINK_IOS_APP_NAME = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IOS_APP_NAME
    const val PARAM_PRODUCT_APPLINK_IPHONE_URL = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_URL
    const val PARAM_PRODUCT_APPLINK_IPHONE_APP_STORE_ID = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_STORE_ID
    const val PARAM_PRODUCT_APPLINK_IPHONE_APP_NAME = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPHONE_APP_NAME
    const val PARAM_PRODUCT_APPLINK_IPAD_URL = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_URL
    const val PARAM_PRODUCT_APPLINK_IPAD_APP_STORE_ID = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_STORE_ID
    const val PARAM_PRODUCT_APPLINK_IPAD_APP_NAME = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_IPAD_APP_NAME
    const val PARAM_PRODUCT_APPLINK_ANDROID_URL = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_URL
    const val PARAM_PRODUCT_APPLINK_ANDROID_PACKAGE = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_PACKAGE
    const val PARAM_PRODUCT_APPLINK_ANDROID_APP_NAME = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_ANDROID_APP_NAME
    const val PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_URL = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_URL
    const val PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_ID = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_ID
    const val PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_NAME = AppEventsConstants.EVENT_PARAM_PRODUCT_APPLINK_WINDOWS_PHONE_APP_NAME
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

    const val USER_ID = "fb_user_id"
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
    const val DEBUG_ENABLED = "debug"
}