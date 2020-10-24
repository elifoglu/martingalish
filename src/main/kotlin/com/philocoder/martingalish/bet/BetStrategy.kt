package com.philocoder.martingalish.bet

import com.philocoder.martingalish.bet.BetResult.*

sealed class BetStrategy(val betResult: BetResult, val stakeCalculatorFn: (Double, Double) -> Double) {

    data class GainMoneyStrategy(val earning: Double, val gainRatio: Double) : BetStrategy(
            betResult = GainMoney,
            stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double -> (totalSpentUntilNow + earning) / (odd - 1) })

    data class LoseMoneyStrategy(val loss: Double, val lossRatio: Double) : BetStrategy(
            betResult = LoseMoney,
            stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double -> (totalSpentUntilNow - loss) / (odd - 1) })

    object BackToBankrollStrategy : BetStrategy(
            betResult = BackToBankroll,
            stakeCalculatorFn = { totalSpentUntilNow: Double, odd: Double -> totalSpentUntilNow / (odd - 1) })
}