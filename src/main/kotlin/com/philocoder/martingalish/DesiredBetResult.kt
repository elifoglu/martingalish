package com.philocoder.martingalish

import arrow.core.Option
import arrow.core.getOrElse
import java.lang.RuntimeException

sealed class DesiredBetResult(val stakeCalculatorFn: (Double, Double, Option<Double>) -> Double) {

    object GainMoney : DesiredBetResult(
            { totalSpentUntilNow, fixedEarning, _ -> (totalSpentUntilNow + fixedEarning) / fixedEarning })

    object LoseMoney : DesiredBetResult(
            { totalSpentUntilNow, fixedEarning, bankrollReduceRatioOpt ->
                val odd = fixedEarning + 1
                val bankrollReduceRatio = bankrollReduceRatioOpt.getOrElse { throw RuntimeException() }
                totalSpentUntilNow / (bankrollReduceRatio * odd - 1)
            })

    object BackToBankroll : DesiredBetResult(
            { totalSpentUntilNow, fixedEarning, _ -> totalSpentUntilNow / fixedEarning })
}