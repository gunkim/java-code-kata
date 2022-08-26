package io.github.gunkim.domain.car

class Car(
    val name: String,
    forward: Int = 0,
) {
    init {
        require(name.isNotBlank()) {
            "자동차 이름은 공백일 수 없습니다."
        }
        require(forward >= 0) {
            "자동차 위치는 음수일 수 없습니다."
        }
    }

    private var _forward: Int = forward
    val forward: Int
        get() = _forward

    fun go() {
        _forward++
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (name != other.name) return false
        if (_forward != other._forward) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + _forward
        return result
    }

    override fun toString(): String {
        return "Car(name='$name', forward=$_forward)"
    }

    operator fun component1(): String = name
    operator fun component2(): Int = _forward

}
