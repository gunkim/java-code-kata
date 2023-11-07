package io.github.gunkim.bank

class Transactions(
    content: List<Transaction>
) {
    val content = content.toList()

    val totalAmount: Int
        get() = content.filter { it.type == Transaction.Type.DEPOSIT }
            .sumOf { it.amount } -
                content.filter { it.type == Transaction.Type.WITHDRAWAL }
                    .sumOf { it.amount }
}
