package com.tealium.example

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var purchaseButton: Button
    private lateinit var setUserButton: Button
    private lateinit var updateUserValueButton: Button
    private lateinit var logProductButton: Button
    private lateinit var flushButton: Button
    private lateinit var achieveLevelButton: Button
    private lateinit var completeRegistrationButton: Button
    private lateinit var addToCartButton: Button
    private lateinit var setUserIdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUserButton = findViewById(R.id.set_user_button)
        setUserButton.setOnClickListener { setUser() }

        purchaseButton = findViewById(R.id.purchase_button)
        purchaseButton.setOnClickListener { logPurchase() }

        updateUserValueButton = findViewById(R.id.update_user_value_button)
        updateUserValueButton.setOnClickListener { updateUser() }

        logProductButton = findViewById(R.id.log_product_item_button)
        logProductButton.setOnClickListener { logProductItem() }

        flushButton = findViewById(R.id.flush_button)
        flushButton.setOnClickListener { flush() }

        achieveLevelButton = findViewById(R.id.achieve_level_button)
        achieveLevelButton.setOnClickListener { achieveLevel() }

        completeRegistrationButton = findViewById(R.id.complete_reg_button)
        completeRegistrationButton.setOnClickListener { completeRegistration() }

        addToCartButton = findViewById(R.id.add_to_cart_button)
        addToCartButton.setOnClickListener { addtoCart() }

        setUserIdButton = findViewById(R.id.set_user_id_button)
        setUserIdButton.setOnClickListener { setUserId() }

        TealiumHelper.trackView("home_view")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_item) {
            showInfo()
        }
        return true
    }

    private fun logPurchase() {
        TealiumHelper.trackEvent("logpurchase", Purchase.info)
    }

    private fun setUser() {
        TealiumHelper.trackEvent("setuser", Profile.user)
    }

    private fun updateUser() {
        val data = mapOf<String, Any>("customer_update_key" to "customer_last_name",
                "customer_update_value" to "Smith")
        TealiumHelper.trackEvent("updateuservalue", data)
    }

    private fun logProductItem() {
        TealiumHelper.trackEvent("logproductitem", Product.item)
    }

    private fun flush() {
        TealiumHelper.trackEvent("flush")
    }

    private fun achieveLevel() {
        TealiumHelper.trackEvent("achievelevel",
                mapOf<String, Any>("level" to Profile.level ))
    }

    private fun completeRegistration() {
        TealiumHelper.trackEvent("completeregistration",
                mapOf<String, Any>("registration_method" to Profile.registrationMethod))
    }

    private fun addtoCart() {
        val data = mapOf<String, Any>("product_id" to arrayOf("abc123"),
                "product_unit_price" to arrayOf(19.99))
        TealiumHelper.trackEvent("addtocart", data)
    }

    private fun setUserId() {
        TealiumHelper.trackEvent("setuserid",
                mapOf<String, Any>("customer_id" to Profile.customerId ))
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.TEALIUM_FACEBOOK_VERSION)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(getString(R.string.about_message, BuildConfig.TEALIUM_VERSION))
        builder.create().show()
    }
}

object Profile {
    val customerId: String = "abc123"
    val user: Map<String, Any> = User.map()
    val level: String = "5"
    val registrationMethod = "google"
}

object User {
    fun map(): Map<String, Any> {
        return mapOf<String, Any>(
                "customer_first_name" to "Jane",
                "customer_last_name" to "Doe",
                "customer_phone" to "8585559636" ,
                "customer_gender" to "F",
                "customer_city" to "San Diego" ,
                "customer_state" to "CA" ,
                "customer_country" to "US")
    }
}

object Product {
    val item: Map<String, Any> = map()

    fun map(): Map<String, Any> {
        return mapOf<String, Any>(
                "product_id" to arrayOf("abc123"),
                "product_availability" to arrayOf(1),
                "product_condition" to arrayOf(2),
                "product_description" to arrayOf("something cool"),
                "product_image_url" to arrayOf("https://link.to.image"),
                "product_name" to arrayOf("https://link.to.product"),
                "product_gtin" to arrayOf("AKEJSHE324JS2398"),
                "product_brand" to arrayOf("cool brand"),
                "product_unit_price" to arrayOf(19.99),
                "currency" to "USD",
                "online_store_id" to arrayOf("STORE234"),
                "product_bulk_discount" to arrayOf("5"))
    }
}

object Purchase {
    val info: Map<String, Any> = map()

    fun map(): Map<String, Any> {
        return mapOf<String, Any>(
                "order_id" to arrayOf("ORD123231"),
                "order_subtotal" to 19.99,
                "order_coupon_code" to "SPRING20",
                "currency" to "USD")
    }
}