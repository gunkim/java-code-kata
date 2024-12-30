package io.github.gunkim.algorithm.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("숫자 뒤집기")
class ReverseNumberTest {
    @Test
    @DisplayName("123을 뒤집으면 321이 된다")
    void testPositiveNumber() {
        var reverseNumber = new ReverseNumber(123);
        int result = reverseNumber.reverse();

        assertThat(result).isEqualTo(321);
    }
    
    @Test
    @DisplayName("-456을 뒤집으면 -654가 된다")
    void testNegativeNumber() {
        var reverseNumber = new ReverseNumber(-456);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(-654);
    }
    
    @Test
    @DisplayName("7은 뒤집어도 7이다")
    void testSingleDigit() {
        var reverseNumber = new ReverseNumber(7);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(7);
    }
    
    @Test
    @DisplayName("0은 뒤집어도 0이다")
    void testZero() {
        var reverseNumber = new ReverseNumber(0);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(0);
    }
}