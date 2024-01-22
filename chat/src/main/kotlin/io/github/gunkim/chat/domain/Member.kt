package io.github.gunkim.chat.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
class Member(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    var password: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var lastLoginAt: LocalDateTime? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name', password='$password', createdAt=$createdAt, lastLoginAt=$lastLoginAt)"
    }
}
