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
    private lateinit var setUserIdButton: Button
    private lateinit var updateUserValueButton: Button
    private lateinit var logProductButton: Button
    private lateinit var flushButton: Button
    private lateinit var achieveLevelButton: Button
    private lateinit var addToCartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        purchaseButton = findViewById(R.id.purchase_button)
        purchaseButton.setOnClickListener { logPurchase() }

        setUserButton = findViewById(R.id.set_user_button)
        setUserButton.setOnClickListener { setUser() }

        setUserIdButton = findViewById(R.id.set_user_id_button)
        setUserIdButton.setOnClickListener { setUserId() }

        updateUserValueButton = findViewById(R.id.update_user_value_button)
        updateUserValueButton.setOnClickListener { updateUser() }

        logProductButton = findViewById(R.id.log_product_item_button)
        logProductButton.setOnClickListener { logProductItem() }

        flushButton = findViewById(R.id.flush_button)
        flushButton.setOnClickListener { flush() }

        achieveLevelButton = findViewById(R.id.achieve_level_button)
        achieveLevelButton.setOnClickListener { achieveLevel() }

        addToCartButton = findViewById(R.id.add_to_cart_button)
        addToCartButton.setOnClickListener { addToCart() }

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
        TealiumHelper.trackEvent("setuser", User.profile)
    }

    private fun setUserId() {
        TealiumHelper.trackEvent("setuserid", mutableMapOf<String, Any>(
            "customer_id" to User.customerId
        ))
    }

    private fun updateUser() {
        TealiumHelper.trackEvent("updateuservalue", mutableMapOf<String, Any>(
            "customer_update_key" to "customer_last_name",
            "customer_update_value" to "Smith"
        ))
    }

    private fun logProductItem() {
        TealiumHelper.trackEvent("logproductitem", Product.info)
    }

    private fun flush() {
        TealiumHelper.trackEvent("flush")
    }

    private fun achieveLevel() {
        TealiumHelper.trackEvent("achievelevel", mutableMapOf<String, Any>(
            "level" to "5"
        ))
    }

    private fun addToCart() {
        TealiumHelper.trackEvent("addtocart", mutableMapOf<String, Any>(
            "product_id" to Product.productId,
            "product_unit_price" to Product.productPrice
        ))
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.TEALIUM_FACEBOOK_VERSION)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(getString(R.string.about_message, BuildConfig.TEALIUM_VERSION))
        builder.create().show()
    }
}

object Purchase {
    val info = mutableMapOf<String, Any>(
        "order_id" to "order123",
        "currency" to "USD",
        "order_subtotal" to 19.99,
        "bulk_discount" to "15",
        "online_store_id" to 50
    )
}

object User {
    val customerId = "cust123"
    val profile = mutableMapOf<String, String>(
        "customer_email" to "test@test.com",
        "customer_id" to customerId,
        "customer_first_name" to "John",
        "customer_last_name" to "Doe",
        "customer_phone" to "858-555-6666",
        "customer_gender" to "M",
        "customer_city" to "San Diego",
        "customer_state" to "CA",
        "customer_zip" to "92121",
        "customer_country" to "US"
    )
}

object Product {
    val productId = "abc123"
    val productPrice = 19.99
    val info = mutableMapOf<String, Any>(
        "product_id" to productId,
        "product_availability" to 1,
        "product_condition" to 2,
        "product_description" to "really cool",
        "product_image_url" to "https://link.to.image",
        "product_url" to "https://link.to.product",
        "product_name" to "some cool product",
        "product_gtin" to "ASDF235562SDFSDF",
        "product_brand" to "awesome brand",
        "product_unit_price" to productPrice,
        "currency" to "USD",
        "online_store_id" to "ABC234SDF",
        "bulk_discount" to "15"
    )
}