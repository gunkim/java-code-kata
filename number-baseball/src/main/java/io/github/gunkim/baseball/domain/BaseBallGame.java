package io.github.gunkim.baseball.domain;

public class BaseBallGame {
    private final Input userInput;
    private final Input computerInput;
    private final Output output;
    private final int totalBallCount;
    private final boolean displayAnswer;

    public BaseBallGame(final Input userInput, final Input computerInput, final Output output, final int totalBallCount, final boolean displayAnswer) {
        this.userInput = userInput;
        this.computerInput = computerInput;
        this.output = output;
        this.totalBallCount = totalBallCount;
        this.displayAnswer = displayAnswer;
    }

    public void start() {
        while (true) {
            if (displayAnswer) {
                output.computerBallNumbers(computerInput.ballNumbers());
            }
            output.inputMessage();
            final BallNumbers userBallNumbers = userInput.ballNumbers();
            final BallNumbers computerBallNumbers = computerInput.ballNumbers();
            final Result matchResult = userBallNumbers.match(computerBallNumbers);
            if (checkGameEnd(matchResult)) break;
        }
    }

    private boolean checkGameEnd(final Result result) {
        if (result.isAllStrike(totalBallCount)) {
            output.gameEndedMessage(totalBallCount);
            return true;
        }
        if (result.isNothing()) {
            output.nothingMessage();
        } else {
            output.strikeAndBallMessage(result);
        }
        return false;
    }
}