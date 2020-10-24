package com.philocoder.martingalish.bet

import java.lang.RuntimeException

sealed class DesiredBetResult(val representation: Char) {

    object GainMoney : DesiredBetResult(representation = 'g')
    object LoseMoney : DesiredBetResult(representation = 'l')
    object BackToBankroll : DesiredBetResult(representation = 'b'), BetStrategy {
        override val stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double -> totalSpentUntilNow / (odd - 1) }
    }

    companion object {
        fun fromRepresentation(representation: Char): DesiredBetResult =
                when (representation) {
                    GainMoney.representation -> GainMoney
                    LoseMoney.representation -> LoseMoney
                    BackToBankroll.representation -> BackToBankroll
                    else -> throw RuntimeException()
                }
    }
}