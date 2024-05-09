package com.ajava8.space;

import java.util.Arrays;
import java.util.List;

public class Test {
        public static void main(String[] args) {
            List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

            ints.stream()
                    .filter(new BreakAtCondition<>(i -> i == 6)) // Filter elements until the condition is met
                    .forEach(i -> System.out.println("Java 8 : " + i));
        }

        static class BreakAtCondition<T> implements java.util.function.Predicate<T> {
            private boolean conditionMet = false;
            private java.util.function.Predicate<T> predicate;

            public BreakAtCondition(java.util.function.Predicate<T> predicate) {
                this.predicate = predicate;
            }

            @Override
            public boolean test(T t) {
                System.out.println("Test Method");
                if (!conditionMet ) {
                    conditionMet = predicate.test(t);
                    return true;
                }

                return false;
            }
        }


}
