package com.philocoder.martingalish

import com.philocoder.martingalish.EarningType.BackToBankroll
import com.philocoder.martingalish.EarningType.Earning

data class Strategy(val sequence: List<EarningType>) {

    companion object {
        fun from(input: String) =
                Strategy(sequence = input.toCharArray().map {
                    when (it) {
                        'g' -> Earning
                        else -> BackToBankroll
                    }
                })
    }
}