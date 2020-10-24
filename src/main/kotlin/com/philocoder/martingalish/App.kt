package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.input.Inputs

fun main() {
    do {
        val inputs: Inputs = Inputs.receive();
        val strategy = Strategy.build(inputs)
        println("Martingalish key: ${inputs.odd}${inputs.strategyInput}${inputs.bankrollReduceRatio.map { it.toString() }.getOrElse { "" }}")
        BetList.from(strategy).printInfo(inputs.actualBankroll)
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}