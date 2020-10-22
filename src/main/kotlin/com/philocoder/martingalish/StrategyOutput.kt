package com.philocoder.martingalish

import arrow.core.Option

data class StrategyOutput(val stakeList: List<Double>,
                          val bankroll: Double,
                          val earningAsPercentage: Double) {

    fun printInfo() = println("""
            |Stake list: $stakeList 
            |Bankroll: $bankroll
            |Earning as percentage: %$earningAsPercentage
            |""".trimMargin())

    companion object {
        fun calculate(strategy: Strategy, odd: Double, bankrollReduceRatio: Option<Double>): StrategyOutput {
            val firstStake = 1.0
            val stakeList = arrayListOf(firstStake)
            val fixedEarning = odd - firstStake
            for (desiredBetResult in strategy.sequence.drop(n = 1)) {
                val currentStake = desiredBetResult.stakeCalculatorFn(stakeList.sum(), fixedEarning, bankrollReduceRatio)
                stakeList.add(currentStake)
            }
            return StrategyOutput(
                    stakeList = stakeList,
                    bankroll = stakeList.sum(),
                    earningAsPercentage = fixedEarning * 100 / stakeList.sum()
            )
        }
    }
}