package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.input.Inputs

fun main() {
    do {
        val inputs: Inputs = Inputs.receive();
        println("Martingalish key: ${inputs.odd}${inputs.strategy}${inputs.bankrollReduceRatio.map { it.toString() }.getOrElse { "" }}")
        StrategyOutput.calculate(inputs).printInfo()
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}