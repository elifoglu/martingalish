package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.bet.DesiredBetResult.GainMoney
import com.philocoder.martingalish.bet.DesiredBetResult.LoseMoney
import com.philocoder.martingalish.bet.DesiredBetResult.BackToBankroll
import com.philocoder.martingalish.bet.BetStrategy
import com.philocoder.martingalish.bet.DesiredBetResult
import com.philocoder.martingalish.bet.GainMoneyStrategy
import com.philocoder.martingalish.bet.LoseMoneyStrategy
import com.philocoder.martingalish.input.Inputs
import java.lang.RuntimeException

data class Strategy(val sequence: List<BetStrategy>, val odd: Double) {

    companion object {
        fun build(inputs: Inputs): Strategy {
            var i = 0
            val earningOfFirstStake = inputs.odd - 1
            val earnings = listOf(earningOfFirstStake) + inputs.gainRatios.map { earningOfFirstStake * it }
            return Strategy(
                    sequence = inputs.strategyInput.toCharArray().map {
                        when (DesiredBetResult.fromRepresentation(it)) {
                            GainMoney -> {
                                val gainMoney = GainMoneyStrategy(
                                        earning = earnings[i],
                                        gainRatio = if (i == 0) 1.0 else inputs.gainRatios[i - 1]
                                )
                                i++
                                gainMoney
                            }
                            LoseMoney -> LoseMoneyStrategy(inputs.bankrollReduceRatio.getOrElse { throw RuntimeException() })
                            BackToBankroll -> BackToBankroll
                        }
                    },
                    odd = inputs.odd)
        }
    }
}