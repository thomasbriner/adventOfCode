fun day9_2() {
    val input = readInput(9)
    val tailPositions = mutableSetOf<VisitedPosition>()

    val rope = Rope()
    println(rope)
    input!!.forEach { move ->
        val split = move.split(' ')
        val direction = directionFromSingleLetter(split[0])
        val count = split[1].toInt()
        (1..count).forEach {
            println("Direction ${direction}")
            tailPositions.add(rope.move(direction))
            println(rope)
        }
    }

    // 08:30
    println(tailPositions.size)
}

class Rope() {
    var knots: MutableList<Knot> = mutableListOf()

    init {
        (0..9).forEach {
            knots.add(Knot())
        }
    }

    fun move(direction: Direction): VisitedPosition {
        knots[0].moveHead(direction)
        (1..knots.size - 1).forEach {
            knots[it].followKnot(knots[it - 1])
        }
        return knots[knots.size - 1].getVisitedPosition()
    }

    override fun toString(): String {
        return "Rope(knots=$knots)"
    }
}


class Knot(
    var x: Int = 0,
    var y: Int = 0
) {

    fun moveHead(direction: Direction) {
        when (direction) {
            Direction.LEFT -> x--
            Direction.UP -> y++
            Direction.RIGHT -> x++
            Direction.DOWN -> y--
        }
    }

    fun followKnot(previous: Knot) {
        if (previous.x in (x - 1..x + 1)
            && previous.y in (y - 1..y + 1)
        ) {
            // dont move
        } else if (previous.x in (x - 1..x + 1)) {
            // move in y
            if (previous.y > y) {
                y++
            } else {
                y--
            }
            if (previous.x != x) {
                // move in both directions
                if (previous.x > x) {
                    x++
                } else {
                    x--
                }
            }
        } else if (previous.y in (y - 1..y + 1)) {
            // move in x
            if (previous.x > x) {
                x++
            } else {
                x--
            }
            if (previous.y != y) {
                // move in both directions
                if (previous.y > y) {
                    y++
                } else {
                    y--
                }
            }
        } else {
            // diagonal 2 steps away
            if (previous.x > x) {
                x++
            } else if (previous.x < x) {
                x--
            }

            if (previous.y > y) {
                y++
            } else if (previous.y < y) {
                y--
            }
//            throw IllegalStateException("peng")
        }

    }

    fun getVisitedPosition(): VisitedPosition {
        return VisitedPosition(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Knot

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "($x,$y)"
    }
}
