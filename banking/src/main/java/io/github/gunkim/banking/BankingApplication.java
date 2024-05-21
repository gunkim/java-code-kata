package io.github.gunkim.banking;

import io.github.gunkim.banking.application.AccountTransactionManager;
import io.github.gunkim.banking.data.InMemoryAccountRepository;
import io.github.gunkim.banking.data.InMemoryTransactionRepository;
import io.github.gunkim.banking.domain.Account;
import io.github.gunkim.banking.domain.AccountId;
import io.github.gunkim.banking.domain.Money;
import io.github.gunkim.banking.domain.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankingApplication {
    private static final AccountId FIXED_MY_ACCOUNT_ID = AccountId.createRandom();
    private static final String TRANSACTION_VIEW_FORMAT = "%-20s %-15s %-15s\n";

    private final AccountTransactionManager accountTransactionManager;

    public BankingApplication(AccountTransactionManager accountTransactionManager) {
        this.accountTransactionManager = accountTransactionManager;
    }

    public static void main(String[] args) {
        var accountRepository = new InMemoryAccountRepository();
        var transactionRepository = new InMemoryTransactionRepository();
        accountRepository.save(Account.zero(FIXED_MY_ACCOUNT_ID));

        var accountTransactionManager = new AccountTransactionManager(accountRepository, transactionRepository);
        var app = new BankingApplication(accountTransactionManager);
        app.run();
    }

    public void run() {
        runTransactions(accountTransactionManager);
        printTransactions(accountTransactionManager.findAll(FIXED_MY_ACCOUNT_ID));
    }

    private void runTransactions(AccountTransactionManager accountTransactionManager) {
        accountTransactionManager.deposit(FIXED_MY_ACCOUNT_ID, new Money(3_000));
        accountTransactionManager.withdraw(FIXED_MY_ACCOUNT_ID, new Money(1_500));
        accountTransactionManager.deposit(FIXED_MY_ACCOUNT_ID, new Money(12_000));
    }

    private void printTransactions(List<Transaction> transactions) {
        final var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        System.out.printf(TRANSACTION_VIEW_FORMAT, "Date", "Amount", "Balance");
        for (Transaction transaction : transactions) {
            System.out.printf(TRANSACTION_VIEW_FORMAT,
                    formatter.format(transaction.createdAt()),
                    transaction.signedAmount(),
                    transaction.balance());
        }
    }
}
