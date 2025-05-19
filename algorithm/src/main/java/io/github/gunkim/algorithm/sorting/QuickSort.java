package io.github.gunkim.algorithm.sorting;

public class QuickSort implements Sort {
    private static final int MINIMUM_ARRAY_SIZE = 2;

    private final int[] numbers;
    private boolean sorted = false;

    public QuickSort(int[] numbers) {
        validateInput(numbers);
        this.numbers = numbers;
    }

    private void validateInput(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        if (numbers.length < MINIMUM_ARRAY_SIZE) {
            throw new IllegalArgumentException("Input array must have at least " + MINIMUM_ARRAY_SIZE + " elements");
        }
    }

    @Override
    public int[] sort() {
        if (sorted) {
            return numbers;
        }
        quickSort(0, numbers.length - 1);
        sorted = true;
        return numbers;
    }

    private void quickSort(int start, int end) {
        if (start >= end) {
            return;
        }
        int pivotIndex = partition(start, end);
        quickSort(start, pivotIndex - 1);
        quickSort(pivotIndex + 1, end);
    }

    private int partition(int start, int end) {
        int pivot = numbers[start];
        int leftPointer = start + 1;
        int rightPointer = end;

        while (leftPointer <= rightPointer) {
            while (leftPointer <= end && pivot >= numbers[leftPointer]) {
                leftPointer++;
            }
            while (rightPointer > start && pivot < numbers[rightPointer]) {
                rightPointer--;
            }
            if (leftPointer < rightPointer) {
                swap(leftPointer, rightPointer);
            }
        }
        swap(start, rightPointer);
        return rightPointer;
    }

    private void swap(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
}