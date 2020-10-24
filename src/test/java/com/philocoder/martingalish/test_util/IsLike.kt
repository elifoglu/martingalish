package com.philocoder.martingalish.test_util

import com.philocoder.martingalish.BetList
import com.philocoder.martingalish.util.round
import org.assertj.core.api.Assertions.assertThat

object IsLike {

    infix fun BetList.`isLike`(expected: ExpectedBetList) {
        assertThat(this.bankroll.round(3)).isEqualTo(expected.bankroll)
        assertThat(this.list.map { it.stake.round(3) }).isEqualTo(expected.stakeList)
    }
}