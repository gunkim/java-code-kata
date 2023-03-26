import unittest

from car_racing_game import Car, RacingGame


class CarTestCase(unittest.TestCase):
    def test_자동차는_전진한다(self):
        car = Car("gun car", 3)
        car.move(lambda start, end: 1)

        self.assertEqual(car.position, 2)


class RacingGameTestCase(unittest.TestCase):
    def test_자동차가_2대_이하라면_예외가_발생한다(self):
        with self.assertRaisesRegex(AssertionError, 'cars must be minimum 2'):
            RacingGame([Car('gun car', 3)])


if __name__ == '__main__':
    unittest.main()
