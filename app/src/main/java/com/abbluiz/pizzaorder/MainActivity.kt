package com.abbluiz.pizzaorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

private const val DELIVERY_PRICE = 2
private const val SMALL_PIZZA_PRICE = 3
private const val MEDIUM_PIZZA_PRICE = 6
private const val LARGE_PIZZA_PRICE = 9
private const val EXTRA_LARGE_PIZZA_PRICE = 12
private const val PEPPERONI_PRICE = 1
private const val CHICKEN_PRICE = 2
private const val BACON_PRICE = 2
private const val EXTRA_CHEESE_PRICE = 2

private const val DELIVERY = "hasDelivery"
private var hasDelivery = "false"

private const val ADDRESS = "userAddress"
private var userAddress = ""

private const val PREFERRED_SIZE = "preferredSize"
private var preferredSize = ""

private const val ONIONS = "containsOnions"
private var containsOnions = "false"

private const val GARLIC = "containsGarlic"
private var containsGarlic = "false"

private const val BLACK_OLIVES = "containsBlackOlives"
private var containsBlackOlives = "false"

private const val PEPPERONI = "containsPepperoni"
private var containsPepperoni = "false"

private const val CHICKEN = "containsChicken"
private var containsChicken = "false"

private const val BACON = "containsBacon"
private var containsBacon = "false"

private const val EXTRA_CHEESE = "containsExtraCheese"
private var containsExtraCheese = "false"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.tb_appbar))

        supportActionBar?.title = getString(R.string.main_activity_toolbar_text)

        if (savedInstanceState != null) {

            hasDelivery = savedInstanceState.getString(DELIVERY).toString()
            userAddress = savedInstanceState.getString(ADDRESS).toString()
            preferredSize = savedInstanceState.getString(PREFERRED_SIZE).toString()
            containsOnions = savedInstanceState.getString(ONIONS).toString()
            containsGarlic = savedInstanceState.getString(GARLIC).toString()
            containsBlackOlives = savedInstanceState.getString(BLACK_OLIVES).toString()
            containsPepperoni = savedInstanceState.getString(PEPPERONI).toString()
            containsChicken = savedInstanceState.getString(CHICKEN).toString()
            containsBacon = savedInstanceState.getString(BACON).toString()
            containsExtraCheese = savedInstanceState.getString(EXTRA_CHEESE).toString()

        }

        bt_place_order.setOnClickListener {

            val intent = Intent(this, DisplayOrderSummaryActivity::class.java).apply {

                putExtra(DELIVERY, hasDelivery)
                putExtra(ADDRESS, userAddress)
                putExtra(PREFERRED_SIZE, preferredSize)
                putExtra(ONIONS, containsOnions)
                putExtra(GARLIC, containsGarlic)
                putExtra(BLACK_OLIVES, containsBlackOlives)
                putExtra(PEPPERONI, containsPepperoni)
                putExtra(CHICKEN, containsChicken)
                putExtra(BACON, containsBacon)
                putExtra(EXTRA_CHEESE, containsExtraCheese)

            }

            startActivity(intent)

        }

        rg_pizza_size_options.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                rb_small_pizza_option.id -> preferredSize = "small"
                rb_medium_pizza_option.id -> preferredSize = "medium"
                rb_large_pizza_option.id -> preferredSize = "large"
                rb_extra_large_pizza_option.id -> preferredSize = "extraLarge"
            }

            if (checkedId >= 0) {

                bt_place_order.isEnabled = true
                cb_onions_option.isEnabled = true
                cb_garlic_option.isEnabled = true
                cb_black_olives_option.isEnabled = true
                cb_pepperoni_option.isEnabled = true
                cb_chicken_option.isEnabled = true
                cb_bacon_option.isEnabled = true
                cb_extra_cheese.isEnabled = true
                sw_home_delivery.isEnabled = true
                bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

            }

        }

        cb_onions_option.setOnCheckedChangeListener { _, isChecked ->

            containsOnions = if (isChecked) {

                "true"

            } else {

                "false"

            }

        }

        cb_garlic_option.setOnCheckedChangeListener { _, isChecked ->

            containsGarlic = if (isChecked) {

                "true"

            } else {

                "false"

            }

        }

        cb_black_olives_option.setOnCheckedChangeListener { _, isChecked ->

            containsBlackOlives = if (isChecked) {

                "true"

            } else {

                "false"

            }

        }

        cb_pepperoni_option.setOnCheckedChangeListener { _, isChecked ->

            containsPepperoni = if (isChecked) {

                "true"

            } else {

                "false"

            }

            bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

        }

        cb_chicken_option.setOnCheckedChangeListener { _, isChecked ->

            containsChicken = if (isChecked) {

                "true"

            } else {

                "false"

            }

            bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

        }

        cb_bacon_option.setOnCheckedChangeListener { _, isChecked ->

            containsBacon = if (isChecked) {

                "true"

            } else {

                "false"

            }

            bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

        }

        cb_extra_cheese.setOnCheckedChangeListener { _, isChecked ->

            containsExtraCheese = if (isChecked) {

                "true"

            } else {

                "false"

            }

            bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

        }

        sw_home_delivery.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                hasDelivery = "true"
                bt_place_order.isEnabled = false

            } else {

                hasDelivery = "false"
                et_address.setText("")
                bt_place_order.isEnabled = true

            }

            et_address.isEnabled = isChecked
            bt_place_order.text = getString(R.string.bt_place_order_enabled_text) + calculateTotalPrice()

        }

        et_address.addTextChangedListener( object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                userAddress = s.toString()

                bt_place_order.isEnabled = s.toString().isNotEmpty()

            }

        })

    }

    private fun calculateTotalPrice(): Int {

        var basePrice = 0
        var toppingsTotalPrice = 0
        var deliveryPrice = 0

        when (preferredSize) {
            "small" -> basePrice = SMALL_PIZZA_PRICE
            "medium" -> basePrice = MEDIUM_PIZZA_PRICE
            "large" -> basePrice = LARGE_PIZZA_PRICE
            "extraLarge" -> basePrice = EXTRA_LARGE_PIZZA_PRICE
        }

        if (containsPepperoni.toBoolean()) {

            toppingsTotalPrice += PEPPERONI_PRICE

        }

        if (containsChicken.toBoolean()) {

            toppingsTotalPrice += CHICKEN_PRICE

        }

        if (containsBacon.toBoolean()) {

            toppingsTotalPrice += BACON_PRICE

        }

        if (containsExtraCheese.toBoolean()) {

            toppingsTotalPrice += EXTRA_CHEESE_PRICE

        }

        if (hasDelivery.toBoolean()) {

            deliveryPrice = DELIVERY_PRICE

        }

        return basePrice + toppingsTotalPrice + deliveryPrice

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putString(DELIVERY, hasDelivery)
        savedInstanceState.putString(ADDRESS, userAddress)
        savedInstanceState.putString(PREFERRED_SIZE, preferredSize)
        savedInstanceState.putString(ONIONS, containsOnions)
        savedInstanceState.putString(GARLIC, containsGarlic)
        savedInstanceState.putString(BLACK_OLIVES, containsBlackOlives)
        savedInstanceState.putString(PEPPERONI, containsPepperoni)
        savedInstanceState.putString(CHICKEN, containsChicken)
        savedInstanceState.putString(EXTRA_CHEESE, containsExtraCheese)

    }

}
