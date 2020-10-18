package com.philocoder.martingalish

import java.util.*

fun main() {
    do {
        print("Enter your martingalish strategy (e.g: gggr): ")
        val strategyInput: String? = readLine()
        if (!strategyInput!!.startsWith("g")) {
            println("Your strategy should start with a 'g'")
        }
        print("Enter odd: ")
        val odd = readLine()!!.toDouble()
        val sequence: List<EarningType> = Strategy.from(strategyInput).sequence.drop(n = 1)
        val firstStake = 1.0
        val stakeList: ArrayList<Double> = arrayListOf(firstStake)
        val fixedEarning: Double = odd - firstStake
        var totalSpentUntilNow: Double = firstStake
        for (type: EarningType in sequence) {
            val currentStake: Double = type.stakeCalculatorFn(totalSpentUntilNow, fixedEarning)
            stakeList.add(currentStake)
            totalSpentUntilNow += currentStake
        }
        println(stakeList)
        println("Bankroll: $totalSpentUntilNow")
        println("Earning as percentage: %${(fixedEarning / stakeList.sum())}")
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}