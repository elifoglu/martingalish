package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some
import com.philocoder.martingalish.test_util.IsLike.isLike
import com.philocoder.martingalish.input.Inputs
import com.philocoder.martingalish.test_util.ExpectedBetList
import org.junit.jupiter.api.Test

class StrategyTest {

    @Test
    fun `3,0g1,0g1,0g1,0g`() {
        //given
        val inputs = Inputs("gggg", 3.0, arrayListOf(1.0, 1.0, 1.0), None, None)

        //when
        val betList = BetList.from(inputs).printInfo(inputs.actualBankroll)
        //val betList = BetList.from(inputs)

        //then
        betList isLike ExpectedBetList(bankroll = 8.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 3.375))
    }

    @Test
    fun `3,0g1,0g1,0g1,0b`() {
        //given
        val inputs = Inputs("gggb", 3.0, arrayListOf(1.0, 1.0), None, None)

        //when
        val betList = BetList.from(inputs).printInfo(inputs.actualBankroll)
        //val betList = BetList.from(inputs)

        //then
        betList isLike ExpectedBetList(bankroll = 7.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 2.375))
    }

    @Test
    fun `test 3,0g1,0g1,0g1,0l`() {
        //given
        val inputs = Inputs("gggl", 3.0, arrayListOf(1.0, 1.0), Some(0.5), None)

        //when
        val betList = BetList.from(inputs).printInfo(inputs.actualBankroll)
        //val betList = BetList.from(inputs)

        //then
        betList isLike ExpectedBetList(bankroll = 5.7, stakeList = arrayListOf(1.0, 1.5, 2.25, 0.95))
    }
}