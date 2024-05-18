package io.github.gunkim.banking.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("금액은")
class MoneyTests {
    @Test
    void 정상적으로_생성된다() {
        assertThatCode(() -> new Money(0))
                .doesNotThrowAnyException();
    }

    @Test
    void 음수가_될_수_없다() {
        assertThatThrownBy(() -> new Money(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금액은 음수가 될 수 없습니다.");
    }

    @Test
    void 금액을_더할_수_있다() {
        var moneyA = new Money(200);
        var moneyB = new Money(300);

        var actualMoney = moneyA.plus(moneyB);
        var expectedMoney = new Money(500);

        assertThat(actualMoney).isEqualTo(expectedMoney);
    }

    @Test
    void 금액을_차감할_수_있다() {
        var moneyA = new Money(200);
        var moneyB = new Money(100);

        var actualMoney = moneyA.minus(moneyB);
        var expectedMoney = new Money(100);

        assertThat(actualMoney).isEqualTo(expectedMoney);
    }
}
