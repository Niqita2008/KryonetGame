package me.niqitadev.core.tools;

import com.badlogic.gdx.utils.ObjectSet;

import java.util.Spliterator;
import java.util.function.BiConsumer;

public class CustomForEach {

    public static class Breaker {
        private boolean shouldBreak = false;

        public void stop() {
            shouldBreak = true;
        }

        boolean get() {
            return shouldBreak;
        }
    }

    public static <T> void forEach(Iterable<T> stream, BiConsumer<T, Breaker> consumer) {
        Spliterator<T> spliterator = stream.spliterator();
        boolean hadNext = true;
        Breaker breaker = new Breaker();

        while (hadNext && !breaker.get()) {
            hadNext = spliterator.tryAdvance(elem -> consumer.accept(elem, breaker));
        }
    }
}