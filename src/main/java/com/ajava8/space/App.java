package com.ajava8.space;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AJava8Space!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World..........!");
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        AtomicInteger j = new AtomicInteger();
        //j.set(4);
        Stream<Integer> intsStream = ints.stream();
       /* intsStream.forEach(i -> {
            j.set(j.get() + 1);
            if (j.get() == 4) {
                System.out.println("j is 4 now");

            } else {
                System.out.println(j.get());
            }
        });*/

       ints.stream()
                .anyMatch(i -> {
                    j.set(j.get() + 1);
                    if (j.get() <= 4) {

                        System.out.println(ints.get(j.get()));

                    }
                    System.out.println("I am executed"+i);
                    return false;
                });


        List<String> empNames = Arrays.asList("jack","Rocky","Paul","kevin","smith");


    }

}
