package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some
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

        val firstStake = 1.0
        val stakeList = arrayListOf(firstStake)
        val fixedEarning = odd - firstStake
        var totalSpentUntilNow = firstStake
        for (desiredBetResult in strategy.sequence.drop(n = 1)) {
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