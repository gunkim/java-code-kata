package io.github.gunkim.rabbit.domain.rabbit.policy

import io.github.gunkim.rabbit.domain.Position

fun interface CreatePositionPolicy {
    fun position(): Position
}
