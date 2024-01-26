package io.github.gunkim.tetris

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.stage.Stage

class TetrisApp : Application() {
    private val windowWidth = 500.0
    private val windowHeight = 810.0
    private val cellSize = 30.0
    private val boardWidth = 10
    private val boardHeight = (windowHeight / cellSize).toInt()
    private val cellPadding = 1.0
    private val previewSize = 2
    private val filledRows = 3

    override fun start(primaryStage: Stage) {
        val root = BorderPane()
        root.style = """
            -fx-background-color: #333333; 
            -fx-border-color: black; 
            -fx-border-width: 2px;
        """.trimIndent()

        val gameBoard = createGridPane(boardWidth, boardHeight, cellSize, cellPadding, Color.WHITE)
        fillBottomRows(gameBoard)

        val rightPanel = VBox(10.0)
        rightPanel.alignment = Pos.TOP_CENTER
        rightPanel.padding = Insets(20.0)

        val scoreDisplay = Text("점수: 100").apply { style = "-fx-fill: white;" }
        rightPanel.children.add(scoreDisplay)

        val nextBlockDisplay = createGridPane(previewSize, previewSize, cellSize / 2, 0.0, Color.WHITE)
        placeRandomTetrimino(nextBlockDisplay)
        rightPanel.children.add(nextBlockDisplay)

        val startPauseButton = Button("시작 / 일시정지").apply {
            style = """
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-padding: 10px 20px;
                -fx-border-radius: 5px;
                -fx-background-radius: 5px;
            """
            setOnMouseEntered {
                style += "-fx-background-color: #66BB6A;"
                cursor = Cursor.HAND
            }
            setOnMouseExited {
                style = style.replace("-fx-background-color: #66BB6A;", "-fx-background-color: #4CAF50;")
                cursor = Cursor.DEFAULT
            }
        }
        rightPanel.children.add(startPauseButton)

        root.center = gameBoard
        root.right = rightPanel

        val scene = Scene(root, windowWidth, windowHeight)
        primaryStage.scene = scene
        primaryStage.title = "테트리스 앱"
        primaryStage.isResizable = false
        primaryStage.show()
    }

    private fun createGridPane(
        width: Int,
        height: Int,
        cellSize: Double,
        padding: Double,
        backgroundColor: Color
    ): GridPane {
        val gridPane = GridPane()
        gridPane.padding = Insets(padding)
        gridPane.hgap = padding
        gridPane.vgap = padding
        gridPane.style =
            "-fx-background-color: ${toRgbString(backgroundColor)}; -fx-border-color: black; -fx-border-width: 1px;"

        for (y in 0 until height) {
            for (x in 0 until width) {
                val cell = Rectangle(cellSize, cellSize)
                cell.fill = Color.TRANSPARENT
                gridPane.add(cell, x, y)
            }
        }
        return gridPane
    }

    private fun fillBottomRows(gridPane: GridPane) {
        val tetriminos = listOf(
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)), // I
            listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(2, 0)), // J
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(2, 1)), // L
            listOf(Pair(0, 0), Pair(1, 0), Pair(0, 1), Pair(1, 1)), // O
            listOf(Pair(1, 0), Pair(2, 0), Pair(0, 1), Pair(1, 1)), // S
            listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(2, 1)), // T
            listOf(Pair(0, 0), Pair(1, 0), Pair(1, 1), Pair(2, 1))  // Z
        )
        val colors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PURPLE, Color.CYAN)

        for (y in boardHeight - filledRows until boardHeight) {
            val tetrimino = tetriminos.random()
            val color = colors.random()

            tetrimino.forEach { (dx, dy) ->
                val x = (0 until boardWidth).random()
                if (y + dy < boardHeight && x + dx < boardWidth) {
                    val index = (y + dy) * boardWidth + (x + dx)
                    (gridPane.children[index] as Rectangle).fill = color
                }
            }
        }
    }

    private fun placeRandomTetrimino(gridPane: GridPane) {
        val tetriminos = listOf(
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)), // I
            listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(2, 0)), // J
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(2, 1)), // L
            listOf(Pair(0, 0), Pair(1, 0), Pair(0, 1), Pair(1, 1)), // O
            listOf(Pair(1, 0), Pair(2, 0), Pair(0, 1), Pair(1, 1)), // S
            listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(2, 1)), // T
            listOf(Pair(0, 0), Pair(1, 0), Pair(1, 1), Pair(2, 1))  // Z
        )
        val colors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PURPLE, Color.CYAN)

        val tetrimino = tetriminos.random()
        val color = colors.random()

        tetrimino.forEach { (dx, dy) ->
            val index = dy * previewSize + dx
            if (index < gridPane.children.size) {
                (gridPane.children[index] as Rectangle).fill = color
            }
        }
    }

    private fun toRgbString(color: Color): String {
        return "rgb(${(color.red * 255).toInt()}, ${(color.green * 255).toInt()}, ${(color.blue * 255).toInt()})"
    }
}