import java.lang.Integer.min

fun day8() {
    var input = readInput(8)

    val forest = constructForest(input!!)

    checkVisibility(forest)

    val nofVisible = forest.map {
        it.filter { tree -> tree.isVisible }.size
    }.sum()

    println(nofVisible)


    calculateViewScores(forest)

    val bestView = forest.map {
        it.map { tree -> tree.viewScore }.max()
    }.max()

    println(bestView)
}


fun constructForest(input: List<String>): MutableList<MutableList<Tree>> {
    val forest = mutableListOf<MutableList<Tree>>()

    input.forEach {
        val row = it.toCharArray().map { char -> char.digitToInt() }.map { height -> Tree(height) }.toMutableList()
        forest.add(row)
    }

    return forest
}

fun checkVisibility(forest: MutableList<MutableList<Tree>>) {
    val nofRows = forest.size
    val nofColumns = forest[0].size


    for (row in 0..nofRows - 1) {
        for (column in 0..nofColumns - 1) {
            val height = forest[row][column].height

            val left = forest[row].subList(0, column)
            val above = forest.map { it[column] }.subList(0, row)
            val right = forest[row].subList(column + 1, nofColumns)
            val below = forest.map { it[column] }.subList(row + 1, nofRows)

            val isVisible = left.none { it.height >= height }
                    || above.none { it.height >= height }
                    || right.none { it.height >= height }
                    || below.none { it.height >= height }

            forest[row][column].isVisible = isVisible

        }
    }

}


fun calculateViewScores(forest: MutableList<MutableList<Tree>>) {
    val nofRows = forest.size
    val nofColumns = forest[0].size


    for (row in 0..nofRows - 1) {
        for (column in 0..nofColumns - 1) {
            val height = forest[row][column].height

            val left = forest[row].subList(0, column)
            val above = forest.map { it[column] }.subList(0, row)
            val right = forest[row].subList(column + 1, nofColumns)
            val below = forest.map { it[column] }.subList(row + 1, nofRows)

            val viewScoreLeft = calculateViewScoreForDirection(left.reversed().toMutableList(), height)
            val viewScoreAbove = calculateViewScoreForDirection(above.reversed().toMutableList(), height)
            val viewScoreRight = calculateViewScoreForDirection(right, height)
            val viewScoreBelow = calculateViewScoreForDirection(below.toMutableList(), height)

            forest[row][column].viewScore = viewScoreLeft * viewScoreAbove * viewScoreRight * viewScoreBelow

        }
    }

}

fun calculateViewScoreForDirection(trees: MutableList<Tree>, height: Int): Int {
    if (trees.size == 0){
        return 0
    }
    var viewScore = 1
    var treeIndex = 0
    while (treeIndex < trees.size && trees[treeIndex].height < height) {
        treeIndex++
        viewScore++
    }
    return min(viewScore,trees.size)
}


data class Tree(val height: Int, var isVisible: Boolean = false, var viewScore: Int = 0)
