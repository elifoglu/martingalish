package com.philocoder.martingalish

sealed class DesiredBetResult(val representation: Char, val stakeCalculatorFn: (Double, Double) -> Double) {

    data class GainMoney(val earning: Double) : DesiredBetResult(
            representation = representation,
            stakeCalculatorFn = { totalSpentUntilNow, odd -> (totalSpentUntilNow + earning) / (odd - 1) }) {
        companion object {
            const val representation = 'g'
        }
    }

    data class LoseMoney(val bankrollReduceRatio: Double) : DesiredBetResult(
            representation = 'l',
            stakeCalculatorFn = { totalSpentUntilNow, odd -> totalSpentUntilNow / (odd / bankrollReduceRatio - 1) }) {
        companion object {
            const val representation = 'l'
        }
    }

    object BackToBankroll : DesiredBetResult(
            representation = 'b',
            stakeCalculatorFn = { totalSpentUntilNow, odd -> totalSpentUntilNow / (odd - 1) })
}