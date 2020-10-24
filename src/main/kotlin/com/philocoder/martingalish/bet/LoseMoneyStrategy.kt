package com.philocoder.martingalish.bet

import com.philocoder.martingalish.bet.DesiredBetResult.LoseMoney


data class LoseMoneyStrategy(val bankrollReduceRatio: Double) : BetStrategy {

    override val representation: Char
        get() = LoseMoney.representation

    override val stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double ->
        totalSpentUntilNow / (odd / bankrollReduceRatio - 1)
    }
}