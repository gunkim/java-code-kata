package io.github.gunkim.rabbit.domain.rabbit.policy

import io.github.gunkim.rabbit.domain.Position

class CreateRandomPositionPolicy : CreatePositionPolicy {
    override fun position(): Position = Position((0..5).random())
}
