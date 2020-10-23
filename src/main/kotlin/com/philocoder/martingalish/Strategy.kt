package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.DesiredBetResult.GainMoney
import com.philocoder.martingalish.DesiredBetResult.LoseMoney
import com.philocoder.martingalish.DesiredBetResult.BackToBankroll
import com.philocoder.martingalish.input.Inputs
import java.lang.RuntimeException

data class Strategy(val sequence: List<DesiredBetResult>, val odd: Double) {

    companion object {
        fun build(inputs: Inputs): Strategy {
            var i = 0
            val earningOfFirstStake = inputs.odd - 1
            val earnings = listOf(earningOfFirstStake) + inputs.gainRatios.map { earningOfFirstStake * it }
            return Strategy(
                    sequence = inputs.strategyInput.toCharArray().map {
                        when (it) {
                            GainMoney.representation -> {
                                val gainMoney = GainMoney(earnings[i])
                                i++
                                gainMoney
                            }
                            LoseMoney.representation -> {
                                LoseMoney(inputs.bankrollReduceRatio.getOrElse { throw RuntimeException() })
                            }
                            BackToBankroll.representation -> BackToBankroll
                            else -> throw RuntimeException()
                        }
                    },
                    odd = inputs.odd)
        }
    }
}