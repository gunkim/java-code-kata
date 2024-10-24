package io.github.gunkim.baseball.application;

import io.github.gunkim.baseball.domain.BallNumbers;
import io.github.gunkim.baseball.domain.Output;
import io.github.gunkim.baseball.domain.Result;

public class ConsoleOutput implements Output {
    @Override
    public void inputMessage() {
        System.out.println("숫자를 입력해주세요 : ");
    }

    @Override
    public void computerBallNumbers(BallNumbers ballNumbers) {
        System.out.println("정답 : " + ballNumbers);
    }

    @Override
    public void gameEndedMessage(int ballCount) {
        System.out.printf("%d개의 숫자를 모두 맞히셨습니다! 게임 종료%n", ballCount);
    }

    @Override
    public void nothingMessage() {
        System.out.println("낫싱");
    }

    @Override
    public void strikeAndBallMessage(final Result result) {
        System.out.printf("%d 스트라이크 %d 볼%n", result.strike(), result.ball());
    }
}
