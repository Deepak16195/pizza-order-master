package com.abbluiz.pizzaorder

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_display_order_summary.*

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

class DisplayOrderSummaryActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_order_summary)
        setSupportActionBar(findViewById(R.id.tb_appbar))

        supportActionBar?.title = getString(R.string.display_order_summary_toolbar_text)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hasDelivery = intent.extras?.getString(DELIVERY).toString()
        userAddress = intent.extras?.getString(ADDRESS).toString()
        preferredSize = intent.extras?.getString(PREFERRED_SIZE).toString()
        containsOnions = intent.extras?.getString(ONIONS).toString()
        containsGarlic = intent.extras?.getString(GARLIC).toString()
        containsBlackOlives = intent.extras?.getString(BLACK_OLIVES).toString()
        containsPepperoni = intent.extras?.getString(PEPPERONI).toString()
        containsChicken = intent.extras?.getString(CHICKEN).toString()
        containsBacon = intent.extras?.getString(BACON).toString()
        containsExtraCheese = intent.extras?.getString(EXTRA_CHEESE).toString()

        when (preferredSize) {
            "small" -> tv_pizza.text = getString(R.string.word_small) + " " + getString(R.string.word_pizza) + " +$" + SMALL_PIZZA_PRICE
            "medium" -> tv_pizza.text = getString(R.string.word_medium) + " " + getString(R.string.word_pizza)+ " +$" + MEDIUM_PIZZA_PRICE
            "large" -> tv_pizza.text = getString(R.string.word_large) + " " + getString(R.string.word_pizza)+ " +$" + LARGE_PIZZA_PRICE
            "extraLarge" -> tv_pizza.text = getString(R.string.word_extra_large) + " " + getString(R.string.word_pizza)+ " +$" + EXTRA_LARGE_PIZZA_PRICE
        }

        val toppings = ArrayList<String>()

        if (containsOnions.toBoolean()) {

            toppings.add(getString(R.string.desc_onions))

        }

        if (containsGarlic.toBoolean()) {

            toppings.add(getString(R.string.word_garlic))

        }

        if (containsBlackOlives.toBoolean()) {

            toppings.add(getString(R.string.desc_black_olives))

        }

        if (containsPepperoni.toBoolean()) {

            toppings.add(getString(R.string.desc_pepperoni))

        }

        if (containsChicken.toBoolean()) {

            toppings.add(getString(R.string.desc_chicken))

        }

        if (containsBacon.toBoolean()) {

            toppings.add(getString(R.string.desc_bacon))
        }

        if (containsExtraCheese.toBoolean()) {

            toppings.add(getString(R.string.desc_extra_cheese))

        }

        if (hasDelivery.toBoolean()) {

            tv_delivery.text = getString(R.string.word_delivery) + " +$" + DELIVERY_PRICE
            tv_address.text = getString(R.string.order_summary_tv_address_text) + " " + userAddress

        } else {

            tv_address.text = getString(R.string.sentence_pick_up_at_store)

        }

        tv_total_price_result.text = "$" + calculateTotalPrice()

        val adapter = ArrayAdapter(this, R.layout.listview_item, toppings)
        lv_toppings.adapter = adapter

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

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true

    }

}
