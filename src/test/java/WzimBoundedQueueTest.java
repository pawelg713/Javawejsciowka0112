import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Uwaga, to nie są dobre testy.
 * Nazwy metod są nieczytelne.
 * Nie jest zachowana konwencja // given // when // then.
 */
class WzimBoundedQueueTest {

    private SimpleWzimBoundedQueue<String> queue;

    @Nested
    class Capacity1 {

        @BeforeEach
        void setUp() {
            queue = new SimpleWzimBoundedQueue<>(1);
        }

        @Test
        void add() {
            assertThat(queue.add("foo")).isTrue();
            assertThatExceptionOfType(IllegalStateException.class).isThrownBy(
                    () -> queue.add("foo")
            );
        }

        @Test
        void offer() {
            assertThat(queue.offer("foo")).isTrue();
            assertThat(queue.offer("foo")).isFalse();
        }

        @Test
        void remove1() {
            queue.add("foo");
            assertThat(queue.remove()).isEqualTo("foo");
        }

        @Test
        void remove2() {
            queue.add("foo");
            assertThat(queue.remove()).isEqualTo("foo");
            assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(
                    () -> queue.remove()
            );
        }

        @Test
        void poll() {
            assertThat(queue.poll()).isNull();
            queue.add("foo");
            assertThat(queue.poll()).isEqualTo("foo");
            assertThat(queue.poll()).isNull();
        }

        @Test
        void element1() {
            assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(
                    () -> queue.element()
            );
        }

        @Test
        void element2() {
            queue.add("foo");
            assertThat(queue.element()).isEqualTo("foo");
            assertThat(queue.element()).isEqualTo("foo");
        }

        @Test
        void peek1() {
            assertThat(queue.peek()).isNull();
        }

        @Test
        void peek2() {
            queue.add("foo");
            assertThat(queue.peek()).isEqualTo("foo");
            assertThat(queue.peek()).isEqualTo("foo");
        }

        @Test
        void size() {
            assertThat(queue.size()).isEqualTo(0);
            queue.add("foo");
            assertThat(queue.size()).isEqualTo(1);
        }

        @Test
        void capacity() {
            assertThat(queue.capacity()).isEqualTo(1);
            queue.add("foo");
            assertThat(queue.capacity()).isEqualTo(1);
        }
    }

    @Nested
    class Other {
        @Test
        void test1() {
            queue = new SimpleWzimBoundedQueue<>(2);
            queue.add("foo");
            queue.add("bar");

            assertThat(queue.poll()).isEqualTo("foo");
            assertThat(queue.poll()).isEqualTo("bar");
            assertThat(queue.poll()).isNull();
        }

        @Test
        void concurrentTest() throws InterruptedException {
            queue = new SimpleWzimBoundedQueue<>(200_000);
            ExecutorService executor = Executors.newCachedThreadPool();
            for (int i = 0; i < 10; i++) {
                executor.execute(() ->
                        IntStream
                                .range(0, 10_000)
                                .forEach(param ->
                                        queue.add("a"))

                );
            }

            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.SECONDS);

            for (int i = 0; i < 100_000; i++) {
                assertThat(queue.poll()).as("testing poll " + i).isEqualTo("a");
            }
        }
    }
}