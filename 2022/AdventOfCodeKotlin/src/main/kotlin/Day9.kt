fun day9() {
    val input = readInput(9)
    val tailPositions = mutableSetOf<VisitedPosition>()
    // geoeffnet: 06:11
    // input heruntergeladen: 06:22


    // start auf 0/0 fuer H und T
    val constellation = Constellation()

    input!!.forEach { move ->
        val split = move.split(' ')
        val direction = directionFromSingleLetter(split[0])
        val count = split[1].toInt()
        (1..count).forEach {
            println("Constellation ${constellation}")
            println("Direction ${direction}")
            constellation.moveHead(direction)
            constellation.followWithTail()
            println("Constellation ${constellation}")
            // bei jeder Bewegung von T: VisitedPosition in Set hinzufuegen
            tailPositions.add(constellation.getVisitedPosition())
        }
    }

    println(tailPositions.size)
}


data class VisitedPosition(val x: Int, val y: Int)

class Constellation(
    var headX: Int = 0,
    var headY: Int = 0,
    var tailX: Int = 0,
    var tailY: Int = 0
) {

    fun moveHead(direction: Direction) {
        when (direction) {
            Direction.LEFT -> headX--
            Direction.UP -> headY++
            Direction.RIGHT -> headX++
            Direction.DOWN -> headY--
        }
    }

    fun followWithTail() {
        if (headX in (tailX - 1..tailX + 1)
            && headY in (tailY - 1..tailY + 1)
        ) {
            // dont move
        } else if (headX in (tailX - 1..tailX + 1)) {
            // move in y
            if (headY > tailY) {
                tailY++
            } else {
                tailY--
            }
            if (headX != tailX) {
                // move in both directions
                if (headX > tailX) {
                    tailX++
                } else {
                    tailX--
                }
            }
        } else if (headY in (tailY - 1..tailY + 1)) {
            // move in x
            if (headX > tailX) {
                tailX++
            } else {
                tailX--
            }
            if (headY != tailY) {
                // move in both directions
                if (headY > tailY) {
                    tailY++
                } else {
                    tailY--
                }
            }
        }


    }

    fun getVisitedPosition(): VisitedPosition {
        return VisitedPosition(tailX, tailY)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Constellation

        if (headX != other.headX) return false
        if (headY != other.headY) return false
        if (tailX != other.tailX) return false
        if (tailY != other.tailY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = headX
        result = 31 * result + headY
        result = 31 * result + tailX
        result = 31 * result + tailY
        return result
    }

    override fun toString(): String {
        return "Constellation(head=($headX,$headY), tail=($tailX,$tailY)"
    }


}

enum class Direction {
    LEFT, UP, RIGHT, DOWN
}

fun directionFromSingleLetter(letter: String): Direction {
    when (letter) {
        "L" -> return Direction.LEFT
        "U" -> return Direction.UP
        "R" -> return Direction.RIGHT
        "D" -> return Direction.DOWN
    }
    return Direction.RIGHT
}
