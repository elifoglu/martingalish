package com.philocoder.martingalish

data class BetList(val list: List<Bet>) {

    private val bankroll = list.map { it.stake }.sum()

    fun printInfo() {
        print("""
        |Stake list: ${list.map { it.stake }}
        |Bankroll: $bankroll
        |""".trimMargin())
        list.forEach { println("Earning ratio: ${it.calculateEarningRatio(bankroll)}") } //todo: fix print
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
    }
}