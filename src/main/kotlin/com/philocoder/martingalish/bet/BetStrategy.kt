package com.philocoder.martingalish.bet

interface BetStrategy: Representable {
    val stakeCalculatorFn: (Double, Double) -> Double
}
