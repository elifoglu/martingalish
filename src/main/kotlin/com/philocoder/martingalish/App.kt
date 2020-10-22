package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some
import arrow.core.getOrElse
import com.philocoder.martingalish.DesiredBetResult.LoseMoney
import com.philocoder.martingalish.StrategyInputValidator.isValid

fun main() {
    do {
        print("Enter your martingalish strategy (e.g: gggb or ggbl): ")
        val strategyInput = readLine()
        val validInput = isValid(strategyInput!!)
        if (!validInput) continue
        val strategy = Strategy.from(strategyInput)

        print("Enter odd: ")
        val odd = readLine()!!.toDouble()

        val bankrollReduceRatio = if (strategy.containsDesiredBetResult(LoseMoney)) {
            print("Enter bankroll reduce ratio: ")
            Some(readLine()!!.toDouble())
        } else None

        println("Key: $odd$strategyInput${bankrollReduceRatio.map { it.toString() }.getOrElse { "" }}")
        StrategyOutput.calculate(strategy, odd, bankrollReduceRatio).printInfo()

        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}