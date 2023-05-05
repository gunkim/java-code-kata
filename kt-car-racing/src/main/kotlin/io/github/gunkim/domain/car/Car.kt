package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Position
import io.github.gunkim.domain.car.vo.Name

data class Car(
    val name: Name,
    val position: Position = Position(0),
) {
    val forwardValue: Int = position.value
    val nameValue: String = name.value

    fun go() = Car(name, position + 1)
}
