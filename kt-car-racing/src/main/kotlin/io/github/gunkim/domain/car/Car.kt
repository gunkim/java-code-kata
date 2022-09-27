package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Forward
import io.github.gunkim.domain.car.vo.Name

data class Car(
    val name: Name,
    val forward: Forward = Forward(0),
) {
    val forwardValue: Int = forward.value
    val nameValue: String = name.value

    fun go(): Car = Car(name, forward + 1)
}