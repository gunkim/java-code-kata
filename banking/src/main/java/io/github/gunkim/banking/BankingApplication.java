package io.github.gunkim.banking;

import io.github.gunkim.banking.application.AccountTransactionManager;
import io.github.gunkim.banking.data.InMemoryAccountRepository;
import io.github.gunkim.banking.data.InMemoryTransactionRepository;
import io.github.gunkim.banking.domain.*;

import java.time.format.DateTimeFormatter;

public class BankingApplication {
    private static final AccountId FIXED_MY_ACCOUNT_ID = AccountId.createRandom();

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
        printTransactions(accountTransactionManager);
    }

    private void runTransactions(AccountTransactionManager accountTransactionManager) {
        accountTransactionManager.deposit(FIXED_MY_ACCOUNT_ID, new Money(3_000));
        accountTransactionManager.withdraw(FIXED_MY_ACCOUNT_ID, new Money(1_500));
        accountTransactionManager.deposit(FIXED_MY_ACCOUNT_ID, new Money(12_000));
    }

    private void printTransactions(AccountTransactionManager accountTransactionManager) {
        final var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        System.out.printf("%-20s %-15s %-15s\n", "Date", "Amount", "Balance");
        for (Transaction transaction : accountTransactionManager.findAll(FIXED_MY_ACCOUNT_ID)) {
            System.out.printf("%-20s %-15s %-15s\n",
                    formatter.format(transaction.createdAt()),
                    transaction.signedAmount(),
                    transaction.balance());
        }
    }
}
