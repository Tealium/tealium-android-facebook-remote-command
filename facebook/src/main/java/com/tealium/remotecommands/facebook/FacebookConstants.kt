@file:JvmName("FacebookConstants")

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
}

enum class StandardEventNames(val value: String) {
    ACHIEVE_LEVEL("achievelevel"),
    AD_CLICK("adclick"),
    AD_IMPRESSION("adimpression"),
    ADD_PAYMENT_INFO("addpaymentinfo"),
    ADD_TO_CART("addtocart"),
    ADD_TO_WISHLIST("addtowishlist"),
    COMPLETE_REGISTRATION("completeregistration"),
    COMPLETE_TUTORIAL("completetutorial"),
    CONTACT("contact"),
    VIEWED_CONTENT("viewedcontent"),
    SEARCHED("searched"),
    RATED("rated"),
    CUSTOMIZE_PRODUCT("customizeproduct"),
    DONATE("donate"),
    FIND_LOCATION("findlocation"),
    SCHEDULE("schedule"),
    START_TRIAL("starttrial"),
    SUBMIT_APPLICATION("submitapplication"),
    SUBSCRIBE("subscribe"),
    INITIATED_CHECKOUT("initiatedcheckout"),
    UNLOCKED_ACHIEVEMENT("unlockedachievement"),
    SPENT_CREDITS("spentcredits")
}

object Event {
    const val EVENT = "event"
    const val VALUE_TO_SUM = "fb_value_to_sum"
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
    const val INITIALIZE = "initialize"
    const val APPLICATION_ID = "applicationid"
    const val ACCESS_TOKEN = "accesstoken"
    const val USER_ID = "userid"
}