package io.github.gunkim

fun quickSort(array: IntArray): IntArray {
    if (array.size <= 1) {
        return array
    }

    val pivot = array[array.size / 2]
    val smaller = array.filter { it < pivot }.toIntArray()
    val greater = array.filter { it > pivot }.toIntArray()

    return quickSort(smaller) + intArrayOf(pivot) + quickSort(greater)
}

fun evenNumberFirstSort(array: IntArray): IntArray {
    val evenNumbersFilter: (Int) -> Boolean = { it % 2 == 0 }

    return array.filter(evenNumbersFilter).sorted().toIntArray() +
            array.filterNot(evenNumbersFilter).sorted().toIntArray()
}

enum class Color(val order: Int) {
    RED(1), WHITE(2), BLUE(3)
}

fun dutchFlagPartition(colors: List<Color>): List<Color> {
    return colors.sortedBy { it.order }
}

fun plusOne(numbers: List<Int>): List<Int> {
    val result = numbers.joinToString("") { it.toString() }.toInt() + 1

    return result.toString()
        .split("").drop(1).dropLast(1)
        .map(String::toInt)
}
