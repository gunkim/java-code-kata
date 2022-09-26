package io.github.gunkim.domain.car.vo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ForwardTest {
    @Test
    fun `음수일 경우 인스턴스 생성이 되지 않는다`() {
        assertThrows<IllegalArgumentException>
        { Forward(-1) }
    }
}