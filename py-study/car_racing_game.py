from random import randint, randrange


class Car:
    def __init__(self, name, speed):
        self.name = name
        self.speed = speed
        self.position = 1

    def move(self):
        distance = randint(1, self.speed)
        self.position = self.position + distance


class RacingGame:
    def __init__(self, cars: list[Car]):
        self.cars = cars

    def play(self, distances):
        for dis in range(distances):
            print(f"Distance: {dis + 1}")
            for car in self.cars:
                car.move()
                print(f"{car.name}:{car.position * '-'}")

        max_pos = max(self.cars, key=lambda x: x.position).position
        winner_names = [car.name for car in self.cars if car.position == max_pos]
        if len(winner_names) == 1:
            print(f"winner is {winner_names[0]}")
        else:
            print(f"winners are {', '.join([name for name in sorted(winner_names)])}")

def input_cars() -> list[Car]:
    return [Car(name, randrange(1, 10)) for name in input("Enter names separated by comma.\n").split(',')]


def main():
    cars = input_cars()
    game = RacingGame(cars)
    game.play(10)


if __name__ == '__main__':
    main()

