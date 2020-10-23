package com.philocoder.martingalish.input

import com.philocoder.martingalish.DesiredBetResult.*

object StrategyInputValidator {

    private val allValidChars = arrayOf(
            GainMoney.representation,
            LoseMoney.representation,
            BackToBankroll.representation
    )

    fun isValid(input: String): Boolean {
        if (input.toCharArray().any { !allValidChars.contains(it) }) {
            println("Your strategy contains invalid chars. Valid chars are: $allValidChars")
            return false
        }
        if (!input.startsWith(GainMoney.representation)) {
            println("Your strategy should start with a ${GainMoney.representation}")
            return false
        }
        return true
    }
}