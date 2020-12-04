package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some
import com.philocoder.martingalish.input.Inputs
import com.philocoder.martingalish.test_util.ExpectedBetList
import com.philocoder.martingalish.test_util.IsLike.isLike
import org.junit.jupiter.api.Test

class MartingalishStrategyTest {

    @Test
    fun `3,0-g1,0g1,0g1,0g`() {
        //given
        val inputs = Inputs("gggg", 3.0, arrayListOf(1.0, 1.0, 1.0), None, None)

        //when
        val strategy = MartingalishStrategy.from(inputs)
        val betList = BetList.of(strategy).printInfo(inputs.actualBankroll)

        //then
        betList isLike ExpectedBetList(bankroll = 8.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 3.375))
    }

    @Test
    fun `3,0-g1,0g1,0g1,0b`() {
        //given
        val inputs = Inputs("gggb", 3.0, arrayListOf(1.0, 1.0), None, None)

        //when
        val strategy = MartingalishStrategy.from(inputs)
        val betList = BetList.of(strategy).printInfo(inputs.actualBankroll)

        //then
        betList isLike ExpectedBetList(bankroll = 7.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 2.375))
    }

    @Test
    fun `3,0-g1,0g1,0g1,0l-1,0`() {
        //given
        val inputs = Inputs("gggl", 3.0, arrayListOf(1.0, 1.0), Some(1.0), None)

        //when
        val strategy = MartingalishStrategy.from(inputs)
        val betList = BetList.of(strategy).printInfo(inputs.actualBankroll)

        //then
        betList isLike ExpectedBetList(bankroll = 6.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 1.375))
    }

    @Test
    fun `3,0-g1,0g1,0lg1,0l-1,0`() {
        //given
        val inputs = Inputs("gglgl", 3.0, arrayListOf(1.0, 1.0), Some(1.0), None)

        //when
        val strategy = MartingalishStrategy.from(inputs)
        val betList = BetList.of(strategy).printInfo(inputs.actualBankroll)

        //then
        betList isLike ExpectedBetList(bankroll = 6.688, stakeList = arrayListOf(1.0, 1.5, 0.25, 2.375, 1.562))
    }
}