package com.philocoder.martingalish

import arrow.core.Option
import arrow.core.getOrElse
import com.philocoder.martingalish.bet.Bet
import com.philocoder.martingalish.bet.DesiredBetResult
import com.philocoder.martingalish.bet.DesiredBetResult.*
import com.philocoder.martingalish.bet.GainMoneyStrategy
import com.philocoder.martingalish.bet.LoseMoneyStrategy
import com.philocoder.martingalish.input.Inputs
import com.philocoder.martingalish.util.round

data class BetList(val list: List<Bet>) {

    val bankroll = list.map { it.stake }.sum()

    fun printInfo(actualBankroll: Option<Double>): BetList {
        printMartingalishKey()
        printStakeListWithBankroll()
        println()
        if (actualBankroll.nonEmpty()) {
            normalize(this, actualBankroll.getOrElse { throw RuntimeException() }).printStakeListWithBankroll()
            println(" (normalized)")
        }
        printRatios()
        return this
    }

    private fun printMartingalishKey() {
        print("Martingalish key: ")
        print("${list[0].odd}-g")
        list.drop(n = 1).forEach {
            when (DesiredBetResult.fromRepresentation(it.betStrategy.representation)) {
                GainMoney -> print("${(it.betStrategy as GainMoneyStrategy).gainRatio.round(3)}${GainMoney.representation}")
                LoseMoney -> print(LoseMoney.representation)
                BackToBankroll -> print(BackToBankroll.representation)
            }
        }
        if (list.any { it.betStrategy is LoseMoneyStrategy }) {
            print("-${(list.first { it.betStrategy is LoseMoneyStrategy }.betStrategy as LoseMoneyStrategy).lossRatio}")
        }
        println()
    }

    private fun printStakeListWithBankroll() =
            print("Stake list: ${list.map { it.stake.round(3) }} - Bankroll: ${bankroll.round(3)}")

    private fun printRatios() =
            list.forEach { println("Ratio for '${it.betStrategy.representation}': ${it.calculateEarningRatio(bankroll).round(3)}") }

    companion object {
        fun from(inputs: Inputs): BetList {
            val strategy = Strategy.build(inputs)
            val betList = arrayListOf<Bet>()
            for (betStrategy in strategy.sequence) {
                val stake = betStrategy.stakeCalculatorFn(betList.map { it.stake }.sum(), strategy.odd)
                betList.add(Bet(
                        betStrategy = betStrategy,
                        stake = stake,
                        odd = strategy.odd,
                        totalSpentSinceThisBet = betList.map { it.stake }.sum()
                ))
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