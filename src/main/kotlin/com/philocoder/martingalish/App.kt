package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.input.Strategy

fun main() {
    do {
        val inputs: Inputs = Inputs.receive();
        val strategy = Strategy.from(inputs.strategyInput)
        println("Martingalish key: ${inputs.odd}${inputs.strategyInput}${inputs.bankrollReduceRatio.map { it.toString() }.getOrElse { "" }}")
        StrategyOutput.calculate(strategy, inputs.odd, inputs.bankrollReduceRatio).printInfo()
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}