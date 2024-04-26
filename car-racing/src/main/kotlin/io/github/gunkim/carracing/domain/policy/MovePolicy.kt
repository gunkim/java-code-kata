package io.github.gunkim.carracing.domain.policy

fun interface MovePolicy {
    fun isMove(): Boolean
}
