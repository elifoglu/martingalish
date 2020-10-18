package com.philocoder.martingalish

import com.philocoder.martingalish.DesiredBetResult.*

data class Strategy(val sequence: List<DesiredBetResult>) {

    fun containsDesiredBetResult(result: DesiredBetResult): Boolean =
        sequence.contains(result)

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