fun readInput(day: Int): List<String>? {
    val fileName = "input_day${day}.txt"
    return object {}.javaClass.getResourceAsStream(fileName)?.bufferedReader()?.readLines()

}
