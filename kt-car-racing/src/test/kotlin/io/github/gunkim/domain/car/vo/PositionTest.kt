package io.github.gunkim.domain.car.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `음수일 경우 인스턴스 생성이 되지 않는다`() {
        assertThrows<IllegalArgumentException> { Position(-1) }
            .apply { assertThat(message).isEqualTo("위치는 음수일 수 없습니다.") }
    }
}