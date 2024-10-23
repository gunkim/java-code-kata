package io.github.gunkim.baseball.domain;

public interface Output {
    void inputMessage();

    void computerBallNumbers(final BallNumbers ballNumbers);

    void gameEndedMessage(final int ballCount);

    void nothingMessage();

    void strikeAndBallMessage(Result result);
}
