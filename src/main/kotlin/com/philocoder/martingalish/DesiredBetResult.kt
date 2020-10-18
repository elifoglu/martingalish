package com.philocoder.martingalish

import arrow.core.Option
import arrow.core.getOrElse
import java.lang.RuntimeException

sealed class DesiredBetResult(val representation: Char, val stakeCalculatorFn: (Double, Double, Option<Double>) -> Double) {

    object GainMoney : DesiredBetResult(
            representation = 'g',
            stakeCalculatorFn = { totalSpentUntilNow, fixedEarning, _ -> (totalSpentUntilNow + fixedEarning) / fixedEarning })

    object LoseMoney : DesiredBetResult(
            representation = 'l',
            stakeCalculatorFn = { totalSpentUntilNow, fixedEarning, bankrollReduceRatioOpt ->
                val odd = fixedEarning + 1
                val bankrollReduceRatio = bankrollReduceRatioOpt.getOrElse { throw RuntimeException() }
                totalSpentUntilNow / (bankrollReduceRatio * odd - 1)
            })

    object BackToBankroll : DesiredBetResult(
            representation = 'b',
            stakeCalculatorFn = { totalSpentUntilNow, fixedEarning, _ -> totalSpentUntilNow / fixedEarning })

    companion object {
        fun fromRepresentation(char: Char): DesiredBetResult =
                when (char) {
                    GainMoney.representation -> GainMoney
                    LoseMoney.representation -> LoseMoney
                    BackToBankroll.representation -> BackToBankroll
                    else -> throw RuntimeException()
                }
    }
}