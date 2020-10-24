package com.philocoder.martingalish

import arrow.core.getOrElse
import com.philocoder.martingalish.bet.BetResult
import com.philocoder.martingalish.bet.BetResult.*
import com.philocoder.martingalish.bet.BetStrategy
import com.philocoder.martingalish.bet.BetStrategy.*
import com.philocoder.martingalish.input.Inputs

data class MartingalishStrategy(val sequence: List<BetStrategy>, val odd: Double) {

    companion object {
        fun from(inputs: Inputs): MartingalishStrategy {
            var i = 0
            val earningOfFirstStake = inputs.odd - 1
            val earnings = listOf(earningOfFirstStake) + inputs.gainRatios.map { earningOfFirstStake * it }
            val loss = inputs.lossRatio.map { it * earningOfFirstStake }
            return MartingalishStrategy(
                    sequence = inputs.strategyInput.toCharArray().map {
                        when (BetResult.fromRepresentation(it)) {
                            GainMoney -> {
                                val gainMoney = GainMoneyStrategy(
                                        earning = earnings[i],
                                        gainRatio = if (i == 0) 1.0 else inputs.gainRatios[i - 1]
                                )
                                i++
                                gainMoney
                            }
                            LoseMoney -> LoseMoneyStrategy(
                                    loss.getOrElse { throw RuntimeException() },
                                    inputs.lossRatio.getOrElse { throw RuntimeException() })
                            BackToBankroll -> BackToBankrollStrategy
                        }
                    },
                    odd = inputs.odd)
        }
    }
}