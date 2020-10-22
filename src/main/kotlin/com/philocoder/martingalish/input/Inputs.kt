package com.philocoder.martingalish.input

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.philocoder.martingalish.DesiredBetResult
import com.philocoder.martingalish.Strategy

data class Inputs(val strategyInput: String,
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

            val bankrollReduceRatio = if (Strategy.from(strategyInput).containsDesiredBetResult(DesiredBetResult.LoseMoney)) {
                print("Enter bankroll reduce ratio: ")
                Some(readLine()!!.toDouble())
            } else None

            return Inputs(strategyInput, odd, bankrollReduceRatio)
        }
    }
}