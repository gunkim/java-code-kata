package io.github.gunkim.employees.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "departments")
data class Department(
    @Id
    val deptNo: String,
    val deptName: String
)
