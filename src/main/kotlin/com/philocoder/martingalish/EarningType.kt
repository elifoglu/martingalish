package com.philocoder.martingalish

sealed class EarningType(val stakeCalculatorFn: (Double, Double) -> Double) {
    object Earning : EarningType({ totalSpentUntilNow, fixedEarning -> (totalSpentUntilNow + fixedEarning) / fixedEarning })
    object BackToBankroll : EarningType({ totalSpentUntilNow, fixedEarning -> totalSpentUntilNow / fixedEarning })
}
