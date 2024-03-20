package io.github.gunkim.domain.car

import io.github.gunkim.domain.policy.MovePolicy

data class Cars(
    val list: List<Car>
) {
    init {
        require(list.isNotEmpty()) { "자동차가 최소 1대는 존재해야 합니다." }
    }

    fun go(movePolicy: MovePolicy) = Cars(list.map { moveAndStay(it, movePolicy) })

    private fun moveAndStay(car: Car, movePolicy: MovePolicy) = if (movePolicy.isMove()) {
        car.go()
    } else {
        car
    }
}
