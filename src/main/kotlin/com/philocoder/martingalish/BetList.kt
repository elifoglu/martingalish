package com.philocoder.martingalish

import arrow.core.Option
import arrow.core.getOrElse
import com.philocoder.martingalish.bet.Bet
import com.philocoder.martingalish.bet.BetResult.GainMoney
import com.philocoder.martingalish.bet.BetResult.LoseMoney
import com.philocoder.martingalish.bet.BetStrategy.*
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
            when (it.betStrategy) {
                is GainMoneyStrategy -> print("${it.betStrategy.gainRatio.round(3)}${GainMoney.representation}")
                is LoseMoneyStrategy -> print(LoseMoney.representation)
                is BackToBankrollStrategy -> print(BackToBankrollStrategy.betResult.representation)
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
            list.forEach { println("Ratio for '${it.betStrategy.betResult.representation}': ${it.calculateEarningRatio(bankroll).round(3)}") }

    companion object {
        fun of(strategy: MartingalishStrategy): BetList {
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