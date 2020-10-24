package com.philocoder.martingalish

import com.philocoder.martingalish.input.Inputs

fun main() {
    do {
        val inputs: Inputs = Inputs.receive()
        BetList.from(inputs).printInfo(inputs.actualBankroll)
        print("Press enter to restart, or write anything to quit...")
    } while (readLine()!!.isEmpty())
}