package io.github.gunkim.rabbit.domain.rabbit.policy

import io.github.gunkim.rabbit.domain.Direction

class CreateRandomDirectionPolicy : CreateDirectionPolicy {
    override fun direction() = Direction.random()
}
