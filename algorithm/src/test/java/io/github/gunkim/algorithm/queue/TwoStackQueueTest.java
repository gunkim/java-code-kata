package io.github.gunkim.algorithm.queue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TwoStackQueueTest {
    @Test
    void 요소가_순서대로_검색되는지_테스트() {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertThat(queue.get()).isEqualTo(1);
        assertThat(queue.get()).isEqualTo(2);
        assertThat(queue.get()).isEqualTo(3);
    }

    @Test
    void 추가와_조회_작업이_번갈아_수행되는지_테스트() {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();
        queue.add(1);
        queue.add(2);

        assertThat(queue.get()).isEqualTo(1);

        queue.add(3);
        assertThat(queue.get()).isEqualTo(2);
        assertThat(queue.get()).isEqualTo(3);
    }

    @Test
    void 빈_큐에서_예외가_발생하는지_테스트() {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();

        assertThatThrownBy(queue::get)
                .isInstanceOf(java.util.EmptyStackException.class);
    }

    @Test
    void 큐를_비우고_다시_채운후_조회하는_테스트() {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();
        queue.add(1);
        queue.add(2);
        queue.get();
        queue.get();

        queue.add(3);
        queue.add(4);

        assertThat(queue.get()).isEqualTo(3);
        assertThat(queue.get()).isEqualTo(4);
    }
}