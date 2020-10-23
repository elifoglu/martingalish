package com.philocoder.martingalish.input

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.philocoder.martingalish.DesiredBetResult
import com.philocoder.martingalish.DesiredBetResult.GainMoney

data class Inputs(val strategyInput: String,
                  val odd: Double,
                  val gainRatios: List<Double>,
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

            val gainRatios = arrayListOf<Double>()
            val gainMoneyRepresentations = strategyInput.toCharArray().filter { it == GainMoney.representation }
            if (gainMoneyRepresentations.size > 1) {
                println("Enter gain ratio for each '${GainMoney.representation}'...")
                gainMoneyRepresentations.drop(1)
                        .forEachIndexed { i, _ ->
                            print("Enter for ${i + 2}. '${GainMoney.representation}': ")
                            gainRatios.add(readLine()!!.toDouble())
                        }
            }

            val bankrollReduceRatio = if (strategyInput.contains(DesiredBetResult.LoseMoney.representation)) {
                print("Enter bankroll reduce ratio: ")
                Some(readLine()!!.toDouble())
            } else None

            return Inputs(
                    strategyInput = strategyInput,
                    odd = odd,
                    gainRatios = gainRatios,
                    bankrollReduceRatio = bankrollReduceRatio)
        }
    }
}