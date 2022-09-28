package io.github.gunkim.domain.car.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class LabTests {
    @Test
    fun `인스턴스가 생성된다`() {
        assertDoesNotThrow { Lab(1) }
    }

    @Test
    fun `0 이하일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Lab(0) }
            .apply { assertThat(message).isEqualTo("게임 횟수는 1번 이상이어야 합니다.") }
    }
}