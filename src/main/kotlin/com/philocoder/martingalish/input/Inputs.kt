package com.philocoder.martingalish.input

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.philocoder.martingalish.DesiredBetResult
import com.philocoder.martingalish.DesiredBetResult.GainMoney
import com.philocoder.martingalish.Strategy

data class Inputs(val strategy: Strategy,
                  val odd: Double,
                  val bankrollReduceRatio: Option<Double>) {

    companion object {
        fun receive(): Inputs {
            var strategyInput: String
            do {
                print("Enter your martingalish strategy (e.g: gggb or ggbl): ")
                strategyInput = readLine()!!
                val validInput = StrategyInputValidator.isValid(strategyInput)
            } while (!validInput)

            print("Enter odd: ")
            val odd = readLine()!!.toDouble()

            val strategy = Strategy.from(strategyInput)

            val gainRatios = arrayListOf(odd - 1)
            val gainMoneyDesiredBetResults = strategy.sequence.filter { it is GainMoney }
            if (gainMoneyDesiredBetResults.size > 1) {
                println("Enter gain ratio for each '${GainMoney.representation}'...")
                gainMoneyDesiredBetResults
                        .drop(1)
                        .forEachIndexed { i, it ->
                            print("Enter for ${i + 2}. '${GainMoney.representation}': ")
                            gainRatios.add(readLine()!!.toDouble())
                        }
            }

            val bankrollReduceRatio = if (strategy.containsDesiredBetResult(DesiredBetResult.LoseMoney)) {
                print("Enter bankroll reduce ratio: ")
                Some(readLine()!!.toDouble())
            } else None

            return Inputs(strategy, odd, bankrollReduceRatio)
        }
    }
}