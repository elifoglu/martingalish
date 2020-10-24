package com.philocoder.martingalish.bet

import com.philocoder.martingalish.bet.DesiredBetResult.GainMoney


data class GainMoneyStrategy(val earning: Double, val gainRatio: Double) : BetStrategy {

    override val representation: Char
        get() = GainMoney.representation

    override val stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double ->
        (totalSpentUntilNow + earning) / (odd - 1)
    }
}