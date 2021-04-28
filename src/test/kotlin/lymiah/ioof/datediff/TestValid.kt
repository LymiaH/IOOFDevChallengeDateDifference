package lymiah.ioof.datediff

import org.junit.jupiter.api.Test

class TestValid {

    @Test
    fun `Dates already in order should remain in order`() {
        val start = Date(2000, 1, 1)
        val end = Date(2001, 1, 1)
        val result = dateDiff(start, end)
        assert(result.start == start) { "Expected start to be $start but got ${result.start}" }
        assert(result.end == end) { "Expected end to be $end but got ${result.end}" }
    }

    @Test
    fun `Dates not in order should be swapped`() {
        val later = Date(2001, 1, 1)
        val earlier = Date(2000, 1, 1)
        val result = dateDiff(later, earlier)
        assert(result.start == earlier) { "Expected start to be $earlier but got ${result.start}" }
        assert(result.end == later) { "Expected end to be $later but got ${result.end}" }
    }

    @Test
    fun `Same day should be zero difference`() {
        val same = Date(2000, 1, 1)
        val result = dateDiff(same, same)
        assert(result.start == same) { "Expected start to be $same but got ${result.start}" }
        assert(result.end == same) { "Expected end to be $same but got ${result.end}" }
        assert(result.diff == 0) { "Expected diff to be 0 but got ${result.diff}" }
    }
}
