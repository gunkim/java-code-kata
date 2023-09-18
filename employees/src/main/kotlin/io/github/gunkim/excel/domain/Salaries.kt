package io.github.gunkim.excel.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import java.io.Serializable
import java.util.*

@Embeddable
data class SalariesId(
    val empNo: Int,
    val fromDate: Date
) : Serializable

@Entity
class Salaries private constructor(
    @EmbeddedId
    val id: SalariesId,
    var salary: Int,
    var toDate: Date,
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @ManyToOne
    val employees: Employees
)
