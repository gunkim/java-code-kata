package io.github.gunkim.algorithm.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("숫자 뒤집기")
class ReverseNumberTest {
    @Test
    void testPositiveNumber() {
        var reverseNumber = new ReverseNumber(123);
        int result = reverseNumber.reverse();

        assertThat(result).isEqualTo(321);
    }
    
    @Test
    void testNegativeNumber() {
        var reverseNumber = new ReverseNumber(-456);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(-654);
    }
    
    @Test
    void testSingleDigit() {
        var reverseNumber = new ReverseNumber(7);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(7);
    }
    
    @Test
    void testZero() {
        var reverseNumber = new ReverseNumber(0);
        int result = reverseNumber.reverse();
    
        assertThat(result).isEqualTo(0);
    }
}