import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day8KtTest {
    @Test
    internal fun testViewScoreInner() {
        val trees = mutableListOf(1, 4, 3, 5, 6).map { Tree(it) }
        val viewScore = calculateViewScoreForDirection(trees, 4)
        assertThat(viewScore).isEqualTo(2)
    }

    @Test
    internal fun testViewScoreBorder() {
        val trees = mutableListOf<Tree>()
        val viewScore = calculateViewScoreForDirection(trees, 4)
        assertThat(viewScore).isEqualTo(0)
    }


    @Test
    internal fun testViewScoreBlockedByFirstTree() {
        val trees = mutableListOf(9, 4, 3, 5, 6).map { Tree(it) }
        val viewScore = calculateViewScoreForDirection(trees, 4)
        assertThat(viewScore).isEqualTo(1)
    }

    @Test
    internal fun testViewScoreSeeTilEnd() {
        val trees = mutableListOf(3, 4, 3, 5, 6).map { Tree(it) }
        val viewScore = calculateViewScoreForDirection(trees, 9)
        assertThat(viewScore).isEqualTo(5)
    }


}

