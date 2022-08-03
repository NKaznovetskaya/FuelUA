package com.zeira.fuelua.utils

import com.zeira.fuelua.domain.models.CreateFuel
import com.zeira.fuelua.domain.models.Fuel

fun Fuel.str(currencySymbol: String): String {
    return when (this.quantity_limit) {
        -1 -> String.format(
            "%s, %s%s",
            "Без обмежень",
            this.price.toString(),
            currencySymbol
        )
        0 -> "Немає в наявності"
        else -> {
            String.format(
                "%s, %s%s",
                "до " + this.quantity_limit.toString() + "л",
                this.price.toString(),
                currencySymbol
            )
        }
    }
}

fun CreateFuel.str(): String {
    return when (this.quantity_limit) {
        -1 -> "ꚙ"
        0 -> "X"
        else -> quantity_limit.toString()
    }
}

fun Fuel.toCreateFuel(): CreateFuel = CreateFuel(availability, price, quantity_limit, title)