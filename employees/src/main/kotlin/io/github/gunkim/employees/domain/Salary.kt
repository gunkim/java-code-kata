package io.github.gunkim.employees.domain

import jakarta.persistence.*
import java.io.Serializable
import java.sql.Timestamp

@Entity(name = "salaries")
class Salary(
    @EmbeddedId
    val id: Id,
    var salary: Int,
    var toDate: Timestamp
) {
    @Embeddable
    data class Id(
        val empNo: Int,
        val fromDate: Timestamp
    ) : Serializable
}
