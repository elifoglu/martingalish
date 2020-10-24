package com.philocoder.martingalish

import arrow.core.None
import com.philocoder.martingalish.test_util.IsLike.isLike
import com.philocoder.martingalish.input.Inputs
import com.philocoder.martingalish.test_util.ExpectedBetList
import org.junit.jupiter.api.Test

class StrategyTest {

    @Test
    fun `test gggg`() {
        //given
        val inputs = Inputs("gggg", 3.0, arrayListOf(1.0, 1.0, 1.0), None, None)

        //when
        val betList = BetList.from(inputs)

        //then
        betList isLike ExpectedBetList(bankroll = 8.125, stakeList = arrayListOf(1.0, 1.5, 2.25, 3.375))
    }
}