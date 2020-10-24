package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse

data class BetList(val list: List<Bet>) {

    private val bankroll = list.map { it.stake }.sum()

    fun printInfo(actualBankroll: Option<Double>) {
        printStakeListWithBankroll()
        printRatios()
        if (actualBankroll != None) {
            println("For normalized:")
            normalize(this, actualBankroll.getOrElse { throw RuntimeException() }).printStakeListWithBankroll()
        }
    }

    private fun printStakeListWithBankroll() {
        print("""
        |Stake list: ${list.map { it.stake }}
        |Bankroll: $bankroll
        |""".trimMargin())
    }

    private fun printRatios() {
        list.forEach {println("Earning ratio: ${it.calculateEarningRatio(bankroll)}") } //todo: fix print
    }

    companion object {
        fun from(strategy: Strategy): BetList {
            val betList = arrayListOf<Bet>()
            for (desiredBetResult in strategy.sequence) {
                val stake = desiredBetResult.stakeCalculatorFn(betList.map { it.stake }.sum(), strategy.odd)
                betList.add(Bet(stake, strategy.odd, betList.map { it.stake }.sum()))
            }
            return BetList(betList)
        }

        fun normalize(betList: BetList, actualBankroll: Double): BetList {
            val normalizationRatio = actualBankroll / betList.bankroll
            return BetList(betList.list.map {
                it.copy(
                        stake = it.stake * normalizationRatio,
                        totalSpentSinceThisBet = it.totalSpentSinceThisBet * normalizationRatio)
            })
        }
    }
}