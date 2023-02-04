package io.github.gunkim

import io.github.gunkim.application.FindWord
import io.github.gunkim.application.FindWord.DefaultFindWord
import io.github.gunkim.application.Hangman
import io.github.gunkim.data.FileReadWords
import io.github.gunkim.domain.Letter
import io.github.gunkim.domain.Player

private const val FILE_NAME = "words"
private val findWord: FindWord = DefaultFindWord(FileReadWords(FILE_NAME))

fun main() {
    val problem = findWord.find()

    var hangman = Hangman.LEVEL1
    var player = Player(problem)
    while (!player.isComplete()) {
        println(hangman.display)

        if (hangman.isDead()) {
            println("제시어 [${problem.value}]을 완성하지 못하여")
            println("게임에서 패배했습니다!")
            return
        }

        println("제시어 : ${problemWord(problem.length, player.letters)}")

        val input = Letter(readln())

        val hitPlayer = player.hit(input)
        if (player != hitPlayer) {
            player = hitPlayer
        } else {
            hangman = hangman.next()
        }
    }
    println("제시어 [${problem.value}]을 모두 완성하여")
    println("게임에서 승리했습니다!")
}

private fun problemWord(length: Int, letters: Map<Int, Letter>) =
    (0 until length).joinToString(" ") { letters[it]?.value ?: "_" }
