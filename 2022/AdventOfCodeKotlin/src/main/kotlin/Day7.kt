fun day7() {
    var input = readInput(7)

    input = input!!.subList(1, input.size)
    val root = Directory("/")
    var act = root
    input.forEach {
        if (it.startsWith("$ cd")) {
            val name = it.substring(5)
            if (name == "..") {
                act = act.parent!!
            } else {
                act.subDirs.add(Directory(name, act))
                act = act.subDirs.single { it.name == name }
            }
        } else if (it.matches(Regex("[0-9]" + ".*"))
        ) {
            act.files.add(fileFromString(it))
        }
    }

    calculateSize(root)

    val dirs = flattenDirectories(root)
    val smallSum = dirs.filter { it.size!! < 100000 }.map { it.size!! }.sum()


    println(smallSum)
}


fun flattenDirectories(act: Directory): MutableList<Directory> {
    val flatDirs = mutableListOf<Directory>()
    act.subDirs.forEach {
        flatDirs.addAll(flattenDirectories(it))
    }
    flatDirs.add(act)
    return flatDirs
}

fun calculateSize(act: Directory): Int {
    var size = 0
    val flatDirs = mutableListOf<Directory>()
    act.subDirs.forEach {
        size += calculateSize(it)
    }
    size += act.files.map { it.size }.sum()
    act.size = size

    return size
}


data class Directory(
    val name: String,
    val parent: Directory? = null,
    val files: MutableList<AFile> = mutableListOf(),
    val subDirs: MutableList<Directory> = mutableListOf(),
    var size: Int? = null
)

fun fileFromString(inputLine: String): AFile {
    val regex = """([0-9]*)\s*([a-z.]*)""".toRegex()
    val matchResult = regex.find(inputLine)
    val (size, name) = matchResult!!.destructured
    return AFile(name, size.toInt())
}

data class AFile(val name: String, val size: Int)
