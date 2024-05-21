package io.github.gunkim.banking;

import io.github.gunkim.banking.application.AccountTransactionManager;
import io.github.gunkim.banking.data.InMemoryAccountRepository;
import io.github.gunkim.banking.data.InMemoryTransactionRepository;
import io.github.gunkim.banking.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankingApplication {
    private static final AccountId FIXED_MY_ACCOUNT_ID = AccountId.createRandom();
    private static final String TRANSACTION_VIEW_FORMAT = "%-20s %-15s %-15s\n";

    public static void main(String[] args) {
        BankingApplication app = new BankingApplication();
        app.run();
    }

    public void run() {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        accountRepository.save(Account.zero(FIXED_MY_ACCOUNT_ID));

        TransactionRepository transactionRepository = new InMemoryTransactionRepository();

        var accountTransactionManager = new AccountTransactionManager(accountRepository, transactionRepository);

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
