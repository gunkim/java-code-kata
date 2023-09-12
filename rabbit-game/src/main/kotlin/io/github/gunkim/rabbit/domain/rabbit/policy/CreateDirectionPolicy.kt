package io.github.gunkim.rabbit.domain.rabbit.policy

import io.github.gunkim.rabbit.domain.Direction

fun interface CreateDirectionPolicy {
    fun direction(): Direction
}
