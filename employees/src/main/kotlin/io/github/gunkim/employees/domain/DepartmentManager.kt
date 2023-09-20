package io.github.gunkim.employees.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import java.io.Serializable
import java.sql.Timestamp

@Entity(name = "dept_manager")
class DepartmentManager(
    @EmbeddedId
    val id: Id,
    val fromDate: Timestamp,
    val toDate: Timestamp
) {
    @Embeddable
    data class Id(
        val empNo: Int,
        val deptNo: String
    ) : Serializable
}
