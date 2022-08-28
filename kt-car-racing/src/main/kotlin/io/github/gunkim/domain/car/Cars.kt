package io.github.gunkim.domain.car

import io.github.gunkim.domain.policy.MovePolicy

class Cars(
    list: List<Car>,
) {
    init {
        require(list.isNotEmpty()) { "자동차가 최소 1대는 존재해야 합니다." }
    }

    private val _list: MutableList<Car> = list.toMutableList()
    val list: List<Car> = list.toList()

    fun go(movePolicy: MovePolicy): Unit = _list.forEach { if (movePolicy.isMove()) it.go() }

    companion object {
        fun from(line: String, delimiters: String = ","): Cars {
            return Cars(
                line.split(delimiters)
                    .filter(String::isNotBlank)
                    .map(String::removeBlank)
                    .map(::Car)
                    .toMutableList()
            )
        }
    }
}

private fun String.removeBlank() = this.replace(" ", "")