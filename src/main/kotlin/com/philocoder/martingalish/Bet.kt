package com.philocoder.martingalish

data class Bet(val desiredResult: DesiredBetResult,
               val stake: Double,
               val odd: Double,
               val totalSpentSinceThisBet: Double
) {
    fun calculateEarningRatio(bankroll: Double) = (bankroll + (odd * stake - totalSpentSinceThisBet - stake)) / bankroll
}
