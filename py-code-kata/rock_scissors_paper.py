from random import choice

HAND = [
    "바위",
    "가위",
    "보",
]


def main():
    user_hand = HAND.index(input("가위, 바위, 보 중 하나를 입력하세요: "))
    computer_hand = HAND.index(choice(HAND))

    print(f"""
사용자: {HAND[user_hand]}
컴퓨터: {HAND[computer_hand]}
    """.strip())
    if (user_hand % 3) + 1 == computer_hand:
        print("이겼습니다!")
    elif user_hand == computer_hand:
        print("비겼습니다!")
    else:
        print("졌습니다!")


if __name__ == "__main__":
    main()
