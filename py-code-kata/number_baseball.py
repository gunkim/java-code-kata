from random import sample


def main():
    computer = sample(range(1, 10), 3)
    while True:
        numbers = list(map(int, input("숫자를 입력해주세요:")))

        strikes = sum(user_number == computer for user_number, computer in zip(numbers, computer))
        balls = sum(user_number in computer for user_number in numbers) - strikes

        print(f"{strikes} 스트라이크 {balls} 볼입니다.")

        if strikes == 3:
            print("정답입니다.")
            break


if __name__ == '__main__':
    main()
