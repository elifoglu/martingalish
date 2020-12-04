package com.philocoder.martingalish

import arrow.core.None
import arrow.core.Some
import com.philocoder.martingalish.input.Inputs
import com.philocoder.martingalish.input.InputsReceivers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InputsReceiversTest {

    @Test
    fun `test byKeyReceiver with key 3,0-g0,75gl-1,0-1000`() {
        //when
        val inputs = InputsReceivers.byKeyReceiver("3.0-g0.75gl-1.0/1000")
        assertThat(inputs).isEqualTo(Inputs("ggl", 3.0, listOf(0.75), Some(1.0), Some(1000.0)))
    }

    @Test
    fun `test byKeyReceiver with key 3,0-g0,75gg-1,0-1000`() {
        //when
        val inputs = InputsReceivers.byKeyReceiver("3.0-g0.75g0.50g/1000")
        assertThat(inputs).isEqualTo(Inputs("ggg", 3.0, listOf(0.75, 0.50), None, Some(1000.0)))
    }
}