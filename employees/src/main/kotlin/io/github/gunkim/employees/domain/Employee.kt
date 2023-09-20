package io.github.gunkim.employees.domain

import io.github.gunkim.employees.domain.vo.Gender
import io.github.gunkim.employees.domain.vo.Name
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.sql.Timestamp

@Entity(name = "employees")
class Employee(
    @Id
    val empNo: Int,
    val birthDate: Timestamp,
    @Embedded
    var name: Name,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    val hireDate: Timestamp
)
