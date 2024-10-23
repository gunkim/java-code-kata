package io.github.gunkim.baseball.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ConsoleInput은")
class ConsoleInputTest {
    @Test
    void 입력한_숫자가_세자리가_아닐_경우_예외가_발생한다() {
        final InputScanner mockInputScanner = () -> "1234";
        final ConsoleInput consoleInput = new ConsoleInput(mockInputScanner, 3);

        assertThatThrownBy(consoleInput::ballNumbers)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("3자리 숫자를 입력해주세요. 현재: 1234");
    }

    @Test
    void 중복된_숫자를_입력할_경우_예외가_발생한다() {
        final InputScanner mockInputScanner = () -> "111";
        final ConsoleInput consoleInput = new ConsoleInput(mockInputScanner, 3);

        assertThatThrownBy(consoleInput::ballNumbers)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복되지 않는 숫자를 입력해주세요. 현재: 111");
    }

    @Test
    void test() {
        final InputScanner mockInputScanner = () -> "abc";
        final ConsoleInput consoleInput = new ConsoleInput(mockInputScanner, 3);

        assertThatThrownBy(consoleInput::ballNumbers)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1부터 9까지의 숫자를 입력해주세요. 현재: abc");
    }
}