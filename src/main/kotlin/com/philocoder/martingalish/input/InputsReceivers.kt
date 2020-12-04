package com.philocoder.martingalish.input

import arrow.core.None
import arrow.core.Some
import com.philocoder.martingalish.bet.BetResult.GainMoney
import com.philocoder.martingalish.bet.BetResult.LoseMoney

object InputsReceivers {

    val byKeyReceiver: (String) -> Inputs = { key ->
        val actualBankroll = key.split('/').run {
            if (size == 1) None else Some(get(1).toDouble())
        }
        val keyWithoutBankroll = key.split('/')[0]
        val odd = keyWithoutBankroll.split('-')[0].toDouble()
        val keyWithoutBankrollAndOdd = keyWithoutBankroll.dropWhile { it != '-' }.drop(2)
        val lossRatio = keyWithoutBankrollAndOdd.split('-').run {
            if (size == 1) None else Some(get(1).toDouble())
        }
        var coreKey = if (lossRatio.isEmpty()) keyWithoutBankrollAndOdd else keyWithoutBankrollAndOdd.dropLastWhile { it != '-' }.dropLast(1)
        var strategy = "${GainMoney.representation}"
        val gainRatios = arrayListOf<Double>()
        while (coreKey.isNotEmpty()) {
            if (coreKey[0] == LoseMoney.representation) {
                strategy += LoseMoney.representation
                coreKey = coreKey.drop(1)
            } else {
                strategy += GainMoney.representation
                gainRatios.add(coreKey.split(GainMoney.representation)[0].toDouble())
                coreKey = coreKey.dropWhile { it != GainMoney.representation }.drop(1)
            }
        }

        Inputs(strategy = strategy,
                odd = odd,
                gainRatios = gainRatios,
                lossRatio = lossRatio,
                actualBankroll = actualBankroll)
    }
    val byNavigationReceiver: () -> Inputs = {
        var strategyInput: String
        do {
            print("Enter your martingalish strategy (e.g: gggb or ggbl): ")
            strategyInput = readLine()!!
        } while (!InputValidator.isValidStrategyInput(strategyInput))

        var odd: Double
        do {
            print("Enter odd: ")
            odd = readLine()!!.toDouble()
        } while (!InputValidator.isValidOdd(odd))

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

        val lossRatio = if (strategyInput.contains(LoseMoney.representation)) {
            print("Enter loss ratio: ")
            Some(readLine()!!.toDouble())
        } else None

        print("Enter actual bankroll (press enter to skip): ")
        val actualBankroll = readLine()!!.let {
            if (it.isNotEmpty()) Some(it.toDouble()) else None
        }

        Inputs(
                strategy = strategyInput,
                odd = odd,
                gainRatios = gainRatios,
                lossRatio = lossRatio,
                actualBankroll = actualBankroll)
    }
}