import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class ConstellationTest {

    @Test
    internal fun tailDoesNotMove() {
        val constellation = Constellation(0, 0, 0, 0)
        constellation.followWithTail()

        val expected = Constellation(0, 0, 0, 0)
        Assertions.assertThat(constellation).isEqualTo(expected)
    }

    @ParameterizedTest(name = "{index} => start constellation:{0} => expected:{1}")
    @MethodSource("createTestInput")
    fun testRomanNumberConverter(constellation: Constellation, expected: Constellation) {
        constellation.followWithTail()
        Assertions.assertThat(constellation).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        private fun createTestInput(): Stream<Arguments?>? {
            return Stream.of(
                // same spot
                Arguments.of(Constellation(0, 0, 0, 0), Constellation(0, 0, 0, 0)),
                // off by one, same row -> dont move
                Arguments.of(Constellation(1, 0, 0, 0), Constellation(1, 0, 0, 0)),
                Arguments.of(Constellation(0, 1, 0, 0), Constellation(0, 1, 0, 0)),
                Arguments.of(Constellation(-1, 0, 0, 0), Constellation(-1, 0, 0, 0)),
                Arguments.of(Constellation(0, -1, 0, 0), Constellation(0, -1, 0, 0)),
                // off by more, same row -> move
                Arguments.of(Constellation(2, 0, 0, 0), Constellation(2, 0, 1, 0)),
                Arguments.of(Constellation(0, 2, 0, 0), Constellation(0, 2, 0, 1)),
                Arguments.of(Constellation(-2, 0, 0, 0), Constellation(-2, 0, -1, 0)),
                Arguments.of(Constellation(0, -2, 0, 0), Constellation(0, -2, 0, -1)),
                // off by one, diagonal -> dont move
                Arguments.of(Constellation(1, 1, 0, 0), Constellation(1, 1, 0, 0)),
                Arguments.of(Constellation(-1, 1, 0, 0), Constellation(-1, 1, 0, 0)),
                Arguments.of(Constellation(-1, -1, 0, 0), Constellation(-1, -1, 0, 0)),
                Arguments.of(Constellation(1, -1, 0, 0), Constellation(1, -1, 0, 0)),
                // off by more, diagonal -> move
                Arguments.of(Constellation(2, 1, 0, 0), Constellation(2, 1, 1, 1)),
                Arguments.of(Constellation(-1, 2, 0, 0), Constellation(-1, 2, -1, 1)),
                Arguments.of(Constellation(-2, -1, 0, 0), Constellation(-2, -1, -1, -1)),
                Arguments.of(Constellation(1, -2, 0, 0), Constellation(1, -2, 1, -1)),


            )
        }
    }

}
