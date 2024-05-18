package io.github.gunkim.banking.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("계좌는")
class AccountTests {
    private Account account;
    private Money initialBalance;

    @BeforeEach
    void setUp() {
        initialBalance = new Money(1000);
        account = new Account(initialBalance);
    }

    @Test
    void 입금할_수_있다() {
        Money depositAmount = new Money(500);

        Money newBalance = account.deposit(depositAmount);

        assertThat(newBalance).isEqualTo(new Money(1500));
        assertThat(account.balance()).isEqualTo(new Money(1500));
    }

    @Test
    void 출금할_수_있다() {
        Money withdrawAmount = new Money(500);

        Money newBalance = account.withdraw(withdrawAmount);

        assertThat(newBalance).isEqualTo(new Money(500));
        assertThat(account.balance()).isEqualTo(new Money(500));
    }

    @Test
    void 잔액보다_큰_금액은_출금할_수_없다() {
        Money withdrawAmount = new Money(1500);

        assertThatThrownBy(() -> account.withdraw(withdrawAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("현재 계좌 잔액(1000)보다 출금 금액(1500)이 더 큽니다.");
    }
}
