package com.philocoder.martingalish.bet

data class Bet(val betStrategy: BetStrategy,
               val stake: Double,
               val odd: Double,
               val totalSpentSinceThisBet: Double) {

    fun calculateEarningRatio(bankroll: Double) = (bankroll + (odd * stake - totalSpentSinceThisBet - stake)) / bankroll
}
