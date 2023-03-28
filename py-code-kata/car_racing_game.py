from dataclasses import dataclass, field
from random import randint, randrange
from typing import Callable


@dataclass
class Car:
    name: str
    speed: int
    position: int = field(default=1)

    def __post_init__(self):
        assert self.speed > 0, "speed must be greater than 0"
        assert self.name is not None, "name must be not None"

    def move(self, get_random: Callable[[int, int], int]):
        distance: int = get_random(1, self.speed)
        self.position = self.position + distance


@dataclass
class RacingGame:
    cars: list[Car]

    def __post_init__(self):
        assert len(self.cars) > 1, "cars must be minimum 2"

    def play(self, distances: int):
        for distance in range(distances):
            print(f"Distance: {distance + 1}")
            for car in self.cars:
                car.move(lambda start, end: randint(start, end))
                print(f"{car.name}:{car.position * '-'}")

        max_pos: int = max(self.cars, key=lambda x: x.position).position
        winner_names: list[str] = [car.name for car in self.cars if
                                   car.position == max_pos]
        if len(winner_names) == 1:
            print(f"winner is {winner_names[0]}")
        else:
            print(
                f"winners are {', '.join([name for name in sorted(winner_names)])}")


def input_cars() -> list[Car]:
    return [Car(name, randrange(1, 10)) for name in
            input("Enter names separated by comma.\n").split(',')]


def main():
    cars = input_cars()
    game = RacingGame(cars)
    game.play(10)


if __name__ == '__main__':
    main()
