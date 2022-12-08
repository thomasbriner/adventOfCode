import java.lang.Integer.min

fun day8() {
    val input = readInput(8)

    val forest = constructForest(input!!)

    calculatePropertyForAllTrees(forest, lambdaIsVisible)

    val nofVisible = forest.map {
        it.filter { tree -> tree.isVisible }.size
    }.sum()

    println(nofVisible)

    calculatePropertyForAllTrees(forest, lambdaViewScore)


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

val lambdaIsVisible =
    { left: List<Tree>, above: List<Tree>, right: List<Tree>, below: List<Tree>, tree: Tree ->
        val isVisible = left.none { it.height >= tree.height }
                || above.none { it.height >= tree.height }
                || right.none { it.height >= tree.height }
                || below.none { it.height >= tree.height }

        tree.isVisible = isVisible
    }

val lambdaViewScore =
    { left: List<Tree>, above: List<Tree>, right: List<Tree>, below: List<Tree>, tree: Tree ->
        val viewScoreLeft = calculateViewScoreForDirection(left.reversed().toMutableList(), tree.height)
        val viewScoreAbove = calculateViewScoreForDirection(above.reversed().toMutableList(), tree.height)
        val viewScoreRight = calculateViewScoreForDirection(right, tree.height)
        val viewScoreBelow = calculateViewScoreForDirection(below.toMutableList(), tree.height)

        tree.viewScore = viewScoreLeft * viewScoreAbove * viewScoreRight * viewScoreBelow

    }

fun calculatePropertyForAllTrees(
    forest: MutableList<MutableList<Tree>>,
    lambda: (List<Tree>, List<Tree>, List<Tree>, List<Tree>, Tree) -> Unit
) {
    val nofRows = forest.size
    val nofColumns = forest[0].size


    for (row in 0 until nofRows) {
        for (column in 0 until nofColumns) {

            val left = forest[row].subList(0, column)
            val above = forest.map { it[column] }.subList(0, row)
            val right = forest[row].subList(column + 1, nofColumns)
            val below = forest.map { it[column] }.subList(row + 1, nofRows)

            lambda(left, above, right, below, forest[row][column])

        }
    }

}

fun calculateViewScoreForDirection(trees: List<Tree>, height: Int): Int {
    if (trees.isEmpty()) {
        return 0
    }
    var viewScore = 1
    var treeIndex = 0
    while (treeIndex < trees.size && trees[treeIndex].height < height) {
        treeIndex++
        viewScore++
    }
    return min(viewScore, trees.size)
}


data class Tree(val height: Int, var isVisible: Boolean = false, var viewScore: Int = 0)
