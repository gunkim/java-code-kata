package io.github.gunkim.baseball.domain;

public interface Output {
    void inputMessage();

    void computerBallNumbers(BallNumbers ballNumbers);

    void gameEndedMessage(int ballCount);

    void nothingMessage();

    void strikeAndBallMessage(Result result);
}
