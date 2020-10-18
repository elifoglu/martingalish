package com.philocoder.martingalish

import com.philocoder.martingalish.DesiredBetResult.*

object StrategyInputValidator {

    private val allValidChars = arrayOf(GainMoney, LoseMoney, BackToBankroll).map { it.representation }

    fun isValid(input: String): Boolean {
        if (!input.startsWith(GainMoney.representation)) {
            println("Your strategy should start with a ${GainMoney.representation}")
            return false
        }
        if (input.toCharArray().any { !allValidChars.contains(it) }) {
            println("Your strategy contains invalid chars. Valid chars are: $allValidChars")
            return false
        }
        return true
    }
}