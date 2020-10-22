package com.philocoder.martingalish

data class Strategy(val sequence: List<DesiredBetResult>) {

    fun containsDesiredBetResult(result: DesiredBetResult): Boolean = sequence.contains(result)

    companion object {
        fun from(input: String) = Strategy(input.toCharArray().map { DesiredBetResult.fromRepresentation(it) })
    }
}