package com.philocoder.martingalish.bet

import com.philocoder.martingalish.bet.DesiredBetResult.LoseMoney


data class LoseMoneyStrategy(val loss: Double, val lossRatio: Double) : BetStrategy {

    override val representation: Char
        get() = LoseMoney.representation

    override val stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double ->
        (totalSpentUntilNow - loss) / (odd - 1)
    }
}