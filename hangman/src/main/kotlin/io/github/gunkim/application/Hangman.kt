package io.github.gunkim.application

enum class Hangman(
    val display: String
) {
    LEVEL1(
        """
        |  +---+
        |  |   |
        |      |
        |      |
        |      |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL2(
        """
        |  +---+
        |  |   |
        |  O   |
        |      |
        |      |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL3(
        """
        |  +---+
        |  |   |
        |  O   |
        |  |   |
        |      |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL4(
        """
        |  +---+
        |  |   |
        |  O   |
        | /|   |
        |      |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL5(
        """
        |  +---+
        |  |   |
        |  O   |
        | /|\  |
        |      |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL6(
        """
        |  +---+
        |  |   |
        |  O   |
        | /|\  |
        | /    |
        |      |
        |=========
        """.trimMargin()
    ),
    LEVEL7(
        """
        |  +---+
        |  |   |
        |  O   |
        | /|\  |
        | / \  |
        |      |
        |=========
        """.trimMargin()
    );

    fun next() = when (this) {
        LEVEL1 -> LEVEL2
        LEVEL2 -> LEVEL3
        LEVEL3 -> LEVEL4
        LEVEL4 -> LEVEL5
        LEVEL5 -> LEVEL6
        LEVEL6 -> LEVEL7
        LEVEL7 -> error("행맨은 이미 죽었습니다.")
    }

    fun isDead() = this == LEVEL7
}
