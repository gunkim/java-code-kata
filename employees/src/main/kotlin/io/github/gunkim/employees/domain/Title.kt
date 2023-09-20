package io.github.gunkim.employees.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import java.io.Serializable
import java.sql.Timestamp

@Entity(name = "titles")
data class Title(
    @EmbeddedId
    val id: Id,
    @Column(name = "to_date")
    val toDate: Timestamp?
) {
    @Embeddable
    data class Id(
        val empNo: Int,
        val title: String,
        val fromDate: Timestamp
    ) : Serializable
}
