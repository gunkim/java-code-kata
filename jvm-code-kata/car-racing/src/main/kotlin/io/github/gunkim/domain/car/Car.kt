package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Name
import io.github.gunkim.domain.car.vo.Position

data class Car(
    val name: Name,
    val position: Position = Position.ZERO
) {
    val forwardValue: Int = position.value
    val nameValue: String = name.value

    fun go() = Car(name, position + INCREASE_VALUE)

    companion object {
        const val INCREASE_VALUE = 1
    }
}
