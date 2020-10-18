package com.philocoder.martingalish

import com.philocoder.martingalish.DesiredBetResult.GainMoney

object StrategyInputValidator {

    fun isValid(input: String): Boolean {
        if (!input.startsWith(GainMoney.representation)) {
            println("Your strategy should start with a ${GainMoney.representation}")
            return false
        }
        //input.toCharArray().all { }
        return true
    }
}