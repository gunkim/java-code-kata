package io.github.gunkim.domain.car.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NameTest {
    @Test
    fun `공백으로 인스턴스 생성이 되지 않는다`() {
        assertThrows<IllegalArgumentException> { Name("") }
            .apply { assertThat(message).isEqualTo("이름은 공백일 수 없습니다.") }
    }
}