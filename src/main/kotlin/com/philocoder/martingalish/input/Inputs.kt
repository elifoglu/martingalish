package com.philocoder.martingalish.input

import arrow.core.Option
import com.philocoder.martingalish.input.InputsReceivers.byKeyReceiver
import com.philocoder.martingalish.input.InputsReceivers.byNavigationReceiver

data class Inputs(val strategy: String,
                  val odd: Double,
                  val gainRatios: List<Double>,
                  val lossRatio: Option<Double>,
                  val actualBankroll: Option<Double>) {

    companion object {
        fun receive(): Inputs {
            print("""
                |(1) By key
                |(2) By navigation
                |Input type: """.trimMargin())
            return when (readLine()!!) {
                "1" -> {
                    print("Enter your martingalish key (e.g: 3.0-g0.75gl-1.0/1000): ")
                    byKeyReceiver(readLine()!!)
                }
                "2" -> byNavigationReceiver()
                else -> throw RuntimeException()
            }
        }
    }
}