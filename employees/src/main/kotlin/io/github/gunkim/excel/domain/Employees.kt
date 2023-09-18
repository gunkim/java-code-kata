package io.github.gunkim.excel.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.sql.Timestamp
import java.util.*

@Entity
class Employees(
    @Id
    val empNo: Int,
    val birthDate: Timestamp,
    @Embedded
    var name: Name,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    val hireDate: Timestamp
)
