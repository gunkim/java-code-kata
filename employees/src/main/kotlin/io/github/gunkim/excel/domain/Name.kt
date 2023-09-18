package io.github.gunkim.excel.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Name(
    @Column(name = "first_name")
    val first: String,
    @Column(name = "last_name")
    val last: String
)
