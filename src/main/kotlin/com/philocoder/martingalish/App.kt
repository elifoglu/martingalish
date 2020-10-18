package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some

fun main() {
    do {
        print("Enter your martingalish strategy (e.g: gggb or ggbl): ")
        val strategyInput = readLine()
        if (!strategyInput!!.startsWith("g")) {
            println("Your strategy should start with a 'g'")
        }
        print("Enter odd: ")
        val odd = readLine()!!.toDouble()
        val bankrollReduceRatio = if (strategyInput.contains('l')) {
            print("Enter bankroll reduce ratio: ")
            Some(readLine()!!.toDouble())
        } else None
        val sequence = Strategy.from(strategyInput).sequence.drop(n = 1)
        val firstStake = 1.0
        val stakeList = arrayListOf(firstStake)
        val fixedEarning = odd - firstStake
        var totalSpentUntilNow = firstStake
        for (desiredBetResult in sequence) {
            val currentStake = desiredBetResult.stakeCalculatorFn(totalSpentUntilNow, fixedEarning, bankrollReduceRatio)
            stakeList.add(currentStake)
            totalSpentUntilNow += currentStake
        }
        println(stakeList)
        println("Bankroll: $totalSpentUntilNow")
        println("Earning as percentage: %${(fixedEarning / stakeList.sum())}")
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}