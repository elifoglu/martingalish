package com.philocoder.martingalish

import com.philocoder.martingalish.DesiredBetResult.*

data class Strategy(val sequence: List<DesiredBetResult>) {

    companion object {
        fun from(input: String) =
                Strategy(sequence = input.toCharArray().map {
                    when (it) {
                        'g' -> GainMoney
                        'l' -> LoseMoney
                        'b' -> BackToBankroll
                        else -> throw RuntimeException()
                    }
                })
    }
}