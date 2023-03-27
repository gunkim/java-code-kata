import random


class Hangman:
    STAGES = [
        """
             _______
            |       |
            |       
            |      
            |       
            |      
            -
        """,
        """
             _______
            |       |
            |       O
            |      
            |       
            |      
            -
        """,
        """
             _______
            |       |
            |       O
            |       |
            |       |
            |      
            -
        """,
        """
             _______
            |       |
            |       O
            |      \\|
            |       |
            |      
            -
        """,
        """
             _______
            |       |
            |       O
            |      \\|/
            |       |
            |      
            -
        """,
        """
             _______
            |       |
            |       O
            |      \\|/
            |       |
            |      /
            -
        """,
        """
             _______
            |       |
            |       O
            |      \\|/
            |       |
            |      / \\
            -
        """,
    ]

    def __init__(self):
        self.__idx = 0

    def next(self):
        self.__idx = self.__idx + 1

    def display(self):
        return self.STAGES[self.__idx]

    def isOver(self):
        return self.__idx == len(self.STAGES) - 1


def main():
    answer: str = random.choice(read_keyword_file())

    guessed_letters: set[str] = set()
    hangman = Hangman()

    print("행맨 게임 시작!")
    while True:
        print(f"주어진 키워드: {masking_display_keyword(answer, guessed_letters)}")
        print(hangman.display())

        if hangman.isOver():
            print(f"게임 오버입니다. 정답은 [{answer}]였습니다!")
            break

        guess: str = input("추측하여 글자를 입력하세요: ").lower()
        if guess in guessed_letters:
            print("이미 시도한 글자입니다. 다른 글자를 시도하세요.")
            continue

        guessed_letters.add(guess)

        if guess in answer:
            if check_win(answer, guessed_letters):
                print(f"축하합니다! 주어진 글자 [{answer}]을 모두 완성했습니다!")
                break
            else:
                print("정답입니다!")
        else:
            print("오답입니다.")
            hangman.next()


def read_keyword_file() -> list[str]:
    with open("hangman-keyword", "r") as f:
        return f.read().splitlines()


def masking_display_keyword(answer: str, guessed_letters: set) -> str:
    return "".join([letter if letter in guessed_letters else "_" for letter in answer])


def check_win(answer: str, guessed_letters: set) -> bool:
    return all(letter in guessed_letters for letter in answer)


if __name__ == "__main__":
    main()
