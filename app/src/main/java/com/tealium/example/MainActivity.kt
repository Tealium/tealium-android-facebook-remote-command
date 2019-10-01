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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        purchaseButton = findViewById(R.id.purchase_button)
        purchaseButton.setOnClickListener { logPurchase() }

        setUserButton = findViewById(R.id.set_user_button)
        setUserButton.setOnClickListener { setUser() }

        updateUserValueButton = findViewById(R.id.update_user_value_button)
        updateUserValueButton.setOnClickListener { updateUser() }

        logProductButton = findViewById(R.id.log_product_item_button)
        logProductButton.setOnClickListener { logProductItem() }

        flushButton = findViewById(R.id.flush_button)
        flushButton.setOnClickListener { flush() }

        achieveLevelButton = findViewById(R.id.achieve_level_button)
        achieveLevelButton.setOnClickListener { achieveLevel() }

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
        TealiumHelper.trackView("logpurchase")
    }

    private fun setUser() {
        TealiumHelper.trackView("setuser")
    }

    private fun updateUser() {
        TealiumHelper.trackView("updateuservalue")
    }

    private fun logProductItem() {
        TealiumHelper.trackView("logproductitem")
    }

    private fun flush() {
        TealiumHelper.trackView("flush")
    }

    private fun achieveLevel() {
        TealiumHelper.trackView("achievelevel")
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.TEALIUM_FACEBOOK_VERSION)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(getString(R.string.about_message, BuildConfig.TEALIUM_VERSION))
        builder.create().show()
    }
}
