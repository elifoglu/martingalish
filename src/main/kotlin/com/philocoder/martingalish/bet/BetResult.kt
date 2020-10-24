package com.philocoder.martingalish.bet

sealed class BetResult(val representation: Char) {

    object GainMoney : BetResult(representation = 'g')
    object LoseMoney : BetResult(representation = 'l')
    object BackToBankroll : BetResult(representation = 'b')

    companion object {
        fun fromRepresentation(representation: Char): BetResult =
                when (representation) {
                    GainMoney.representation -> GainMoney
                    LoseMoney.representation -> LoseMoney
                    BackToBankroll.representation -> BackToBankroll
                    else -> throw RuntimeException()
                }
    }
}