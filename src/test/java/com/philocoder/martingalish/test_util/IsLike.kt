package com.philocoder.martingalish.test_util

import com.philocoder.martingalish.BetList
import org.assertj.core.api.Assertions.assertThat

object IsLike {

    infix fun BetList.`isLike`(expected: ExpectedBetList) {
        assertThat(this.bankroll).isEqualTo(expected.bankroll)
        assertThat(this.list.map { it.stake }).isEqualTo(expected.stakeList)
    }
}