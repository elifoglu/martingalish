package com.philocoder.martingalish

object StrategyInputValidator {

    fun isValid(input: String): Boolean {
        if (!input.startsWith("g")) {
            println("Your strategy should start with a 'g'")
            return false
        }
        return true
    }
}